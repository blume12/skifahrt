package de.fhfl.js.skifahrt.level;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.fhfl.js.skifahrt.R;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelOneActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private WindowManager mWindowManager;
    private Display mDisplay;

    private TextView textview;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);


        //Ansprechen des Sensor-Services
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Auswahl des Sensortyps
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Listenerregistrieren und Sensorsensibilität

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        // Get an instance of the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        textview = (TextView) findViewById(R.id.textView1);
        relativeLayout = (RelativeLayout) findViewById(R.id.levelScreen);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_90:
                // Landscape mode
                SkierMoving skier = new SkierMoving(event);
                skier.setMaxHeight(relativeLayout.getHeight());
                skier.setMaxWidth(relativeLayout.getWidth());
                skier.setSkierHeight(textview.getLineHeight());
                skier.setSkierPositionX(textview.getX());
                skier.setSkierPositionY(textview.getY());
                textview.setX(skier.getX());
                textview.setY(skier.getY());
                break;
            case Surface.ROTATION_270:
                // Landscape mode
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
