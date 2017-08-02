package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amr.sunbula.Models.RegistrationResponse;
import com.example.amr.sunbula.Models.VerfiedAccntResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmEmailActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmEmailActivity";
    Button btn_continue;
    EditText Cnformcode;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        pdialog = new ProgressDialog(ConfirmEmailActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        Cnformcode = (EditText) findViewById(R.id.txtconfirmcontinue);
        btn_continue = (Button) findViewById(R.id.btn_continue);

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Cnformcode.getText().toString().isEmpty()) {
                    Cnformcode.setError("Please enter here");
                } else
                    VerfiedAccntPost(UserID, Cnformcode.getText().toString());
            }
        });
    }

    public void VerfiedAccntPost(String UserId, String Code) {
        pdialog.show();
        mAPIService.VerfiedAccnt(UserId, Code).enqueue(new Callback<VerfiedAccntResponse>() {

            @Override
            public void onResponse(Call<VerfiedAccntResponse> call, Response<VerfiedAccntResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().isSuccess()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        Toast.makeText(ConfirmEmailActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ConfirmEmailActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(ConfirmEmailActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<VerfiedAccntResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(ConfirmEmailActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
