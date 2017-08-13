package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.HisUserCausesAdapter;
import com.example.amr.sunbula.Adapters.List_CategoriesAdapter;
import com.example.amr.sunbula.Fragment.AllProfileFragment;
import com.example.amr.sunbula.Models.APIResponses.CompleteOrDeleteCauseResponse;
import com.example.amr.sunbula.Models.APIResponses.FollowResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.Models.APIResponses.UNFollowResponse;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowModels.AllCausesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.JoinedCasesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.MyCausesProfile;
import com.example.amr.sunbula.Models.DBFlowWrappers.AllCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.HisCausesPeopleWrapper;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailsUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    String people_id;
    String UserID;
    APIService mAPIService;
    List<ListofPepoleResponse.ListofPepoleFollowingBean> listofPepoleFollowingBeen;
    private ProgressDialog pdialog;
    ListView list_show_hiscauses;
    List<UserDetailsResponse.MyCasesBean> myCasesBeanList;
    List<HisCausesPeopleWrapper> hisCausesPeopleWrappers;
    HisUserCausesAdapter adapter;
    TextView text_reviews_user_profile, text_causes_user_profile, text_location_user_profile, username_user_profile;
    ImageView image_user_profile;
    Button btn_add_user, btn_message_call;
    boolean switch_following = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_user);

        toolbar = (Toolbar) findViewById(R.id.toolbar_show_people_details);
        toolbar.setTitle("View Profile");
        setSupportActionBar(toolbar);

        list_show_hiscauses = (ListView) findViewById(R.id.list_hiscauses);
        btn_add_user = (Button) findViewById(R.id.btn_add_user);
        btn_message_call = (Button) findViewById(R.id.btn_message_call);

        hisCausesPeopleWrappers = new ArrayList<>();

        SharedPreferences sharedPreferences = ShowDetailsUserActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        pdialog = new ProgressDialog(ShowDetailsUserActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        username_user_profile = (TextView) findViewById(R.id.username_user_profile);
        text_reviews_user_profile = (TextView) findViewById(R.id.text_reviews_user_profile);
        text_causes_user_profile = (TextView) findViewById(R.id.text_causes_user_profile);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        people_id = b.getString("people_id");

        HisDetailsPost(people_id);

        text_reviews_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowDetailsUserActivity.this, "Make Review", Toast.LENGTH_SHORT).show();
            }
        });

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListofPepolePost(UserID, people_id);
            }
        });
    }

    public void HisDetailsPost(final String people_id) {
        pdialog.show();
        mAPIService.UserDetails(people_id).enqueue(new Callback<UserDetailsResponse>() {

            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        username_user_profile.setText(response.body().getName());

                        UserDetailsResponse userDetailsResponse = response.body();
                        myCasesBeanList = new ArrayList<UserDetailsResponse.MyCasesBean>();

                        myCasesBeanList = userDetailsResponse.getMyCases();

                        text_causes_user_profile.setText(myCasesBeanList.size() + " Causes");

                        for (int a = 0; a < myCasesBeanList.size(); a++) {
                            HisCausesPeopleWrapper hisCausesPeopleWrapper = new HisCausesPeopleWrapper(myCasesBeanList.get(a));
                            hisCausesPeopleWrappers.add(hisCausesPeopleWrapper);
                        }

                        adapter = new HisUserCausesAdapter(ShowDetailsUserActivity.this, R.layout.item_in_bottom_hisprofile,
                                hisCausesPeopleWrappers, UserID, people_id);
                        list_show_hiscauses.setAdapter(adapter);

                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Toast.makeText(ShowDetailsUserActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void ListofPepolePost(final String User_ID, final String people_id) {
        pdialog.show();
        mAPIService.ListofPepole(User_ID).enqueue(new Callback<ListofPepoleResponse>() {

            @Override
            public void onResponse(Call<ListofPepoleResponse> call, Response<ListofPepoleResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        listofPepoleFollowingBeen = new ArrayList<ListofPepoleResponse.ListofPepoleFollowingBean>();

                        ListofPepoleResponse listofPepoleResponse = response.body();

                        listofPepoleFollowingBeen = listofPepoleResponse.getListofPepoleFollowing();

                        for (int i = 0; i < listofPepoleFollowingBeen.size(); i++) {
                            if (people_id.contains(listofPepoleFollowingBeen.get(i).getFollowID()))
                                switch_following = false;
                            else
                                switch_following = true;
                        }
                        if (switch_following)
                            FollowPost(User_ID, people_id);
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailsUserActivity.this);
                            builder.setMessage("Do you want to UnFollow ?")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            UnFollowPost(UserID, people_id);

                                        }
                                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Nothing
                                }
                            });
                            AlertDialog d = builder.create();
                            d.setTitle("Are you sure");
                            d.show();
                        }

                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListofPepoleResponse> call, Throwable t) {
                Toast.makeText(ShowDetailsUserActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void FollowPost(String User_ID, String people_id) {
        pdialog.show();
        mAPIService.Follow(User_ID, people_id).enqueue(new Callback<FollowResponse>() {

            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        Toast.makeText(ShowDetailsUserActivity.this, "Follow Successfully", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                Toast.makeText(ShowDetailsUserActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void UnFollowPost(String User_ID, String people_id) {
        pdialog.show();
        mAPIService.UNFollow(User_ID, people_id).enqueue(new Callback<UNFollowResponse>() {

            @Override
            public void onResponse(Call<UNFollowResponse> call, Response<UNFollowResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        Toast.makeText(ShowDetailsUserActivity.this, "UnFollow Successfully", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UNFollowResponse> call, Throwable t) {
                Toast.makeText(ShowDetailsUserActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_people_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.report) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDateandTime = sdf.format(new Date());
            Toast.makeText(this, currentDateandTime, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
