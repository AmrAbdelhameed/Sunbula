package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.amr.sunbula.Models.DBFlowModels.NewsFeed;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

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
    private SearchCauses_Adapter adapter;
    SearchPeople_Adapter adapter2;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;
    boolean choice;
    List<SearchCausesResponse.SearchedCasesBean> searchedCasesBeen;
    List<SearchPeopleResponse.SearchedPepoleBean> searchedPepoleBeen;

    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    SearchCausesResponse.SearchedCasesBean searchedCasesBean;
    SearchPeopleResponse.SearchedPepoleBean searchedPepoleBean;

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

                if (isNetworkAvailable()) {
                    if (!text_search.getText().toString().isEmpty()) {
                        progressDialog = new ProgressDialog(SearchCauses_People.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        if (choice) {
//                    SearchCausesPost(UserID, text_search.getText().toString());
                            SearchCausesFirebase(text_search.getText().toString());
                        } else {
//                    SearchPeoplePost(UserID, text_search.getText().toString());
                            SearchPeopleFirebase(text_search.getText().toString());
                        }
                    }
                } else {
                    Toast.makeText(SearchCauses_People.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                }
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

    public void SearchCausesFirebase(final String body) {

        searchedCasesBeen = new ArrayList<SearchCausesResponse.SearchedCasesBean>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("Search").child("SearchedCases").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                searchedCasesBeen.clear();
                for (DataSnapshot child : children) {

                    searchedCasesBean = new SearchCausesResponse.SearchedCasesBean();
                    String CauseID = child.getKey();
                    String CaseName = child.child("CaseName").getValue().toString();
                    String CaseDescription = child.child("CaseDescription").getValue().toString();
                    String EndDate = child.child("EndDate").getValue().toString();
                    String IMG = child.child("IMG").getValue().toString();
                    boolean IsJoined = (boolean) child.child("IsJoined").getValue();
                    boolean IsOwner = (boolean) child.child("IsOwner").getValue();
                    String Numberofjoins = child.child("Numberofjoins").getValue().toString();
                    String status = child.child("status").getValue().toString();
                    String Amount = child.child("Amount").getValue().toString();

                    if (CaseName.contains(body)) {
                        searchedCasesBean.setCaseName(CaseName);
                        searchedCasesBean.setCaseDescription(CaseDescription);
                        searchedCasesBean.setIsJoined(IsJoined);
                        searchedCasesBean.setIsOwner(IsOwner);
                        searchedCasesBean.setCauseID(CauseID);
                        searchedCasesBean.setEndDate(EndDate);
                        searchedCasesBean.setIMG(IMG);
                        searchedCasesBean.setAmount(Integer.parseInt(Amount));
                        searchedCasesBean.setNumberofjoins(Integer.parseInt(Numberofjoins));
                        searchedCasesBean.setStatus(Integer.parseInt(status));

                        searchedCasesBeen.add(searchedCasesBean);

                        adapter = new SearchCauses_Adapter(SearchCauses_People.this, R.layout.item_in_search_causes, searchedCasesBeen);
                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public void SearchPeopleFirebase(final String body) {

        searchedPepoleBeen = new ArrayList<SearchPeopleResponse.SearchedPepoleBean>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("Search").child("SearchedPepole").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                searchedPepoleBeen.clear();
                for (DataSnapshot child : children) {

                    searchedPepoleBean = new SearchPeopleResponse.SearchedPepoleBean();

//                    String User_ID = child.getKey();
                    String Name = child.child("Name").getValue().toString();
                    String User_ID = child.child("User_ID").getValue().toString();
                    String ImgURL = child.child("ImgURL").getValue().toString();

                    if (Name.contains(body)) {
                        searchedPepoleBean.setName(Name);
                        searchedPepoleBean.setUser_ID(User_ID);
                        searchedPepoleBean.setImgURL(ImgURL);

                        searchedPepoleBeen.add(searchedPepoleBean);

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
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
