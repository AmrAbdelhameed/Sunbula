package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.amr.sunbula.Adapters.MessagesFragmentAdapter;
import com.example.amr.sunbula.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> stringArrayList;
    private MessagesFragmentAdapter adapter;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages, container, false);

        stringArrayList = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            stringArrayList.add("Amr Abdelhameed Abuelkhair");
        }

        listView = (ListView) v.findViewById(R.id.list_item_messages);

        adapter = new MessagesFragmentAdapter(getActivity(), R.layout.item_in_messages, stringArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
