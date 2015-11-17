package de.fhfl.js.skifahrt.level;

import android.hardware.SensorEvent;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class SkierMoving {

    private String TAG = "SkierMoving";

    private float mSensorX;
    private float mSensorY;

    private float skierHeight;

    private float skierPositionX;
    private float skierPositionY;

    private int maxHeight;
    private int maxWidth;

    public SkierMoving (SensorEvent event) {
        mSensorX = event.values[1];
        mSensorY = event.values[0];
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }


    public void setSkierHeight(int height) {
        skierHeight = height;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setSkierPositionX(float skierPositionX) {
        this.skierPositionX = skierPositionX;
    }

    public void setSkierPositionY(float skierPositionY) {
        this.skierPositionY = skierPositionY;
    }

    public float getX(){
        if(mSensorX > 0) {
            return mSensorX+skierPositionX;
        }
        return skierPositionX+(float) 0.1;
    }

    public float getY(){
        Log.d(TAG, "skierPositionY: "+skierPositionY);
        Log.d(TAG, "maxHeight: "+(maxHeight-skierHeight));
        if(skierPositionY <= 0.0) {
            return (float) 1.0;
        } else if (skierPositionY > maxHeight-skierHeight) {
            return maxHeight-skierHeight-1;
        }
        return mSensorY+skierPositionY;
    }
}

