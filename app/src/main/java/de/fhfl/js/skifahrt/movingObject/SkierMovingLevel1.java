package de.fhfl.js.skifahrt.movingObject;

import android.hardware.SensorEvent;
import android.view.Display;
import android.view.Surface;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class SkierMovingLevel1 extends SkierMoving {

    public void setEventValuesToDimensions(SensorEvent event, Display display) {
        switch (display.getRotation()) {
            case Surface.ROTATION_90:
            case Surface.ROTATION_180:
                mSensorX = event.values[1];
                mSensorY = event.values[0];
                break;
            case Surface.ROTATION_270:
            case Surface.ROTATION_0:
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                break;
        }
    }
}

