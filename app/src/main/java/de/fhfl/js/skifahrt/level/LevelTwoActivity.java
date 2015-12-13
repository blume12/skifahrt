package de.fhfl.js.skifahrt.level;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Stellt die Funktionen für die Sprachsteuerung bereit.
 * <p/>
 * Created by Admin on 24.11.2015.
 */
public class LevelTwoActivity extends LevelActivity implements RecognitionListener {

    private static final String TAG = "LevelTwoActivity";

    private SpeechRecognizer speechRecognizer = null;
    private Intent recognizerIntent;

    /**
     * Erstellt bei ersten Aufrufen der Activity die Sprachsteuerung.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "de");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        speechRecognizer.startListening(recognizerIntent);
    }

    /**
     * Stoppt alle offenen Events.
     */
    @Override
    protected void stopEvent() {
        super.stopEvent();
        speechRecognizer.stopListening();
    }

    /**
     * Beim Pausieren der Activity wird die Sprachsteuerung zerstört.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
            Log.i(TAG, "destroy");
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(TAG, "onBufferReceived");
    }

    /**
     * Beim Ende des Sprechens wird sofort nochmal die Sprachsteuerung gestartet.
     */
    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");
        speechRecognizer.startListening(recognizerIntent);
    }

    /**
     * Gibt den ErrorCode aus.
     * @param errorCode int
     */
    @Override
    public void onError(int errorCode) {
        Log.e(TAG, "onError: " + errorCode);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(TAG, "onPartialResults");
        speechRecognizer.startListening(recognizerIntent);
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(TAG, "onReadyForSpeech");
    }

    /**
     * Überprüft die Ergebenisse der Spracheingabe, ob ein richtiges Wort gesagt wurde und
     * setzt dann die Werte für die Bewegung des Skifahrers.
     *
     * @param results
     */
    @Override
    public void onResults(Bundle results) {
        Log.i(TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        String firstCase = matches.get(0);
        if(firstCase != null) {
            if (firstCase.equalsIgnoreCase("hoch") || firstCase.equalsIgnoreCase("koch") || firstCase.equalsIgnoreCase("^")) {
                Log.i(TAG, "hooch");
                skier.setMoveY(-0.2F);
            } else if (firstCase.equalsIgnoreCase("runter") || firstCase.equalsIgnoreCase("unter") || firstCase.equalsIgnoreCase("fronter")) {
                Log.i(TAG, "ruunter");
                skier.setMoveY(0.2F);
            } else if (firstCase.equalsIgnoreCase("rechts") || firstCase.equalsIgnoreCase("recht")) {
                Log.i(TAG, "rechts");
                skier.setMoveY(0);
            }
        }
        speechRecognizer.startListening(recognizerIntent);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(TAG, "onRmsChanged");
    }
}