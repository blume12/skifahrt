package de.fhfl.js.skifahrt.movingObject;

import android.util.Log;

/**
 * Bewegt den Skifahrer an die nächste Position.
 *
 * Created by Jasmin on 06.12.2015.
 */
public class SkierMoving extends MovingObject {

    private String TAG = "SkierMoving";

    protected float moveX = 0.05F;
    protected float moveY;

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
        if (moveX > 0) {
            return moveX + skierPositionX + speed;
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
        return moveY + skierPositionY;
    }

    public void addToSpeed(int levelStep) {
        speed = speed + (levelStep *0.1F);
    }

    public void setMoveX(float moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(float moveY) {
        this.moveY = moveY;
    }

}
