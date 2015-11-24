package de.fhfl.js.skifahrt.level;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;





/**
 * Created by Admin on 24.11.2015.
 */




import de.fhfl.js.skifahrt.R;


public class LevelTwoActivity extends AppCompatActivity implements
        RecognitionListener {

    public TextView returnedText;
    private SpeechRecognizer speechRecognizer = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        returnedText = (TextView) findViewById(R.id.txtSpeechInput);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "de");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        // recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


        speechRecognizer.startListening(recognizerIntent);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");

        speechRecognizer.startListening(recognizerIntent);
    }

    @Override
    public void onError(int errorCode) {

    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
        ArrayList<String> matches = arg0.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
      //  for (String result : matches) text += result + "\n";

        returnedText.setText(text);
        speechRecognizer.startListening(recognizerIntent);


    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

        returnedText.setText(text);
        speechRecognizer.startListening(recognizerIntent);

        // if(matches.toString().equalsIgnoreCase("rechts") || matches.toString().equalsIgnoreCase("brechts") || matches.toString().equalsIgnoreCase("reichts"){

        //}
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

}