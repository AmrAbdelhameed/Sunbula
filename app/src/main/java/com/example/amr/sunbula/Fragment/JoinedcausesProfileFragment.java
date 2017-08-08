package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.All_inProfileFragmentAdapter;
import com.example.amr.sunbula.Adapters.JoinedCases_inProfileFragmentAdapter;
import com.example.amr.sunbula.Adapters.MyCauses_inProfileFragmentAdapter;
import com.example.amr.sunbula.Models.DBFlowModels.JoinedCasesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.MyCausesProfile;
import com.example.amr.sunbula.Models.DBFlowWrappers.JoinedCausesProfileWrapper;
import com.example.amr.sunbula.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedcausesProfileFragment extends Fragment {

    private ListView listView;
    List<JoinedCausesProfileWrapper> joinedCasesProfiles;
    private JoinedCases_inProfileFragmentAdapter adapter;

    public JoinedcausesProfileFragment(List<JoinedCausesProfileWrapper> joinedCasesProfiles) {
        this.joinedCasesProfiles = joinedCasesProfiles;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_joined_profile, container, false);

        listView = (ListView) v.findViewById(R.id.list_item_joined);

        adapter = new JoinedCases_inProfileFragmentAdapter(getActivity(), joinedCasesProfiles);
        listView.setAdapter(adapter);

        return v;
    }

}
