package de.fhfl.js.skifahrt.level.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhfl.js.skifahrt.R;

/**
 * Stellt ein Fragment für den Popup dar, wenn verloren wird.
 */
public class LevelLostFragment extends Fragment {

    /**
     * Erstellt eine View für das Fragment. Es wird die XML-Datei für das Verloren-Popup bereitgestellt.
     *
     * @param inflater           Inflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_lost, container, false);
    }

}
