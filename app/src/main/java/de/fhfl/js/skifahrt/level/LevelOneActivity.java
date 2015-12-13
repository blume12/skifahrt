package de.fhfl.js.skifahrt.level;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelOneActivity extends LevelActivity implements SensorEventListener {

    private static final String TAG = "LevelOneActivity";

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
    }

    @Override
    protected void stopEvent() {
        super.stopEvent();
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }
        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_90:
            case Surface.ROTATION_180:
                skier.setMoveX(event.values[1]);
                skier.setMoveY(event.values[0]);
                break;
            case Surface.ROTATION_270:
            case Surface.ROTATION_0:
                skier.setMoveX(event.values[0]);
                skier.setMoveX(event.values[1]);
                break;
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
