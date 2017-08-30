package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.HisUserCausesAdapter;
import com.example.amr.sunbula.Models.APIResponses.FollowResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
import com.example.amr.sunbula.Models.APIResponses.MakeReportResponse;
import com.example.amr.sunbula.Models.APIResponses.MakeReviewResponse;
import com.example.amr.sunbula.Models.APIResponses.UNFollowResponse;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.HisCausesPeopleWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

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
    List<String> listofPepoleFollowingBeen;
    private ProgressDialog pdialog;
    ListView list_show_hiscauses;
    List<UserDetailsResponse.MyCasesBean> myCasesBeanList;
    List<HisCausesPeopleWrapper> hisCausesPeopleWrappers;
    HisUserCausesAdapter adapter;
    TextView text_reviews_user_profile, text_causes_user_profile, text_location_user_profile, username_user_profile;
    de.hdodenhof.circleimageview.CircleImageView image_user_profile;
    Button btn_add_user, btn_message_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_user);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        toolbar = (Toolbar) findViewById(R.id.toolbar_show_people_details);
        toolbar.setTitle("View Profile");
        setSupportActionBar(toolbar);

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

        text_reviews_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(ShowDetailsUserActivity.this);
                View dialogView = li.inflate(R.layout.dialog_review, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ShowDetailsUserActivity.this);

                alertDialogBuilder.setTitle("Make Review");
                alertDialogBuilder.setIcon(R.drawable.logo);

                alertDialogBuilder.setView(dialogView);
                final EditText review_star = (EditText) dialogView
                        .findViewById(R.id.review_star);
                final EditText review_body = (EditText) dialogView
                        .findViewById(R.id.review_body);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        MakeReviewPost(people_id, review_star.getText().toString(), review_body.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
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

                        if (userDetailsResponse.getMyCases().size() > 0) {
                            myCasesBeanList = new ArrayList<UserDetailsResponse.MyCasesBean>();
                            myCasesBeanList = userDetailsResponse.getMyCases();

                            text_causes_user_profile.setText(myCasesBeanList.size() + " Causes");
                            if (response.body().getAddress() != null)
                                text_location_user_profile.setText(String.valueOf(response.body().getAddress()));
                            if (response.body().getImgURL().contains("http"))
                                Picasso.with(ShowDetailsUserActivity.this).load(response.body().getImgURL()).into(image_user_profile);

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

    public void MakeReviewPost(String ReviweeID, String ReviewStars, String ReviewBody) {
        pdialog.show();
        mAPIService.MakeReview(ReviweeID, ReviewStars, ReviewBody).enqueue(new Callback<MakeReviewResponse>() {

            @Override
            public void onResponse(Call<MakeReviewResponse> call, Response<MakeReviewResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(ShowDetailsUserActivity.this, "Make Review Successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<MakeReviewResponse> call, Throwable t) {
                Toast.makeText(ShowDetailsUserActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void MakeReportPost(String ReportBody, String ReportName, String ReportDate, String ReportedPerson, String ReporterPerson) {
        pdialog.show();
        mAPIService.MakeReport(ReportBody, ReportName, ReportDate, ReportedPerson, ReporterPerson).enqueue(new Callback<MakeReportResponse>() {

            @Override
            public void onResponse(Call<MakeReportResponse> call, Response<MakeReportResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(ShowDetailsUserActivity.this, "Make Report Successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ShowDetailsUserActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<MakeReportResponse> call, Throwable t) {
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
            LayoutInflater li = LayoutInflater.from(ShowDetailsUserActivity.this);
            View dialogView = li.inflate(R.layout.dialog_report, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ShowDetailsUserActivity.this);

            alertDialogBuilder.setTitle("Make Report");
            alertDialogBuilder.setIcon(R.drawable.logo);

            alertDialogBuilder.setView(dialogView);
            final EditText report_name = (EditText) dialogView
                    .findViewById(R.id.report_name);
            final EditText report_body = (EditText) dialogView
                    .findViewById(R.id.report_body);
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    MakeReportPost(report_body.getText().toString()
                                            , report_name.getText().toString(), currentDateandTime, people_id, UserID);
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
