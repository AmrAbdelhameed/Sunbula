package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.LoginResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    ImageButton b_login;
    TextView Email, password, forgetpassword, go_to_register;
    APIService mAPIService;
    private ProgressDialog pdialog;
    private boolean loggedIn = false, loggedIn2 = false;

    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    String UserID = "";
    private ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
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
                } else {
                    final String emaill = Email.getText().toString();

//                    LoginPost(Email.getText().toString(), password.getText().toString());
                    final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Proccessing...", true);

                    (firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(), password.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        databaseReference = database.getReference();
                                        databaseReference.child("users").addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                                for (DataSnapshot child : children) {
                                                    String uid = child.getKey();
                                                    String uemail = child.child("email").getValue().toString();
                                                    String uimgURL = child.child("imgURL").getValue().toString();
                                                    String uname = child.child("name").getValue().toString();

                                                    if (emaill.equals(uemail)) {
                                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("UserID", uid);
                                                        editor.putString("UserName", uname);
                                                        editor.putString("UserImgURL", uimgURL);
                                                        editor.putString("UserEmail", uemail);
                                                        editor.apply();
                                                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });


                                    } else {
                                        Log.e("ERROR", task.getException().toString());
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
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
            }
        });
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
                        editor.apply();
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
