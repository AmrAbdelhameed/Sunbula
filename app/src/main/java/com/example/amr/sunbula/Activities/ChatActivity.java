package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.MessagesInboxAdapter;
import com.example.amr.sunbula.Models.APIResponses.RecieveMassegeResponse;
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

public class ChatActivity extends AppCompatActivity {

    APIService mAPIService;
    String UserID, FromID, ThreadID, ImageUSER;
    TextView txt_message;
    ImageView btn_send;
    private ListView list_chat;
    private List<RecieveMassegeResponse.MSgsBean> listOfMassegesBeen;
    private MessagesInboxAdapter adapter;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        txt_message = (TextView) findViewById(R.id.txt_message);
        btn_send = (ImageView) findViewById(R.id.btn_send);

        pdialog = new ProgressDialog(ChatActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        Intent in = getIntent();
        Bundle b = in.getExtras();
        ThreadID = b.getString("ThreadID");
        FromID = b.getString("FromID");
        ImageUSER = b.getString("ImageUSER");

        SharedPreferences sharedPreferences = ChatActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        list_chat = (ListView) findViewById(R.id.list_chat);

        RecieveMassegePost(ThreadID, UserID);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_message.getText().toString().isEmpty()) {
                    SendMassegePost(UserID, FromID, txt_message.getText().toString());
                    txt_message.setText("");
                }
            }
        });
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

                        adapter = new MessagesInboxAdapter(ChatActivity.this, listOfMassegesBeen, ImageUSER);
                        list_chat.setAdapter(adapter);
                        list_chat.setSelection(adapter.getCount() - 1);

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

    private void SendMassegePost(String User_ID, String ToID, String MSGBody) {
        pdialog.show();
        mAPIService.SendMassege(User_ID, ToID, MSGBody).enqueue(new Callback<SendMassegeResponse>() {

            @Override
            public void onResponse(Call<SendMassegeResponse> call, Response<SendMassegeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
//                        adapter.notifyDataSetChanged();
                        RecieveMassegePost(ThreadID, UserID);
                    } else
                        Toast.makeText(ChatActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
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
