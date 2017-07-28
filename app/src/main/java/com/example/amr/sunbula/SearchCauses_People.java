package com.example.amr.sunbula;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SearchCauses_People extends AppCompatActivity {

    Button btn_cause, btn_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_causes__people);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);

        btn_cause = (Button) findViewById(R.id.button_cause);
        btn_people = (Button) findViewById(R.id.button_people);

        Action_cause();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_causes, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search_causes);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    adapter.filter("");
//                    listView.clearTextFilter();
//                } else {
//                    adapter.filter(newText);
//                }
                return true;
            }
        });

        return true;
    }
}
