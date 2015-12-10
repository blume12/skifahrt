package de.fhfl.js.skifahrt.movingObject;

import android.hardware.SensorEvent;
import android.view.Display;

/**
 * Created by Jasmin on 06.12.2015.
 */
abstract public class SkierMoving extends MovingObject {

    private String TAG = "SkierMoving";

    protected float mSensorX;
    protected float mSensorY;

    private float speed = 0.0F;

    private float skierHeight;

    private float skierPositionX;
    private float skierPositionY;

    public void setSkierHeight(int height) {
        skierHeight = height;
    }

    public void setSkierPositionX(float skierPositionX) {
        this.skierPositionX = skierPositionX;
    }

    public void setSkierPositionY(float skierPositionY) {
        this.skierPositionY = skierPositionY;
    }

    public float getX() {
        if (mSensorX > 0) {
            return mSensorX + skierPositionX;
        }
        return skierPositionX + speed;
    }

    public float getY() {
        // Log.d(TAG, "skierPositionY: " + skierPositionY);
        // Log.d(TAG, "maxHeight: " + (maxHeight - skierHeight));
        if (skierPositionY <= 0.0) {
            return (float) 1.0;
        } else if (skierPositionY > maxHeight - skierHeight) {
            return maxHeight - skierHeight - speed;
        }
        return mSensorY + skierPositionY;
    }

    public void setEventValuesToDimensions(SensorEvent event, Display display) {

    }

    public void addToSpeed(int levelStep) {
        speed = speed + levelStep;
    }

}
