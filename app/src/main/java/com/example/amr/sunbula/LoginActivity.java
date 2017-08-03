package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.LoginResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    ImageButton b_login;
    TextView Email, password, forgetpassword, go_to_register;
    APIService mAPIService;
    private ProgressDialog pdialog;
    private boolean loggedIn = false;
    private boolean loggedIn2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(LoginActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        b_login = (ImageButton) findViewById(R.id.btn_login);
        Email = (TextView) findViewById(R.id.txtemaillogin);
        password = (TextView) findViewById(R.id.txtpasswordlogin);
        forgetpassword = (TextView) findViewById(R.id.text_forget);
        go_to_register = (TextView) findViewById(R.id.txt_toregister);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString().isEmpty()) {
                    Email.setError("Please enter here");
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Please enter here");
                } else
                    LoginPost(Email.getText().toString(), password.getText().toString());
            }
        });

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(i);
                finish();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("isVerified", false);
        loggedIn2 = sharedPreferences.getBoolean("facebookID", false);

        if (loggedIn || loggedIn2) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void LoginPost(String email, String pass) {
        pdialog.show();
        mAPIService.Login(email, pass).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().is_Verified()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserID", response.body().getUser_ID());
                        editor.putBoolean("isVerified", true);
                        editor.putBoolean("facebookID", false);
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        if (response.body().isSuccess()) {
                            Toast.makeText(LoginActivity.this, "Please check your mail to confirmation your email", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, ConfirmEmailActivity.class);
                            Bundle b = new Bundle();
                            b.putString("UserID", response.body().getUser_ID());
                            i.putExtras(b);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(LoginActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
