package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amr.sunbula.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MycausesProfileFragment extends Fragment {

    public MycausesProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mycauses_profile, container, false);
    }

}
