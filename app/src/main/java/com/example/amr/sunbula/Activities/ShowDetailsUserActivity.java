package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.HisUserCausesAdapter;
import com.example.amr.sunbula.Models.APIResponses.BlockUserResponse;
import com.example.amr.sunbula.Models.APIResponses.FollowResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.Models.APIResponses.UNFollowResponse;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.HisCausesPeopleWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailsUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    String people_id;
    String UserID, imageUrl;
    APIService mAPIService;
    List<String> listofPepoleFollowingBeen;
    ListView list_show_hiscauses;
    List<UserDetailsResponse.MyCasesBean> myCasesBeanList;
    List<HisCausesPeopleWrapper> hisCausesPeopleWrappers;
    HisUserCausesAdapter adapter;
    TextView text_reviews_user_profile, text_causes_user_profile, text_location_user_profile, username_user_profile;
    de.hdodenhof.circleimageview.CircleImageView image_user_profile;
    Button btn_add_user, btn_message_call;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_user);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        toolbar = (Toolbar) findViewById(R.id.toolbar_show_people_details);
        toolbar.setTitle("View Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list_show_hiscauses = (ListView) findViewById(R.id.list_hiscauses);
        btn_add_user = (Button) findViewById(R.id.btn_add_user);
        btn_message_call = (Button) findViewById(R.id.btn_message_call);
        image_user_profile = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.image_user_profile);

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
        text_location_user_profile = (TextView) findViewById(R.id.text_location_user_profile);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        people_id = b.getString("people_id");

        HisDetailsPost(people_id);
        ListofPepolePost(UserID);

        btn_message_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMassegePost(UserID, people_id, "I'd like to connect with you");
            }
        });

        text_reviews_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MakeReviewPost(people_id, review_star.getText().toString(), review_body.getText().toString());

                Intent i = new Intent(ShowDetailsUserActivity.this, MakeReviewActivity.class);
                i.putExtra("people_id", people_id);
                startActivity(i);
            }
        });

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!listofPepoleFollowingBeen.contains(people_id)) {
                    FollowPost(UserID, people_id);
                } else {
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

                        text_causes_user_profile.setText(myCasesBeanList.size() + " Cases");

                        imageUrl = response.body().getImgURL();
                        if (imageUrl != null && imageUrl.isEmpty())
                            imageUrl = null;
                        Picasso.with(ShowDetailsUserActivity.this).load(imageUrl).into(image_user_profile);

                        if (response.body().getAddress() != null)
                            text_location_user_profile.setText(String.valueOf(response.body().getAddress()));

                        if (myCasesBeanList.size() > 0) {

                            for (int a = 0; a < myCasesBeanList.size(); a++) {
                                HisCausesPeopleWrapper hisCausesPeopleWrapper = new HisCausesPeopleWrapper(myCasesBeanList.get(a));
                                hisCausesPeopleWrappers.add(hisCausesPeopleWrapper);
                            }

                            adapter = new HisUserCausesAdapter(ShowDetailsUserActivity.this, R.layout.item_in_bottom_hisprofile,
                                    hisCausesPeopleWrappers, UserID, people_id);
                            list_show_hiscauses.setAdapter(adapter);
                        }
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

    public void ListofPepolePost(String User_ID) {
        pdialog.show();
        mAPIService.ListofPepole(User_ID).enqueue(new Callback<ListofPepoleResponse>() {

            @Override
            public void onResponse(Call<ListofPepoleResponse> call, Response<ListofPepoleResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        listofPepoleFollowingBeen = new ArrayList<String>();

                        ListofPepoleResponse listofPepoleResponse = response.body();

                        for (int i = 0; i < listofPepoleResponse.getListofPepoleFollowing().size(); i++) {
                            listofPepoleFollowingBeen.add(listofPepoleResponse.getListofPepoleFollowing().get(i).getFollowID());
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

    public void FollowPost(String User_ID, final String people_ID) {
        pdialog.show();
        mAPIService.Follow(User_ID, people_ID).enqueue(new Callback<FollowResponse>() {

            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        listofPepoleFollowingBeen.add(people_ID);
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

    public void UnFollowPost(String User_ID, final String people_ID) {
        pdialog.show();
        mAPIService.UNFollow(User_ID, people_ID).enqueue(new Callback<UNFollowResponse>() {

            @Override
            public void onResponse(Call<UNFollowResponse> call, Response<UNFollowResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        int c = listofPepoleFollowingBeen.indexOf(people_ID);
                        listofPepoleFollowingBeen.remove(c);
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

    public void BlockUserPost(String FromUserID, String ToUserID) {
        pdialog.show();
        mAPIService.BlockUser(FromUserID, ToUserID).enqueue(new Callback<BlockUserResponse>() {

            @Override
            public void onResponse(Call<BlockUserResponse> call, Response<BlockUserResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(ShowDetailsUserActivity.this, "Blocked Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<BlockUserResponse> call, Throwable t) {
                Toast.makeText(ShowDetailsUserActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    private void SendMassegePost(String User_ID, String ToID, String MSGBody) {
        pdialog.show();
        mAPIService.SendMassege(User_ID, ToID, MSGBody).enqueue(new Callback<SendMassegeResponse>() {

            @Override
            public void onResponse(Call<SendMassegeResponse> call, Response<SendMassegeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(ShowDetailsUserActivity.this, "Your message has been sent pending approval by the administrator", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<SendMassegeResponse> call, Throwable t) {
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

            Intent i = new Intent(ShowDetailsUserActivity.this, MakeReportActivity.class);
            i.putExtra("UserID", UserID);
            i.putExtra("people_id", people_id);
            startActivity(i);

            return true;
        }
        if (id == R.id.block) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to block " + username_user_profile.getText().toString() + " ?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            BlockUserPost(UserID, people_id);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
