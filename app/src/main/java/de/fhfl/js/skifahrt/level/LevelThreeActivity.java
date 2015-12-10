package de.fhfl.js.skifahrt.level;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import de.fhfl.js.skifahrt.R;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelThreeActivity extends LevelActivity {

    public ImageView skifahrer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skifahrer = (ImageView) findViewById(R.id.skifahrer);
    }


}
