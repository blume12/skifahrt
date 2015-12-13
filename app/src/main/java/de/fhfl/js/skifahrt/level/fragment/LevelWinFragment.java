package de.fhfl.js.skifahrt.level.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhfl.js.skifahrt.R;

/**
 * Stellt ein Fragment für den Popup dar, wenn gewonnen wird.
 * <p/>
 * Created by Jasmin on 24.11.2015.
 */
public class LevelWinFragment extends Fragment {

    /**
     * Erstellt eine View für das Fragment. Es wird die XML-Datei für das Gewonnen-Popup bereitgestellt.
     *
     * @param inflater           Inflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_win, container, false);
    }
}
