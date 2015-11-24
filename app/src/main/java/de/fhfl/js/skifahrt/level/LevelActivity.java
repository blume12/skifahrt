package de.fhfl.js.skifahrt.level;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Jasmin on 19.11.2015.
 */
abstract public class LevelActivity extends AppCompatActivity implements LevelWinFragment.OnFragmentInteractionListener, LevelLostFragment.OnFragmentInteractionListener {


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
        int skierX2 = firstPosition[0]+skier.getMeasuredWidth();
        int skierY1 = firstPosition[1];
        int skierY2 = firstPosition[1] + skier.getMeasuredHeight();
        int goalX1 = secondPosition[0];
        int goalX2 = secondPosition[0] +  goal.getMeasuredHeight();
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

}
