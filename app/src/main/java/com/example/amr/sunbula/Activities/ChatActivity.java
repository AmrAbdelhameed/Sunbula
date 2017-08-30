package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.MessagesFragmentAdapter;
import com.example.amr.sunbula.Adapters.MessagesInboxAdapter;
import com.example.amr.sunbula.Adapters.NotificationsFragmentAdapter;
import com.example.amr.sunbula.Models.APIResponses.RecieveMassegeResponse;
import com.example.amr.sunbula.Models.APIResponses.RecieveMassegeResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ListView list_chat;
    private List<RecieveMassegeResponse.MSgsBean> listOfMassegesBeen;
    private MessagesInboxAdapter adapter;
    APIService mAPIService;
    private ProgressDialog pdialog;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);

        pdialog = new ProgressDialog(ChatActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        Intent in = getIntent();
        Bundle b = in.getExtras();
        String ThreadID = b.getString("ThreadID");

        SharedPreferences sharedPreferences = ChatActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        list_chat = (ListView) findViewById(R.id.list_chat);

        RecieveMassegePost(ThreadID, UserID);
    }

    public void RecieveMassegePost(String ThreadID, String User_ID) {
        pdialog.show();
        mAPIService.RecieveMassege(ThreadID, "1/1/9999", User_ID).enqueue(new Callback<RecieveMassegeResponse>() {

            @Override
            public void onResponse(Call<RecieveMassegeResponse> call, Response<RecieveMassegeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        setTitle(response.body().getUserName());


                        listOfMassegesBeen = new ArrayList<RecieveMassegeResponse.MSgsBean>();

                        RecieveMassegeResponse RecieveMassegeResponse = response.body();
                        listOfMassegesBeen = RecieveMassegeResponse.getMSgs();

                        adapter = new MessagesInboxAdapter(ChatActivity.this, listOfMassegesBeen, response.body().getImage());
                        list_chat.setAdapter(adapter);
                    } else
                        Toast.makeText(ChatActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<RecieveMassegeResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }
}
