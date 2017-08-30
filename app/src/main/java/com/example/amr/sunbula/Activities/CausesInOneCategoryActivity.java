package com.example.amr.sunbula.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.amr.sunbula.Adapters.List_CausesAdapter;
import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.R;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CausesInOneCategoryActivity extends AppCompatActivity {

    List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> allCasesBeanList;
    String CatName;
    String JsonCauses;
    ListView listView_causesInCategroied;
    List_CausesAdapter list_causesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_causes_in_one_category);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_causesInCategroied);
        setSupportActionBar(toolbar);

        listView_causesInCategroied = (ListView) findViewById(R.id.list_causesInCategroied);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//        allCasesBeanList = (ArrayList<AllCategoriesResponse.AllCategoriesBean.AllCasesBean>) bundle.getSerializable("Causes");
//
        CatName = bundle.getString("NameCategory");
        setTitle("Causes of " + CatName);
        JsonCauses = bundle.getString("Causes");
        Gson gson = new Gson();
        Type type = new TypeToken<List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean>>() {
        }.getType();
        allCasesBeanList = gson.fromJson(JsonCauses, type);

        list_causesAdapter = new List_CausesAdapter(CausesInOneCategoryActivity.this, R.layout.item_in_causes, allCasesBeanList);
        listView_causesInCategroied.setDivider(null);
        listView_causesInCategroied.setAdapter(list_causesAdapter);

    }
}
