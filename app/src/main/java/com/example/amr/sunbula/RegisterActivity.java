package com.example.amr.sunbula;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    ImageView user_profile;
    EditText username, password, email;
    Button btn_register, btn_login_facebok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_profile = (ImageView) findViewById(R.id.imageregister);
        username = (EditText) findViewById(R.id.txtusernameregister);
        password = (EditText) findViewById(R.id.txtpasswordregister);
        email = (EditText) findViewById(R.id.txtemailregister);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login_facebok = (Button) findViewById(R.id.btn_login_facebok);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = username.getText().toString();
                String EMail = email.getText().toString();
                String Password = password.getText().toString();

                if (Name.isEmpty()) {
                    username.setError("Please enter here");
                } else if (EMail.isEmpty()) {
                    email.setError("Please enter here");
                } else if (Password.isEmpty()) {
                    password.setError("Please enter here");
                } else {
                    Toast.makeText(RegisterActivity.this, Name + EMail + Password, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_login_facebok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "Facebook", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
