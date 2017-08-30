package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.ResetPasswordResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String TAG = "ChangePasswordActivity";
    APIService mAPIService;
    private ProgressDialog pdialog;
    EditText txt_email_newpass, txt_code_newpass, txtnewpass, txtconfirmpass;
    Button btn_changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        mAPIService = ApiUtils.getAPIService();
        pdialog = new ProgressDialog(ChangePasswordActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        txt_email_newpass = (EditText) findViewById(R.id.txt_email_newpass);
        txt_code_newpass = (EditText) findViewById(R.id.txt_code_newpass);
        txtnewpass = (EditText) findViewById(R.id.txtnewpass);
        txtconfirmpass = (EditText) findViewById(R.id.txtconfirmpass);
        btn_changepassword = (Button) findViewById(R.id.btn_changepassword);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        String email_change_pass = b.getString("email_change_pass");

        txt_email_newpass.setText(email_change_pass);

        btn_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_email_newpass.getText().toString().isEmpty()) {
                    txt_email_newpass.setError("Please enter here");
                }
                if (txt_code_newpass.getText().toString().isEmpty()) {
                    txt_code_newpass.setError("Please enter here");
                }
                if (txtnewpass.getText().toString().isEmpty()) {
                    txtnewpass.setError("Please enter here");
                }
                if (txtconfirmpass.getText().toString().isEmpty()) {
                    txtconfirmpass.setError("Please enter here");
                } else
                    ResetPassword(txt_email_newpass.getText().toString()
                            , txtnewpass.getText().toString()
                            , txtconfirmpass.getText().toString()
                            , txt_code_newpass.getText().toString());
            }
        });
    }

    public void ResetPassword(String email, String pass, String confpass, String code) {
        pdialog.show();
        mAPIService.ResetPassword(email, pass, confpass, code).enqueue(new Callback<ResetPasswordResponse>() {

            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().isIsSuccess()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        Toast.makeText(ChangePasswordActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(ChangePasswordActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
