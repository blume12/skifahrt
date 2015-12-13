package de.fhfl.js.skifahrt.level;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
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
 * Created by Jasmin on 19.11.2015.
 */
abstract public class LevelActivity extends AppCompatActivity {

    private static final String TAG = "LevelActivity";
    private static final int MAX_STEP = 5;
    private static final int MAX_LIFE = 3;

    private LinearLayout layoutWin;
    private LinearLayout layoutLost;
    private ImageView skifahrer;
    private ImageView rabbit;
    private Rabbit rabbitView;
    private ImageView goal;
    protected SkierMoving skier;
    private RelativeLayout relativeLayout;
    private boolean firstCall = true;

    private int levelStep = 1;
    protected int life = 1;
    boolean lost = false;
    boolean win = false;

    private ScheduledFuture scheduleFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        if (getIntent().getExtras() != null) {
            levelStep = getIntent().getIntExtra("levelStep", 1);
        }
        if (getIntent().getExtras() != null) {
            life = getIntent().getIntExtra("life", 1);
        }

        setLevelStepText();
        setLevelText();

        layoutWin = (LinearLayout) findViewById(R.id.layoutFragmentWin);
        layoutLost = (LinearLayout) findViewById(R.id.layoutFragmentLost);

        skifahrer = (ImageView) findViewById(R.id.skifahrer);
        rabbit = (ImageView) findViewById(R.id.imageView3);
        goal = (ImageView) findViewById(R.id.goal);
        relativeLayout = (RelativeLayout) findViewById(R.id.levelScreen);

        rabbitView = new Rabbit();
        skier = new SkierMoving();
    }

    private void setLevelStepText() {
        TextView levelStepOutput = (TextView) findViewById(R.id.levelStep);
        levelStepOutput.setText(levelStep + "/" + MAX_STEP);
    }

    private void setLevelText() {
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && firstCall) {
            skier.setMaxHeight(relativeLayout.getHeight());
            skier.setMaxWidth(relativeLayout.getWidth());
            skier.setSkierHeight(skifahrer.getHeight());
            skier.addToSpeed(levelStep);

            rabbitView.setMaxHeight(relativeLayout.getHeight());
            rabbitView.setMaxWidth(relativeLayout.getWidth());
            rabbitView.loadPosition();
            rabbit.setX(rabbitView.getPositionX());
            rabbit.setY(rabbitView.getPositionY());
            firstCall = false;

            ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

            // This schedule a runnable task every 2 minutes
            scheduleFuture  = scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    runSkier();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(lost) {
                                openLostDialog();
                            } else if(win) {
                                openWinDialog();
                            }
                        }
                    });
                }
            }, 0, 2, TimeUnit.MILLISECONDS);
        }
    }

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

    public void reloadLevel(View view) {
        life++;
        Class nextClassLevel = this.getClass();

        Intent intent = new Intent(this, nextClassLevel);
        intent.putExtra("life", life);
        intent.putExtra("levelStep", levelStep);
        this.startActivity(intent);
    }

    public void nextLevel(View view) {
        levelStep++;
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
        Intent intent = new Intent(this, nextClassLevel);

        Log.d(TAG, "LevelStep bevor:" + levelStep);
        intent.putExtra("levelStep", levelStep);
        intent.putExtra("life", life);
        this.startActivity(intent);
    }

    private boolean isViewOverlapping(View skier, View goal) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        skier.getLocationOnScreen(firstPosition);
        goal.getLocationOnScreen(secondPosition);

        int skierX1 = firstPosition[0];
        int skierX2 = firstPosition[0] + skier.getMeasuredWidth();
        int skierY1 = firstPosition[1];
        int skierY2 = firstPosition[1] + skier.getMeasuredHeight();
        int goalX1 = secondPosition[0];
        int goalX2 = secondPosition[0] + goal.getMeasuredHeight();
        int goalY1 = secondPosition[1];
        int goalY2 = secondPosition[1] + goal.getMeasuredHeight();
        return (skierX2 >= goalX1 &&
                skierY2 >= goalY1 &&
                skierY1 <= goalY2 &&
                skierX2 <= goalX2) ||

                (skierX1 >= goalX1 &&
                        skierY2 >= goalY1 &&
                        skierY1 <= goalY2 &&
                        skierX1 <= goalX2);
    }

    protected ImageView getSkierImageView() {
        return skifahrer;
    }

    protected ImageView getRabbitImageView() {
        return rabbit;
    }

    protected ImageView getGoalImageView() {
        return goal;
    }

    private void openWinDialog() {
        stopEvent();

        Fragment frag = new LevelWinFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.level_win, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
        layoutWin.setVisibility(View.VISIBLE);
    }

    private void openLostDialog() {
        Log.d(TAG, "openLostDialog");
        stopEvent();
        if (life >= MAX_LIFE) {
            // maximale lebensanzahl erreicht. Zurück zur MainActivity
            life = 1;
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return;
        }

        Fragment frag = new LevelLostFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.level_lost, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
        layoutLost.setVisibility(View.VISIBLE);
    }

    protected void runSkier() {
        try {
            Log.d(TAG, "Run");
            //Log.d(TAG, "moveX: " + (skifahrer.getX() + moveX));


            if (isViewOverlapping(getSkierImageView(), getGoalImageView())) {
                Log.d(TAG, "Ziel erreicht");
                //openWinDialog();
                win = true;
            } else if (isViewOverlapping(getSkierImageView(), getRabbitImageView())) {
                Log.i(TAG, "Level verloren");
                //openLostDialog();
                lost = true;
            } else {
                skier.setSkierPositionX(getSkierImageView().getX());
                skier.setSkierPositionY(getSkierImageView().getY());
                                     /* if (skier.isYOutOfWindow()) {
                                          stopEvent();
                                          openLostDialog();
                                      } else {*/
                getSkierImageView().setX(skier.getX());
                getSkierImageView().setY(skier.getY());
                //}
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    protected void stopEvent() {
        scheduleFuture.cancel(true);
    };


}
