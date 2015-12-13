package de.fhfl.js.skifahrt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import de.fhfl.js.skifahrt.level.LevelFourActivity;
import de.fhfl.js.skifahrt.level.LevelOneActivity;
import de.fhfl.js.skifahrt.level.LevelThreeActivity;
import de.fhfl.js.skifahrt.level.LevelTwoActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * Startet als erste Activity der App. Es handelt die einzelnen Levels.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Wird aufgerufen, wenn die App zerstört wird aber noch nicht vollständig beendet ist
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    /**
     * Wird aufgerufen, wenn die App beendet wird.
     */
    @Override
    public void onStop() {
        super.onStop();
        finish();
    }

    /**
     * Startet die Activity vom ersten Level
     *
     * @param view View
     */
    public void openLevelOne(View view) {
        Log.v(TAG, "openLevelOne");
        Intent intent = new Intent(this, LevelOneActivity.class);
        this.startActivity(intent);
    }

    /**
     * Startet die Activity vom zweiten Level
     *
     * @param view View
     */
    public void openLevelTwo(View view) {
        Log.v(TAG, "openLevelTwo");
        Intent intent = new Intent(this, LevelTwoActivity.class);
        this.startActivity(intent);

    }

    /**
     * Startet die Activity vom dritten Level
     *
     * @param view View
     */
    public void openLevelThree(View view) {
        Log.v(TAG, "openLevelThree");
        Intent intent = new Intent(this, LevelThreeActivity.class);
        this.startActivity(intent);
    }

    /**
     * Startet die Activity vom vierten Level
     *
     * @param view View
     */
    public void openLevelFour(View view) {
        Log.v(TAG, "openLevelFour");
        Intent intent = new Intent(this, LevelFourActivity.class);
        this.startActivity(intent);
    }
}
