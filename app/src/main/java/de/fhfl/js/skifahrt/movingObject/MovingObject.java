package de.fhfl.js.skifahrt.movingObject;

import android.util.Log;

/**
 * Stellt die gemeinsamen Methoden für das Bewegen eines Objekts bereit.
 * <p/>
 * Created by Jasmin on 06.12.2015.
 */
abstract public class MovingObject {
    private static final String TAG = "MovingObject";

    protected float positionX;
    protected float positionY;

    protected float maxHeight;
    protected float maxWidth;

    /**
     * Setzt die Maximale Höhe des BewegungsObjektes.
     *
     * @param maxHeight float
     */
    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * Setzt die Maximale Breite des BewegungsObjektes.
     *
     * @param maxWidth float
     */
    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Setzt die X-Position des BewegungsObjektes.
     *
     * @return float
     */
    public float getPositionX() {
        Log.d(TAG, "positionX: " + positionX);
        return positionX;
    }

    /**
     * Setzt die Y-Position des BewegungsObjektes.
     *
     * @return float
     */
    public float getPositionY() {
        Log.d(TAG, "positionY: " + positionY);
        return positionY;
    }
}
