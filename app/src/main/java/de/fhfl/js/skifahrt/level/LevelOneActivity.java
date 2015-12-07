package de.fhfl.js.skifahrt.level;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import de.fhfl.js.skifahrt.movingObject.SkierMovingLevel1;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelOneActivity extends LevelActivity implements SensorEventListener {

    private String TAG = "LevelOneActivity";

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private WindowManager mWindowManager;
    private Display mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ansprechen des Sensor-Services
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Auswahl des Sensortyps
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Listenerregistrieren und Sensorsensibilität

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        // Get an instance of the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        skier = new SkierMovingLevel1();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        if (isViewOverlapping(getSkierImageView(), getGoalImageView())) {
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME));
            Log.d(TAG, "Ziel erreicht");
            openWinDialog();
        } else if (isViewOverlapping(getSkierImageView(), getRabbitImageView())) {
            Log.i(TAG, "Level verloren");
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME));
            openLostDialog();
        } else {
            // Landscape mode
            skier.setEventValuesToDimensions(event, mDisplay);
            skier.setSkierPositionX(getSkierImageView().getX());
            skier.setSkierPositionY(getSkierImageView().getY());
            getSkierImageView().setX(skier.getX());
            getSkierImageView().setY(skier.getY());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor s = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        super.onStop();
    }
}
