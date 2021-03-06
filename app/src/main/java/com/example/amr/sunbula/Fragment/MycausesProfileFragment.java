package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.amr.sunbula.Adapters.MyCauses_inProfileFragmentAdapter;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowModels.MyCausesProfile;
import com.example.amr.sunbula.Models.DBFlowWrappers.MyCausesProfileWrapper;
import com.example.amr.sunbula.R;
import com.google.firebase.crash.FirebaseCrash;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MycausesProfileFragment extends Fragment {

    private ListView listView;
    List<MyCausesProfileWrapper> myCasesBeanList;
    private MyCauses_inProfileFragmentAdapter adapter;

    public MycausesProfileFragment(List<MyCausesProfileWrapper> myCasesBeanList) {
        this.myCasesBeanList = myCasesBeanList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mycauses_profile, container, false);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        listView = (ListView) v.findViewById(R.id.list_item_mycauses);

        adapter = new MyCauses_inProfileFragmentAdapter(getActivity(), myCasesBeanList);
        listView.setDivider(null);
        listView.setAdapter(adapter);

        return v;
    }
}
