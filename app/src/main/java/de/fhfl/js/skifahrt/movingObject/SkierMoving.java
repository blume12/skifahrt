package de.fhfl.js.skifahrt.movingObject;

import android.util.Log;
/**
 * Bewegt den Skifahrer an die nächste Position.
 *
 * Created by Jasmin on 06.12.2015.
 */
public class SkierMoving extends MovingObject {

    private static final String TAG = "SkierMoving";

    protected float moveX = 0.05F;
    protected float moveY;

    private float speed = 0.0F;

    private float skierHeight;

    private float skierPositionX;
    private float skierPositionY;

    /**
     * Setzt die Höhe des Skifahrers.
     *
     * @param height int
     */
    public void setSkierHeight(int height) {
        skierHeight = height;
    }

    /**
     * Setzt die Position X des Skifahrers.
     *
     * @param skierPositionX float
     */
    public void setSkierPositionX(float skierPositionX) {
        this.skierPositionX = skierPositionX;
    }

    /**
     * Setzt die Position Y des Skifahrers.
     *
     * @param skierPositionY float
     */
    public void setSkierPositionY(float skierPositionY) {
        this.skierPositionY = skierPositionY;
    }

    /**
     * Gibt den berechneten neuen X-Wert zurück.
     *
     * @return float
     */
    public float getX() {
        if (moveX > 0) {
            return moveX + skierPositionX + speed;
        }
        return skierPositionX + speed;
    }
    /**
     * Gibt den berechneten neuen Y-Wert zurück.
     *
     * @return float
     */
    public float getY() {
        // Log.d(TAG, "skierPositionY: " + skierPositionY);
        // Log.d(TAG, "maxHeight: " + (maxHeight - skierHeight));
        if (skierPositionY <= 0.0) {
            return (float) 1.0;
        } else if (skierPositionY > maxHeight - skierHeight) {
            return maxHeight - skierHeight - speed;
        }
        return moveY + skierPositionY;
    }

    /**
     * Berechnet die neue Geschwindigkeit abghängig von der Geschwindigkeit.
     *
     * @param levelStep int
     */
    public void addToSpeed(int levelStep) {

        Log.v(TAG, "addToSpeed");
        speed = speed + (levelStep *0.1F);
    }

    /**
     * Setzt den Wert, den die Position X verschiebt.
     *
     * @param moveX float
     */
    public void setMoveX(float moveX) {
        this.moveX = moveX;
    }

    /**
     * Setzt den Wert, den die Position Y verschiebt.
     *
     * @param moveY float
     */
    public void setMoveY(float moveY) {
        this.moveY = moveY;
    }

}
