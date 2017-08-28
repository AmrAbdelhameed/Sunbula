package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.ForgetPasswordResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ResetPasswordActivity";
    APIService mAPIService;
    private ProgressDialog pdialog;
    EditText txtemailresetpassword;
    Button btn_resetpassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();

        mAPIService = ApiUtils.getAPIService();
        pdialog = new ProgressDialog(ResetPasswordActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        txtemailresetpassword = (EditText) findViewById(R.id.txtemailresetpassword);
        btn_resetpassword = (Button) findViewById(R.id.btn_resetpassword);

        btn_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtemailresetpassword.getText().toString().isEmpty()) {
                    txtemailresetpassword.setError("Please enter here");
                } else {
//                    ForgetPassword(txtemailresetpassword.getText().toString());
                    firebaseAuth.sendPasswordResetEmail(txtemailresetpassword.getText().toString())
                            .addOnCompleteListener(ResetPasswordActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ResetPasswordActivity.this, "Failed to send reset Email!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password on mail!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void ForgetPassword(String email) {
        pdialog.show();
        mAPIService.ForgetPassword(email).enqueue(new Callback<ForgetPasswordResponse>() {

            @Override
            public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().isIsSuccess()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        Toast.makeText(ResetPasswordActivity.this, "Please check your mail", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(ResetPasswordActivity.this, ChangePasswordActivity.class);
                        Bundle b = new Bundle();
                        b.putString("email_change_pass", txtemailresetpassword.getText().toString());
                        i.putExtras(b);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(ResetPasswordActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
