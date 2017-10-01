package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.SearchCauses_Adapter;
import com.example.amr.sunbula.Adapters.SearchPeople_Adapter;
import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.Models.APIResponses.SearchPeopleResponse;
import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCauses_People extends AppCompatActivity {

    Button btn_cause, btn_people;
    ImageView btn_search;
    EditText text_search;
    SearchPeople_Adapter adapter2;
    String UserID;
    APIService mAPIService;
    boolean choice;
    List<SearchCausesResponse.SearchedCasesBean> searchedCasesBeen;
    List<SearchPeopleResponse.SearchedPepoleBean> searchedPepoleBeen;
    private ListView listView;
    private SearchCauses_Adapter adapter;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_causes__people);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

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
                if (!choice) {
                    Action_cause();
                }
            }
        });

        btn_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice) {
                    Action_people();
                }
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice)
                    SearchCausesPost(UserID, text_search.getText().toString());
                else
                    SearchPeoplePost(UserID, text_search.getText().toString());
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
                        SearchCausesResponse searchCausesResponse = response.body();
                        if (searchCausesResponse.getSearchedCases().size() > 0) {
                            searchedCasesBeen = new ArrayList<SearchCausesResponse.SearchedCasesBean>();
                            searchedCasesBeen = searchCausesResponse.getSearchedCases();

                            adapter = new SearchCauses_Adapter(SearchCauses_People.this, R.layout.item_in_search_causes, searchedCasesBeen);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                    // TODO Auto-generated method stub
                                    SendMassegePost(UserID, searchedCasesBeen.get(pos).getOwnderID(), "I'd like to connect with you");
                                }
                            });
                        }
                    } else
                        Toast.makeText(SearchCauses_People.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<SearchCausesResponse> call, Throwable t) {
                Toast.makeText(SearchCauses_People.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void SearchPeoplePost(String UserId, String Searchword) {
        pdialog.show();
        mAPIService.SearchPeople(UserId, Searchword, 2).enqueue(new Callback<SearchPeopleResponse>() {

            @Override
            public void onResponse(Call<SearchPeopleResponse> call, Response<SearchPeopleResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        SearchPeopleResponse searchCausesResponse = response.body();

                        if (searchCausesResponse.getSearchedPepole().size() > 0) {
                            searchedPepoleBeen = new ArrayList<SearchPeopleResponse.SearchedPepoleBean>();
                            searchedPepoleBeen = searchCausesResponse.getSearchedPepole();

                            adapter2 = new SearchPeople_Adapter(SearchCauses_People.this, R.layout.item_in_search_people, searchedPepoleBeen);
                            listView.setAdapter(adapter2);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(SearchCauses_People.this, ShowDetailsUserActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("people_id", searchedPepoleBeen.get(pos).getUser_ID());
                                    i.putExtras(b);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            });
                        }
                    } else
                        Toast.makeText(SearchCauses_People.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<SearchPeopleResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }

    private void Action_cause() {
        choice = true;
        text_search.setText("");
        listView.setAdapter(null);

        btn_cause.setBackgroundResource(R.drawable.first_search_shape);
        btn_people.setBackgroundResource(R.drawable.second_first_search_shape);

        btn_cause.setTextColor(getApplication().getResources().getColor(R.color.tab_enable));
        btn_people.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
    }

    private void Action_people() {
        choice = false;
        text_search.setText("");
        listView.setAdapter(null);

        btn_people.setBackgroundResource(R.drawable.second_search_shape);
        btn_cause.setBackgroundResource(R.drawable.first_second_search_shape);

        btn_cause.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
        btn_people.setTextColor(getApplication().getResources().getColor(R.color.tab_enable));
    }

    private void SendMassegePost(String User_ID, String ToID, String MSGBody) {
        pdialog.show();
        mAPIService.SendMassege(User_ID, ToID, MSGBody).enqueue(new Callback<SendMassegeResponse>() {

            @Override
            public void onResponse(Call<SendMassegeResponse> call, Response<SendMassegeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(SearchCauses_People.this, "Your message has been sent pending approval by the administrator", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SearchCauses_People.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<SendMassegeResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }
}
