package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    ListView list_following;
    public FollowingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_following, container, false);

        list_following = (ListView)v.findViewById(R.id.list_item_following);

        Toast.makeText(getActivity(), "Following", Toast.LENGTH_SHORT).show();

        return v;
    }
}
