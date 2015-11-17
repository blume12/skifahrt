package de.fhfl.js.skifahrt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.fhfl.js.skifahrt.level.LevelFourActivity;
import de.fhfl.js.skifahrt.level.LevelOneActivity;
import de.fhfl.js.skifahrt.level.LevelThreeActivity;
import de.fhfl.js.skifahrt.level.LevelTwoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLevelOne(View view) {
        Intent intent = new Intent(this, LevelOneActivity.class);
        this.startActivity(intent);
    }

    public void openLevelTwo(View view) {
        Intent intent = new Intent(this, LevelTwoActivity.class);

    }

    public void openLevelThree(View view) {
        Intent intent = new Intent(this, LevelThreeActivity.class);
    }

    public void openLevelFour(View view) {
        Intent intent = new Intent(this, LevelFourActivity.class);
    }
}
