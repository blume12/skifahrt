package de.fhfl.js.skifahrt.level;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.Timer;
import java.util.logging.Handler;

import de.fhfl.js.skifahrt.R;
import de.fhfl.js.skifahrt.movingObject.SkierMovingLevel2;


/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelThreeActivity extends LevelActivity  {

    public ImageView skifahrer;
    public TextView gestureEvent;
    String TAG = "adfs";
    Timer timer;
    private CountDownTimer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureEvent = (TextView)findViewById(R.id.txtSpeechInput);
        skifahrer = (ImageView)findViewById(R.id.skifahrer);
        skier = new SkierMovingLevel2();

        myTimer = new CountDownTimer(10*1000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "CountDownTimer.onTick(): sUntilFinished:" + millisUntilFinished / 1000);

                gestureEvent.setText("" + (Math.ceil(millisUntilFinished/1000d)));
                skifahrer.setX(skifahrer.getX() +10);

            }

            @Override
            public void onFinish() {
                Log.v(TAG, "CountDownTimer.onFinish()");


                gestureEvent.setText("0");

            }
        } .start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }

    SimpleOnGestureListener simpleOnGestureListener
            = new SimpleOnGestureListener(){


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            String swipe = "";
            float sensitvity = 50;

            // TODO Auto-generated method stub
            if((e1.getX() - e2.getX()) > sensitvity){
                swipe += "Swipe Left\n";
                skifahrer.setX(skifahrer.getX() -100);

            }else if((e2.getX() - e1.getX()) > sensitvity){
                swipe += "Swipe Right\n";
                skifahrer.setX(skifahrer.getX() +100);
            }else{
                swipe += "\n";
            }

            if((e1.getY() - e2.getY()) > sensitvity){
                swipe += "Swipe Up\n";

                skifahrer.setY(skifahrer.getY() -100);

            }else if((e2.getY() - e1.getY()) > sensitvity){
                swipe += "Swipe Down\n";
                skifahrer.setY(skifahrer.getY() +100);

            }else{
                swipe += "\n";
            }

            gestureEvent.setText(swipe);

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);



}
