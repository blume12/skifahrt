package de.fhfl.js.skifahrt.level;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import de.fhfl.js.skifahrt.R;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelOneActivity extends AppCompatActivity implements SensorEventListener {

    private String TAG = "LevelOneActivity";

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private WindowManager mWindowManager;
    private Display mDisplay;

    private ImageView skifahrer;
    private ImageView rabbit;
    private Rabbit rabbitView;
    private SkierMoving skier;
    private RelativeLayout relativeLayout;
    private boolean firstCall = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);


        //Ansprechen des Sensor-Services
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Auswahl des Sensortyps
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Listenerregistrieren und Sensorsensibilität

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        // Get an instance of the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        skifahrer = (ImageView) findViewById(R.id.skifahrer);
        rabbit = (ImageView) findViewById(R.id.imageView3);
        relativeLayout = (RelativeLayout) findViewById(R.id.levelScreen);
        rabbitView = new Rabbit();
        skier = new SkierMoving();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && firstCall) {
            skier.setMaxHeight(relativeLayout.getHeight());
            skier.setMaxWidth(relativeLayout.getWidth());
            skier.setSkierHeight(skifahrer.getHeight());

            Log.d(TAG, "TEST: " + relativeLayout.getHeight());
            rabbitView.setMaxHeight(relativeLayout.getHeight());
            rabbitView.setMaxWidth(relativeLayout.getWidth());
            rabbitView.loadPosition();
            rabbit.setX(rabbitView.getPositionX());
            rabbit.setY(rabbitView.getPositionY());
            firstCall = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        // Landscape mode
        skier.setEventValuesToDimensions(event, mDisplay);
        skier.setSkierPositionX(skifahrer.getX());
        skier.setSkierPositionY(skifahrer.getY());
        skifahrer.setX(skier.getX());
        skifahrer.setY(skier.getY());

        if(rabbitView.checkIfSkierCollide(rabbit.getWidth(), rabbit.getHeight(), skifahrer.getWidth(), skifahrer.getHeight())) {
            Log.d(TAG, "Level verloren");
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
