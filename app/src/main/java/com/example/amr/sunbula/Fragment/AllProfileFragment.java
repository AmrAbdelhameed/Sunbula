package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.All_inProfileFragmentAdapter;
import com.example.amr.sunbula.Adapters.NotificationsFragmentAdapter;
import com.example.amr.sunbula.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProfileFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> stringArrayList;
    private All_inProfileFragmentAdapter adapter;

    public AllProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_profile, container, false);

        stringArrayList = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            stringArrayList.add("All");
        }

        listView = (ListView) v.findViewById(R.id.list_item_all);

        adapter = new All_inProfileFragmentAdapter(getActivity(), R.layout.item_in_bottom_profile, stringArrayList);
        listView.setAdapter(adapter);

        return v;
    }

}
