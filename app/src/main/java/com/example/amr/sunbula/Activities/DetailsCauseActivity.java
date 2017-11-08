package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.AllCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.HisCausesPeopleWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.JoinedCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.MyCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.NewsFeedWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsCauseActivity extends AppCompatActivity {

    TextView cause_user_details, cause_details, text_NEEDED, text_date;
    de.hdodenhof.circleimageview.CircleImageView image_cause;
    String cause_user_detailsS, cause_detailsS, text_dateS, imageURL;
    int text_NEEDEDS;
    String UserID, TOID;
    APIService mAPIService;
    Button btn_donate;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cause);

        pdialog = new ProgressDialog(DetailsCauseActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = DetailsCauseActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        image_cause = findViewById(R.id.image_cause);
        cause_user_details = findViewById(R.id.cause_user_details);
        cause_details = findViewById(R.id.cause_details);
        text_NEEDED = findViewById(R.id.text_NEEDED);
        text_date = findViewById(R.id.text_date);
        btn_donate = findViewById(R.id.btn_donate);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        Gson gson = new Gson();
        int id = b.getInt("id");
        if (id == 1) {

            NewsFeedWrapper newsFeedWrapper = gson.fromJson(b.getString("myObject"), NewsFeedWrapper.class);

            TOID = newsFeedWrapper.getOwnderID();
            cause_user_detailsS = newsFeedWrapper.getCaseName();
            cause_detailsS = newsFeedWrapper.getCaseDescription();
            text_NEEDEDS = newsFeedWrapper.getAmount();
            text_dateS = newsFeedWrapper.getEndDate();

            imageURL = newsFeedWrapper.getIMG();
            if (imageURL != null && imageURL.isEmpty())
                imageURL = null;

            if (newsFeedWrapper.isJoined() || newsFeedWrapper.isOwner())
                btn_donate.setVisibility(View.GONE);

        } else if (id == 2) {

            AllCausesProfileWrapper allCausesProfileWrapper = gson.fromJson(b.getString("myObject"), AllCausesProfileWrapper.class);

            TOID = allCausesProfileWrapper.getOwnderID();
            cause_user_detailsS = allCausesProfileWrapper.getCaseName();
            cause_detailsS = allCausesProfileWrapper.getCaseDescription();
            text_NEEDEDS = allCausesProfileWrapper.getAmount();
            text_dateS = allCausesProfileWrapper.getEndDate();

            imageURL = allCausesProfileWrapper.getIMG();
            if (imageURL != null && imageURL.isEmpty())
                imageURL = null;

            if (allCausesProfileWrapper.isJoined() || allCausesProfileWrapper.isOwner())
                btn_donate.setVisibility(View.GONE);

        } else if (id == 3) {

            MyCausesProfileWrapper myCausesProfileWrapper = gson.fromJson(b.getString("myObject"), MyCausesProfileWrapper.class);

            TOID = myCausesProfileWrapper.getOwnderID();
            cause_user_detailsS = myCausesProfileWrapper.getCaseName();
            cause_detailsS = myCausesProfileWrapper.getCaseDescription();
            text_NEEDEDS = myCausesProfileWrapper.getAmount();
            text_dateS = myCausesProfileWrapper.getEndDate();

            imageURL = myCausesProfileWrapper.getIMG();
            if (imageURL != null && imageURL.isEmpty())
                imageURL = null;

            if (myCausesProfileWrapper.isJoined())
                btn_donate.setVisibility(View.GONE);

        } else if (id == 4) {

            JoinedCausesProfileWrapper joinedCausesProfileWrapper = gson.fromJson(b.getString("myObject"), JoinedCausesProfileWrapper.class);

            TOID = joinedCausesProfileWrapper.getOwnderID();
            cause_user_detailsS = joinedCausesProfileWrapper.getCaseName();
            cause_detailsS = joinedCausesProfileWrapper.getCaseDescription();
            text_NEEDEDS = joinedCausesProfileWrapper.getAmount();
            text_dateS = joinedCausesProfileWrapper.getEndDate();

            imageURL = joinedCausesProfileWrapper.getIMG();
            if (imageURL != null && imageURL.isEmpty())
                imageURL = null;

            btn_donate.setVisibility(View.GONE);
        } else if (id == 5) {

            SearchCausesResponse.SearchedCasesBean searchedCasesBean = gson.fromJson(b.getString("myObject"), SearchCausesResponse.SearchedCasesBean.class);

            TOID = searchedCasesBean.getOwnderID();
            cause_user_detailsS = searchedCasesBean.getCaseName();
            cause_detailsS = searchedCasesBean.getCaseDescription();
            text_NEEDEDS = searchedCasesBean.getAmount();
            text_dateS = searchedCasesBean.getEndDate();

            imageURL = searchedCasesBean.getIMG();
            if (imageURL != null && imageURL.isEmpty())
                imageURL = null;

            if (searchedCasesBean.isIsJoined() || searchedCasesBean.isIsOwner())
                btn_donate.setVisibility(View.GONE);
        } else if (id == 6) {

            HisCausesPeopleWrapper hisCausesPeopleWrapper = gson.fromJson(b.getString("myObject"), HisCausesPeopleWrapper.class);

            TOID = hisCausesPeopleWrapper.getOwnderID();
            cause_user_detailsS = hisCausesPeopleWrapper.getCaseName();
            cause_detailsS = hisCausesPeopleWrapper.getCaseDescription();
            text_NEEDEDS = hisCausesPeopleWrapper.getAmount();
            text_dateS = hisCausesPeopleWrapper.getEndDate();

            imageURL = hisCausesPeopleWrapper.getIMG();
            if (imageURL != null && imageURL.isEmpty())
                imageURL = null;

            if (hisCausesPeopleWrapper.isJoined() || hisCausesPeopleWrapper.isOwner())
                btn_donate.setVisibility(View.GONE);
        }

        cause_user_details.setText(cause_user_detailsS);
        cause_details.setText(cause_detailsS);
        text_NEEDED.setText(String.valueOf(text_NEEDEDS));
        text_date.setText(text_dateS);
        Picasso.with(DetailsCauseActivity.this).load(imageURL).into(image_cause);

        btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMassegePost(UserID, TOID, "I'd like to connect with you");
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
                        Toast.makeText(DetailsCauseActivity.this, "Your message has been sent pending approval by the administrator", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(DetailsCauseActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
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
