package de.fhfl.js.skifahrt.level;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import de.fhfl.js.skifahrt.R;

/**
 * Created by Jasmin on 17.11.2015.
 */
public class LevelTwoActivity extends AppCompatActivity implements OnClickListener {


    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
   // startActivityForResult(intent, RESULT_SPEECH);
    protected static final int REQUEST_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        findViewById(R.id.btnSpeak).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.txtSpeechInput)).setText(thingsYouSaid.get(0));
        }
    }
}
  //  @Override
  //  protected void onCreate(Bundle savedInstanceState) {
  //      super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_level);
  //  }


//}
