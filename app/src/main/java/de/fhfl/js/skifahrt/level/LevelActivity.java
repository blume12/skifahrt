package de.fhfl.js.skifahrt.level;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Jasmin on 19.11.2015.
 */
abstract public class LevelActivity extends AppCompatActivity implements LevelWinFragment.OnFragmentInteractionListener, LevelLostFragment.OnFragmentInteractionListener{


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

        // firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        skier.getLocationOnScreen(firstPosition);
        goal.getLocationOnScreen(secondPosition);

        int skierX = firstPosition[0];
        int skierY = skier.getMeasuredHeight() + firstPosition[1];
        int goalX = secondPosition[0];
        int goalY = secondPosition[1];
        return (skierX >= goalX && skierX <= goalX+goal.getMeasuredWidth()&& skierY >= goalY && skierY <= goalY+goal.getMeasuredHeight());

    }

}
