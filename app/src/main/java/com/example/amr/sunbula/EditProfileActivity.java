package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.Models.APIResponses.EditProfileResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    ArrayList<String> CategoriesNames_in_AddCause;
    ArrayList<String> CategoriesIDs_in_AddCause;
    ArrayList<String> Gender_arraylist;
    String Name, Email, mNumber, Address, Gender;
    String GetID = "", GetGender = "";
    EditText txt_edit_name, txt_edit_email, txt_edit_pin, txt_edit_phone;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;
    List<AllCategoriesResponse.AllCategoriesBean> allCategoriesBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editprofile);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        CategoriesNames_in_AddCause = new ArrayList<>();
        CategoriesIDs_in_AddCause = new ArrayList<>();
        Gender_arraylist = new ArrayList<>();

        pdialog = new ProgressDialog(EditProfileActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = EditProfileActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        Intent in = getIntent();
        Bundle b = in.getExtras();
        Name = b.getString("Name");
        Email = b.getString("Email");
        mNumber = b.getString("mNumber");
        Address = b.getString("Address");
        Gender = b.getString("Gender");

        if (Gender.equalsIgnoreCase("Male")) {
            Gender_arraylist.add("Male");
            Gender_arraylist.add("Female");
        } else if (Gender.equalsIgnoreCase("Female")) {
            Gender_arraylist.add("Female");
            Gender_arraylist.add("Male");
        } else {
            Gender_arraylist.add("Gender");
            Gender_arraylist.add("Male");
            Gender_arraylist.add("Female");
        }

        txt_edit_name = (EditText) findViewById(R.id.txt_edit_name);
        txt_edit_email = (EditText) findViewById(R.id.txt_edit_email);
        txt_edit_pin = (EditText) findViewById(R.id.txt_edit_pin);
        txt_edit_phone = (EditText) findViewById(R.id.txt_edit_phone);

        txt_edit_name.setText(Name);
        txt_edit_email.setText(Email);
        txt_edit_pin.setText(Address);
        txt_edit_phone.setText(mNumber);

        CategoriesNames_in_AddCause.add("Categories");
        CategoriesIDs_in_AddCause.add("IDs");

        GetAllCategories(UserID);

        Spinner staticSpinner = (Spinner) findViewById(R.id.spinner_InterestedCategories);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter staticAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CategoriesNames_in_AddCause);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (!CategoriesNames_in_AddCause.get(position).equals("Categories")) {
                    GetID = CategoriesIDs_in_AddCause.get(position);
                    Toast.makeText(EditProfileActivity.this, GetID, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Spinner staticSpinner2 = (Spinner) findViewById(R.id.spinner_gender);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter staticAdapter2 = new ArrayAdapter(EditProfileActivity.this, android.R.layout.simple_spinner_item, Gender_arraylist);

        // Specify the layout to use when the list of choices appears
        staticAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner2.setAdapter(staticAdapter2);

        staticSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (!Gender_arraylist.get(position).equals("Gender")) {
                    GetGender = Gender_arraylist.get(position);
                    Toast.makeText(EditProfileActivity.this, GetGender, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void GetAllCategories(String UserId) {
        pdialog.show();
        mAPIService.GetAllCategories(UserId).enqueue(new Callback<AllCategoriesResponse>() {

            @Override
            public void onResponse(Call<AllCategoriesResponse> call, Response<AllCategoriesResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        AllCategoriesResponse allCategoriesResponse = response.body();

                        allCategoriesBeen = new ArrayList<AllCategoriesResponse.AllCategoriesBean>();

                        allCategoriesBeen = allCategoriesResponse.getAllCategories();

                        for (int i = 0; i < allCategoriesBeen.size(); i++) {
                            CategoriesIDs_in_AddCause.add(allCategoriesBeen.get(i).getCategoryID());
                            CategoriesNames_in_AddCause.add(allCategoriesBeen.get(i).getCategoryName());
                        }
                    } else
                        Toast.makeText(EditProfileActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void EditProfilePost(String UserId, String Name, String EMail, String MobileNumber,
                                String Address, String Gender, String InterestedCategory) {
        pdialog.show();
        mAPIService.EditProfile(UserId, Name, EMail, MobileNumber, Address, Gender, InterestedCategory).enqueue(new Callback<EditProfileResponse>() {

            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(EditProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(EditProfileActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
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
            if (txt_edit_name.getText().toString().isEmpty())
                txt_edit_name.setError("enter here");
            if (txt_edit_email.getText().toString().isEmpty())
                txt_edit_email.setError("enter here");
//            if (txt_edit_pin.getText().toString().isEmpty())
//                txt_edit_pin.setError("enter here");
            if (txt_edit_phone.getText().toString().isEmpty())
                txt_edit_phone.setError("enter here");
            if (GetID.equals(""))
                Toast.makeText(this, "Please select your Interested Category", Toast.LENGTH_SHORT).show();
            if (GetGender.equals(""))
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            else {
                EditProfilePost(UserID, txt_edit_name.getText().toString(), txt_edit_email.getText().toString(),
                        txt_edit_phone.getText().toString(), txt_edit_pin.getText().toString(), GetGender, GetID);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
