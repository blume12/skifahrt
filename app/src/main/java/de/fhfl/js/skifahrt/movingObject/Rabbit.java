package de.fhfl.js.skifahrt.movingObject;

import android.util.Log;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class Rabbit extends MovingObject {
    private static final String TAG = "Rabbit";

    public void loadPosition() {
        Random rand = new Random();
        float max = maxWidth - 500;
        float min = maxWidth / 2;

        positionX = rand.nextFloat() * (max - min) + min;

        rand = new Random();
        max = maxHeight - 450;
        min = 50;

        positionY = rand.nextFloat() * (max - min) + min;
        Log.v(TAG, "Rabbit Position: " + positionX + "/" + positionY);
    }
}
