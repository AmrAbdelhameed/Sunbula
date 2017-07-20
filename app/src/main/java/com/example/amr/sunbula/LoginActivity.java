package com.example.amr.sunbula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button b_login;
    TextView username, password, forgetpassword, go_to_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b_login = (Button) findViewById(R.id.btn_login);
        username = (TextView) findViewById(R.id.txtusernamelogin);
        password = (TextView) findViewById(R.id.txtpasswordlogin);
        forgetpassword = (TextView) findViewById(R.id.text_forget);
        go_to_register = (TextView) findViewById(R.id.txt_toregister);

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
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
}
