package de.fhfl.js.skifahrt.movingObject;

import android.util.Log;

/**
 * Stellt die gemeinsamen Methoden für das Bewegen eines Objekts bereit.
 *
 * Created by Jasmin on 06.12.2015.
 */
abstract public class MovingObject {
    private static final String TAG = "MovingObject";

    protected float positionX;
    protected float positionY;

    protected float maxHeight;
    protected float maxWidth;

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public float getPositionX(){
        Log.d(TAG, "positionX: " + positionX);
        return positionX;
    }

    public float getPositionY(){
        Log.d(TAG, "positionY: "+positionY);
        return positionY;
    }


}
