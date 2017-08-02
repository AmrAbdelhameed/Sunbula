package com.example.amr.sunbula;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.HomeFragmentAdapter;
import com.example.amr.sunbula.Adapters.NotificationsFragmentAdapter;
import com.example.amr.sunbula.Adapters.SearchCauses_PeopleAdapter;

import java.util.ArrayList;

public class SearchCauses_People extends AppCompatActivity {

    Button btn_cause, btn_people;
    ImageView btn_search;
    EditText text_search;
    private ListView listView;
    private ArrayList<String> stringArrayList;
    private SearchCauses_PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_causes__people);

        btn_cause = (Button) findViewById(R.id.button_cause);
        btn_people = (Button) findViewById(R.id.button_people);
        text_search = (EditText) findViewById(R.id.text_search);
        btn_search = (ImageView) findViewById(R.id.btn_search);

        Action_cause();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchCauses_People.this, text_search.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        stringArrayList = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            stringArrayList.add("Kitchenstories");
        }

        listView = (ListView) findViewById(R.id.list_search_causes);

        adapter = new SearchCauses_PeopleAdapter(SearchCauses_People.this, R.layout.item_in_search_causes, stringArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchCauses_People.this, (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });

        btn_cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Action_cause();
            }
        });

        btn_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Action_people();
            }
        });
    }

    private void Action_cause() {
        btn_cause.setBackgroundResource(R.drawable.first_search_shape);
        btn_people.setBackgroundResource(R.drawable.second_first_search_shape);

        btn_cause.setTextColor(getApplication().getResources().getColor(R.color.tab_enable));
        btn_people.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
    }

    private void Action_people() {
        btn_people.setBackgroundResource(R.drawable.second_search_shape);
        btn_cause.setBackgroundResource(R.drawable.first_second_search_shape);

        btn_cause.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
        btn_people.setTextColor(getApplication().getResources().getColor(R.color.tab_enable));
    }
}
