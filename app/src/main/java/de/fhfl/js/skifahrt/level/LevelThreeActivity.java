package de.fhfl.js.skifahrt.level;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * Stellt die Funktionen für das Wischen des Skifahrers bereit.
 *
 * Created by Jasmin on 17.11.2015.
 */
public class LevelThreeActivity extends LevelActivity {

    private static final String TAG = "LevelThreeActivity";

    /**
     * Initalisiert die benötigten Events für das TouchEvent.
     *
     * @param event MotionEvent
     * @return GestureDetector
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG, "onTouchEvent");
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Überprüft, in welche Richtung geswiped wird und setzt dann die Werte, in welche Richtung der
     * Skifahrer fahren soll für das SkierMoving
     */
    SimpleOnGestureListener simpleOnGestureListener
            = new SimpleOnGestureListener() {


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float sensitvity = 50;

            if ((e2.getX() - e1.getX()) > sensitvity) {
                skier.setMoveY(0);
            }
            if ((e1.getY() - e2.getY()) > sensitvity) {
                skier.setMoveY(-0.2F);
            } else if ((e2.getY() - e1.getY()) > sensitvity) {
                skier.setMoveY(0.2F);
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);
}
