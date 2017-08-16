package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.amr.sunbula.Adapters.All_inProfileFragmentAdapter;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowModels.AllCausesProfile;
import com.example.amr.sunbula.Models.DBFlowWrappers.AllCausesProfileWrapper;
import com.example.amr.sunbula.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProfileFragment extends Fragment {

    private ListView listView;
    List<AllCausesProfileWrapper> allCasesListBeen;
    private All_inProfileFragmentAdapter adapter;

    public AllProfileFragment(List<AllCausesProfileWrapper> allCasesListBeen) {
        this.allCasesListBeen = allCasesListBeen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_profile, container, false);

        listView = (ListView) v.findViewById(R.id.list_item_all);

        adapter = new All_inProfileFragmentAdapter(getActivity(), allCasesListBeen);
        listView.setDivider(null);
        listView.setAdapter(adapter);

        return v;
    }
}
