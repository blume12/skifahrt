package de.fhfl.js.skifahrt.level;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 24.11.2015.
 */

import de.fhfl.js.skifahrt.R;
import de.fhfl.js.skifahrt.movingObject.SkierMovingLevel2;


public class LevelTwoActivity extends LevelActivity implements
        RecognitionListener {

    public TextView returnedText;
    public ImageView skifahrer;
    private SpeechRecognizer speechRecognizer = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    TranslateAnimation animation = new TranslateAnimation(0, 1800 , 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnedText = (TextView) findViewById(R.id.txtSpeechInput);
        skifahrer = (ImageView) findViewById(R.id.skifahrer);

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
        skier = new SkierMovingLevel2();

        //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(10000);  // animation duration
        animation.setRepeatCount(0);  // animation repeat count
        animation.setRepeatMode(0);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);

        skifahrer.startAnimation(animation);
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
      for (String result : matches) text += result + "\n";

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
        for (String result : matches) {
            text += result + "\n";
        }


            returnedText.setText(text);

        if (matches.get(0).toString().equalsIgnoreCase("hoch") || matches.get(0).toString().equalsIgnoreCase("koch") || matches.get(0).toString().equalsIgnoreCase("^")) {
            Log.i(LOG_TAG, "hooch");
            //getSkierImageView().setX(skier.getX());
            //getSkierImageView().setY(skier.getY());
           // skifahrer.setY(skifahrer.getY() -100);
            //skifahrer.setX(skifahrer.getX() + 100);
           TranslateAnimation animation = new TranslateAnimation(skifahrer.getX(),skifahrer.getX()+50 , skifahrer.getY(), skifahrer.getY()+50);
            skifahrer.startAnimation(animation);
        }

        if (matches.get(0).toString().equalsIgnoreCase("runter") || matches.get(0).toString().equalsIgnoreCase("unter") || matches.get(0).toString().equalsIgnoreCase("fronter")) {
            Log.i(LOG_TAG, "ruunter");
            //getSkierImageView().setX(skier.getX());
            //getSkierImageView().setY(skier.getY());
            skifahrer.setY(skifahrer.getY() + 100);
            skifahrer.setX(skifahrer.getX() + 100);
        }




            speechRecognizer.startListening(recognizerIntent);




        //}
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

}