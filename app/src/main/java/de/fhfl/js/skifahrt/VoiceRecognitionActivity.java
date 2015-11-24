package de.fhfl.js.skifahrt;

/**
 * Created by Admin on 24.11.2015.
 */
import java.util.ArrayList;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import de.fhfl.js.skifahrt.level.SkierMoving;

public class VoiceRecognitionActivity extends AppCompatActivity implements
        RecognitionListener {

    public TextView returnedText;
    private SpeechRecognizer speechRecognizer = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

  /**  private void setPartialResult(String[] results) {
        returnedText.setText(TextUtils.join(" · ", results));
    } */
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
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


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
    /**   String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
        toggleButton.setChecked(false);*/
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
        for (String result : matches) text += result + "\n";
        /**   Log.i("onPartialResults: keySet: " + partialResults.keySet());
        String[] results = partialResults.getStringArray("com.google.android.voicesearch.UNSUPPORTED_PARTIAL_RESULTS");
        //double[] resultsConfidence = partialResults.getDoubleArray("com.google.android.voicesearch.UNSUPPORTED_PARTIAL_RESULTS_CONFIDENCE");
        if (results != null) {
            setPartialResult(results);
        }*/
        returnedText.setText(text);


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
        /**  Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
         */
    }
   /** public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
*/
}