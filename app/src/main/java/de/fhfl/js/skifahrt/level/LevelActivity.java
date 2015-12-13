package de.fhfl.js.skifahrt.level;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import de.fhfl.js.skifahrt.MainActivity;
import de.fhfl.js.skifahrt.R;
import de.fhfl.js.skifahrt.level.fragment.LevelLostFragment;
import de.fhfl.js.skifahrt.level.fragment.LevelWinFragment;
import de.fhfl.js.skifahrt.movingObject.Rabbit;
import de.fhfl.js.skifahrt.movingObject.SkierMoving;

/**
 * Steuert die Grundelement für alle Levels.
 * <p/>
 * Created by Jasmin on 19.11.2015.
 */
abstract public class LevelActivity extends AppCompatActivity {

    private static final String TAG = "LevelActivity";
    private static final int MAX_STEP = 5;
    private static final int MAX_LIFE = 3;

    private LinearLayout layoutWin;
    private LinearLayout layoutLost;
    private ImageView skierView;
    private ImageView rabbitView;
    private Rabbit rabbit;
    private ImageView goalView;
    protected SkierMoving skier;
    private RelativeLayout relativeLayout;
    private boolean firstCall = true;
    private int levelStep = 1;
    protected int life = 1;
    private boolean lost = false;
    private boolean win = false;
    private ScheduledFuture scheduleFuture;

    /**
     * Wird beim ersten Aufruf der Activity gestartet. Setzt den Text für die Levelansichten
     * Lädt die Variablen für die einzelnen Views. Startet den Skifahrer und des Hasen.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setzt den Content View
        setContentView(R.layout.activity_level);
        // wenn ein für die Activity eine Variable gesetzt wurde, sollen diese in die
        // Klassenvariable setzen
        if (getIntent().getExtras() != null) {
            // levelStep setzen: default = 1
            levelStep = getIntent().getIntExtra("levelStep", 1);
            // Lebenspunkte setzen: default = 1
            life = getIntent().getIntExtra("life", 1);
        }

        // Lädt die Texte für die Übersichtlichkeit
        loadLevelStepText();
        loadLevelText();

        // lädt alle Variablen für die benötigten Views
        layoutWin = (LinearLayout) findViewById(R.id.layoutFragmentWin);
        layoutLost = (LinearLayout) findViewById(R.id.layoutFragmentLost);
        skierView = (ImageView) findViewById(R.id.skifahrer);
        rabbitView = (ImageView) findViewById(R.id.rabbit);
        goalView = (ImageView) findViewById(R.id.goal);
        relativeLayout = (RelativeLayout) findViewById(R.id.levelScreen);

        // Initalisiert den Hasen und den Skifahrer
        rabbit = new Rabbit();
        skier = new SkierMoving();
    }

    /**
     * Lädt den Text in das Layout für den momentanen LevelStep
     */
    private void loadLevelStepText() {
        TextView levelStepOutput = (TextView) findViewById(R.id.levelStep);
        levelStepOutput.setText(levelStep + "/" + MAX_STEP);
    }

    /**
     * Lädt den Text in das Layout für das momentane Level
     */
    private void loadLevelText() {
        TextView levelText = (TextView) findViewById(R.id.levelText);
        String text = "";
        switch (this.getClass().getSimpleName()) {
            case "LevelOneActivity":
                text = getString(R.string.level_name_one_long);
                break;
            case "LevelTwoActivity":
                text = getString(R.string.level_name_two_long);
                break;
            case "LevelThreeActivity":
                text = getString(R.string.level_name_three_long);
                break;
            case "LevelFourActivity":
                text = getString(R.string.level_name_four_long);
                break;
        }
        levelText.setText(text);
    }

    /**
     * Beim ersten Fokus werden die Höhen für die Views ausgelesen und die Threads für
     * das kontinuierliche Fahren des Skifahrers gesetzt.
     *
     * @param hasFocus boolean
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && firstCall) {
            // Werte für den Skifahrer setzen
            skier.setMaxHeight(relativeLayout.getHeight());
            skier.setMaxWidth(relativeLayout.getWidth());
            skier.setSkierHeight(skierView.getHeight());
            skier.addToSpeed(levelStep);

            // Werte für den Hasen setzen
            rabbit.setMaxHeight(relativeLayout.getHeight());
            rabbit.setMaxWidth(relativeLayout.getWidth());
            rabbit.loadPosition();
            rabbitView.setX(rabbit.getPositionX());
            rabbitView.setY(rabbit.getPositionY());
            firstCall = false;

            // Threads für das Bewegen der einzelnen Elemente setzen.
            ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
            scheduleFuture = scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    runSkier();
                    // runOnUiThread wird benötigt, damit das Fragement aufgerufen werden kann
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (lost) {
                                openLostDialog();
                            } else if (win) {
                                openWinDialog();
                            }
                        }
                    });
                }
            }, 0, 2, TimeUnit.MILLISECONDS); // jede Miilisekunde
        }
    }

    /**
     * Lässt die MainActivity beim Klicken auf den Zurückbutton starten.
     */
    @Override
    public void onBackPressed() {
        // Go to the Main menu. If this wouldn't call, the fragments will be call at backPressed
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    /**
     * Wird aufgerufen, wenn die App zerstört wird aber noch nicht vollständig beendet wird
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Alle offenen Events müssen hier gestoppt werden
        stopEvent();
        finish();
    }

    /**
     * Wird aufgerufen, wenn die App beendet wird.
     */
    @Override
    public void onStop() {
        super.onStop();
        // Alle offenen Events müssen hier gestoppt werden
        stopEvent();
        finish();
    }

    /**
     * Das momentan laufende Level wird nochmals wiederholt.
     *
     * @param view View
     */
    public void reloadLevel(View view) {
        // verlorenes Leben hochzählen.
        life++;

        // Geliche Activity nochmal starten mit einem Leben weniger
        Intent intent = new Intent(this, this.getClass());
        intent.putExtra("life", life);
        intent.putExtra("levelStep", levelStep);
        this.startActivity(intent);
    }

    /**
     * Startet das nächste Level: Wenn die Levelsteps noch nicht voll sind, wird zunächst der nächste
     * Level Schritt mit höhrer Geschwindigkeit geladen. Ansonsten wird das nächste Level mit neuer
     * Steuerung geöffnet.
     *
     * @param view View
     */
    public void nextLevel(View view) {
        levelStep++; // Level Schritt hochzählen.
        Class nextClassLevel = this.getClass();
        Log.d(TAG, "" + this.getClass().getSimpleName());
        if (levelStep > MAX_STEP) {
            switch (this.getClass().getSimpleName()) {
                case "LevelOneActivity":
                    nextClassLevel = LevelTwoActivity.class;
                    break;
                case "LevelTwoActivity":
                    nextClassLevel = LevelThreeActivity.class;
                    break;
                case "LevelThreeActivity":
                    nextClassLevel = LevelFourActivity.class;
                    break;
                case "LevelFourActivity":
                    nextClassLevel = MainActivity.class;
                    break;
            }
            levelStep = 1;
        }
        // nächste Activity öffnen.
        Intent intent = new Intent(this, nextClassLevel);
        Log.d(TAG, "LevelStep bevor:" + levelStep);
        intent.putExtra("levelStep", levelStep);
        intent.putExtra("life", life);
        this.startActivity(intent);
    }

    /**
     * Prüft, ob sich zwei Views überlagern.
     *
     * @param firstView  View
     * @param secondView View
     * @return boolean
     */
    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        // lädt die Positionen der Views auf dem Bildschirm
        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        // prüft, ob die beiden Views sich überlagern:
        int skierX1 = firstPosition[0];
        int skierX2 = firstPosition[0] + firstView.getMeasuredWidth();
        int skierY1 = firstPosition[1];
        int skierY2 = firstPosition[1] + firstView.getMeasuredHeight();
        int goalX1 = secondPosition[0];
        int goalX2 = secondPosition[0] + secondView.getMeasuredHeight();
        int goalY1 = secondPosition[1];
        int goalY2 = secondPosition[1] + secondView.getMeasuredHeight();
        return (skierX2 >= goalX1 &&
                skierY2 >= goalY1 &&
                skierY1 <= goalY2 &&
                skierX2 <= goalX2) ||

                (skierX1 >= goalX1 &&
                        skierY2 >= goalY1 &&
                        skierY1 <= goalY2 &&
                        skierX1 <= goalX2);
    }

    /**
     * Gibt die ImageView für den Skifahrer zurück.
     *
     * @return ImageView
     */
    protected ImageView getSkierImageView() {
        return skierView;
    }

    /**
     * Gibt die ImageView für den Hasen zurück.
     *
     * @return ImageView
     */
    protected ImageView getRabbitImageView() {
        return rabbitView;
    }

    /**
     * Gibt die ImageView für das Ziel zurück.
     *
     * @return ImageView
     */
    protected ImageView getGoalImageView() {
        return goalView;
    }

    /**
     * Öffnet das Fragment für den "Gewonnen"-Dialog.
     */
    private void openWinDialog() {
        Log.d(TAG, "openWinDialog");
        // Zuerst alle Events stoppen.
        stopEvent();
        // jetzt das Fragment starten.
        Fragment frag = new LevelWinFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.level_win, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
        // Sichtbarkeit des Views auf sichtbar setzen.
        layoutWin.setVisibility(View.VISIBLE);
    }

    /**
     * Öffnet das Fragment für den "Verloren"-Dialog.
     */
    private void openLostDialog() {
        Log.d(TAG, "openLostDialog");
        // Zuerst alle Events stoppen.
        stopEvent();
        if (life >= MAX_LIFE) {
            // maximale lebensanzahl erreicht. Zurück zur MainActivity
            life = 1;
            openMainActivity();
            return;
        }
        // jetzt das Fragment starten.
        Fragment frag = new LevelLostFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.level_lost, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
        // Sichtbarkeit des Views auf sichtbar setzen.
        layoutLost.setVisibility(View.VISIBLE);
    }

    /**
     * Bewegt den Skifahrer vorwärts, indem er die Position des Views setzt. Überprüft auch,
     * ob der Skifahrer den Hasen anfährt. Geschieht dieses, wird ein Flag gesetzt, sodass der
     * passende Dialog aufgerufen wird.
     */
    protected void runSkier() {
        try {
            Log.d(TAG, "Run");
            if (isViewOverlapping(getSkierImageView(), getGoalImageView())) {
                Log.d(TAG, "Ziel erreicht");
                win = true;
            } else if (isViewOverlapping(getSkierImageView(), getRabbitImageView())) {
                Log.i(TAG, "Level verloren");
                lost = true;
            } else {
                skier.setSkierPositionX(getSkierImageView().getX());
                skier.setSkierPositionY(getSkierImageView().getY());
                // TODO: Bande führt auch zum fehler
                                     /* if (skier.isYOutOfWindow()) {
                                          stopEvent();
                                          openLostDialog();
                                      } else {*/
                getSkierImageView().setX(skier.getX());
                getSkierImageView().setY(skier.getY());
                //}
            }
        } catch (Exception e) {
            System.out.print("Error: "+e);
        }
    }

    /**
     * Stoppt alle notwendigen Events.
     */
    protected void stopEvent() {
        // Stoppt den Thread für das Bewegen des Skifahrers.
        scheduleFuture.cancel(true);
    }

    /**
     * Öffnet die MainActivity.
     */
    protected void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
