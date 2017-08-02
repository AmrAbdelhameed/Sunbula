package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.NotificationsFragmentAdapter;
import com.example.amr.sunbula.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> stringArrayList;
    private NotificationsFragmentAdapter adapter;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        stringArrayList = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            stringArrayList.add("Karen");
        }

        listView = (ListView) v.findViewById(R.id.list_item_notifications);

        adapter = new NotificationsFragmentAdapter(getActivity(), R.layout.item_in_notification, stringArrayList);
        listView.setAdapter(adapter);

        return v;
    }

}
