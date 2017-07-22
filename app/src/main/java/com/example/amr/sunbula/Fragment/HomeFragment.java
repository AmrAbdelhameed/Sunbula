package com.example.amr.sunbula.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.amr.sunbula.HomeFragmentAdapter;
import com.example.amr.sunbula.R;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> stringArrayList;
    private HomeFragmentAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("News Feed");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setData();

        listView = (ListView) v.findViewById(R.id.list_item);

        adapter = new HomeFragmentAdapter(getActivity(), R.layout.item_in_home, stringArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void setData() {
        stringArrayList = new ArrayList<>();

        stringArrayList.add("Amr");
        stringArrayList.add("Karim");
        stringArrayList.add("Samy");
        stringArrayList.add("Dalia");
        stringArrayList.add("Heba");
        stringArrayList.add("Mahmoud");
        stringArrayList.add("Ziad");
        stringArrayList.add("Mariam");
        stringArrayList.add("Ahmed");
        stringArrayList.add("Youssef");
        stringArrayList.add("Mohammed");
        stringArrayList.add("Samira");


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.filter("");
                    listView.clearTextFilter();
                } else {
                    adapter.filter(newText);
                }
                return true;
            }
        });
    }
}
