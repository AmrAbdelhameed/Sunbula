package com.example.amr.sunbula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    String UserID, Name, Email, mNumber, Address, Gender;
    EditText txt_edit_name, txt_edit_email, txt_edit_pin, txt_edit_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editprofile);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        UserID = b.getString("UserID");
        Name = b.getString("Name");
        Email = b.getString("Email");
        mNumber = b.getString("mNumber");
        Address = b.getString("Address");
        Gender = b.getString("Gender");

        txt_edit_name = (EditText) findViewById(R.id.txt_edit_name);
        txt_edit_email = (EditText) findViewById(R.id.txt_edit_email);
        txt_edit_pin = (EditText) findViewById(R.id.txt_edit_pin);
        txt_edit_phone = (EditText) findViewById(R.id.txt_edit_phone);

        txt_edit_name.setText(Name);
        txt_edit_email.setText(Email);
        txt_edit_pin.setText(Address);
        txt_edit_phone.setText(mNumber);

        Toast.makeText(this, UserID + " " + Gender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_profile) {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
