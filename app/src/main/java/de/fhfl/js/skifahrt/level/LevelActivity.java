package de.fhfl.js.skifahrt.level;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import de.fhfl.js.skifahrt.MainActivity;
import de.fhfl.js.skifahrt.R;
import de.fhfl.js.skifahrt.movingObject.Rabbit;
import de.fhfl.js.skifahrt.movingObject.SkierMoving;

/**
 * Created by Jasmin on 19.11.2015.
 */
abstract public class LevelActivity extends AppCompatActivity implements LevelWinFragment.OnFragmentInteractionListener, LevelLostFragment.OnFragmentInteractionListener {


    private LinearLayout layoutWin;
    private LinearLayout layoutLost;

    private ImageView skifahrer;
    private ImageView rabbit;
    private Rabbit rabbitView;
    private ImageView goal;

    protected SkierMoving skier;

    private RelativeLayout relativeLayout;


    private boolean firstCall = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        layoutWin = (LinearLayout) findViewById(R.id.layoutFragmentWin);
        layoutLost = (LinearLayout) findViewById(R.id.layoutFragmentLost);

        skifahrer = (ImageView) findViewById(R.id.skifahrer);
        rabbit = (ImageView) findViewById(R.id.imageView3);
        goal = (ImageView) findViewById(R.id.goal);
        relativeLayout = (RelativeLayout) findViewById(R.id.levelScreen);
        rabbitView = new Rabbit();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && firstCall) {
            skier.setMaxHeight(relativeLayout.getHeight());
            skier.setMaxWidth(relativeLayout.getWidth());
            skier.setSkierHeight(skifahrer.getHeight());

            rabbitView.setMaxHeight(relativeLayout.getHeight());
            rabbitView.setMaxWidth(relativeLayout.getWidth());
            rabbitView.loadPosition();
            rabbit.setX(rabbitView.getPositionX());
            rabbit.setY(rabbitView.getPositionY());
            firstCall = false;
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


    public void reloadLevel(View view) {
        Intent intent = new Intent(this, this.getClass());
        this.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    protected boolean isViewOverlapping(View skier, View goal) {
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

    protected void openWinDialog() {
        layoutWin.setVisibility(View.VISIBLE);

        Fragment frag = new LevelWinFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.level_win, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    protected void openLostDialog() {
        layoutLost.setVisibility(View.VISIBLE);

        Fragment frag = new LevelLostFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.level_lost, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }


}
