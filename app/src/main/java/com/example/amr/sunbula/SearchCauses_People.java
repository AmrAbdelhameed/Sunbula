package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.SearchCauses_PeopleAdapter;
import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCauses_People extends AppCompatActivity {

    Button btn_cause, btn_people;
    ImageView btn_search;
    EditText text_search;
    private ListView listView;
    private SearchCauses_PeopleAdapter adapter;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;
    boolean choice;
    List<SearchCausesResponse.SearchedCasesBean> searchedCasesBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_causes__people);

        pdialog = new ProgressDialog(SearchCauses_People.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        listView = (ListView) findViewById(R.id.list_search_causes);
        btn_cause = (Button) findViewById(R.id.button_cause);
        btn_people = (Button) findViewById(R.id.button_people);
        text_search = (EditText) findViewById(R.id.text_search);
        btn_search = (ImageView) findViewById(R.id.btn_search);

        SharedPreferences sharedPreferences = SearchCauses_People.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice)
                    SearchCausesPost(UserID, text_search.getText().toString());
                else
                    Toast.makeText(SearchCauses_People.this, "Enta fe people w 3ayz te-seach 3la el causes !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SearchCausesPost(String UserId, String Searchword) {
        pdialog.show();
        mAPIService.SearchCauses(UserId, Searchword, 1).enqueue(new Callback<SearchCausesResponse>() {

            @Override
            public void onResponse(Call<SearchCausesResponse> call, Response<SearchCausesResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        searchedCasesBeen = new ArrayList<SearchCausesResponse.SearchedCasesBean>();

                        SearchCausesResponse searchCausesResponse = response.body();
                        searchedCasesBeen = searchCausesResponse.getSearchedCases();

                        adapter = new SearchCauses_PeopleAdapter(SearchCauses_People.this, R.layout.item_in_search_causes, searchedCasesBeen);
                        listView.setAdapter(adapter);
                    } else
                        Toast.makeText(SearchCauses_People.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<SearchCausesResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }

    private void Action_cause() {
        choice = true;

        btn_cause.setBackgroundResource(R.drawable.first_search_shape);
        btn_people.setBackgroundResource(R.drawable.second_first_search_shape);

        btn_cause.setTextColor(getApplication().getResources().getColor(R.color.tab_enable));
        btn_people.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
    }

    private void Action_people() {
        choice = false;

        btn_people.setBackgroundResource(R.drawable.second_search_shape);
        btn_cause.setBackgroundResource(R.drawable.first_second_search_shape);

        btn_cause.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
        btn_people.setTextColor(getApplication().getResources().getColor(R.color.tab_enable));
    }
}
