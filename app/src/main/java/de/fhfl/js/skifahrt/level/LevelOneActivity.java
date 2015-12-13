package de.fhfl.js.skifahrt.level;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Stellt die Sensoren und Aktivitäten für das erste Level bereit
 * <p/>
 * Created by Jasmin on 17.11.2015.
 */
public class LevelOneActivity extends LevelActivity implements SensorEventListener {

    private static final String TAG = "LevelOneActivity";

    private SensorManager sensorManager;
    private Display mDisplay;

    /**
     * Erstellt die Activity des ersten Levels.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        // Ansprechen des Sensor-Services
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Auswahl des Sensortyps
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Listener registrieren und Sensorsensibilität
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        // Instance von dem WindowManager holen, um die Displaygröße auszulesen
        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
    }

    /**
     * Schliesst alle offenen Events für das Level
     */
    @Override
    protected void stopEvent() {
        super.stopEvent();
        Log.v(TAG, "stopEvent");
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME));
    }

    /**
     * Handelt die Parameter, wenn sich die Lagesensoeren verändern. Die Werte werden dann der Klasse
     * SkierMoving übermittelt.
     *
     * @param event SensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v(TAG, "onSensorChanged");
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }
        float moveX = 0.0F;
        float moveY = 0.0F;
        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_90:
            case Surface.ROTATION_180:
                moveX = event.values[1];
                moveY = event.values[0];
                break;
            case Surface.ROTATION_270:
            case Surface.ROTATION_0:
                moveX = event.values[0];
                moveY = event.values[1];
                break;
        }

        moveX = moveX/10;
        moveY = moveY/10;
        Log.d(TAG, "MoveX: " + moveX + ", MoveY: " + moveY);
        skier.setMoveX(moveX);
        skier.setMoveY(moveY);
    }

    /**
     * Wird aufgreufen, wenn sich der Sensor geändert hat. Zusätzlich wird hier noch ein
     * Genauigkeitswert übergeben.
     *
     * @param sensor   Sensor
     * @param accuracy int
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.v(TAG, "onAccuracyChanged");
    }

    /**
     * Wird aufgerufen, wenn die Activity fortgesetzt wird. Falls ein Sensor noch da ist, wird
     * dieser nochmal bneu gesetzt.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor s = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * Beim Pausieren der App wird der Sensor gelöscht und die App gestoppt.
     */
    @Override
    protected void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
