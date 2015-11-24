package de.fhfl.js.skifahrt.level;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Jasmin on 19.11.2015.
 */
abstract public class LevelActivity extends AppCompatActivity implements LevelLostFragment.OnFragmentInteractionListener{


    public void reloadLevel(View view) {
        Intent intent = new Intent(this, this.getClass());
        this.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    protected boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        int r = firstView.getMeasuredWidth() + firstPosition[0];
        int l = secondPosition[0];
        return r >= l && (r != 0 && l != 0);
    }

}
