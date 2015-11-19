package de.fhfl.js.skifahrt.level;

import android.util.Log;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class Rabbit {
    private static final String TAG = "Rabbit";

    private float positionX;
    private float positionY;

    private float maxHeight;
    private float maxWidth;

    public Rabbit() {

    }


    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void loadPosition() {
        Random rand = new Random();
        float max = maxWidth-50;
        float min = 50;

        positionX = rand.nextFloat() * (max - min) + min;

        rand = new Random();
        max = maxHeight-50;
        min = 50;

        positionY = rand.nextFloat() * (max - min) + min;
    }

    public float getPositionX(){
        Log.d(TAG, "positionX: "+positionX);
        return positionX;
    }

    public float getPositionY(){
        Log.d(TAG, "positionY: "+positionY);
        return positionY;
    }

    public boolean checkIfSkierCollide(float rabbitWidth, float rabbitHeight,float skierWidth, float skierHeight) {
        if(skierWidth >= positionX && skierWidth <= positionX+rabbitWidth && skierHeight >= positionY && skierHeight <= positionY+rabbitHeight) {
            return true;
        }
        return false;
    }


}
