package com.example.amr.sunbula.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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
import com.example.amr.sunbula.Models.APIResponses.ImageResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.Utility;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    List<String> CategoriesIDs_in_AddCause, CategoriesNames_in_AddCause, Gender_arraylist;
    String Name = "", Email = "", mNumber = "", Gender = "", imageURL = "", InterestedCategory = "";
    String GetID = "", GetGender = "";
    EditText txt_edit_name, txt_edit_email, txt_edit_phone;
    String UserID;
    APIService mAPIService;
    List<AllCategoriesResponse.AllCategoriesBean> allCategoriesBeen;
    de.hdodenhof.circleimageview.CircleImageView new_image_profile;
    String imagePath = "";
    private ProgressDialog pdialog;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editprofile);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new_image_profile = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.new_image_profile);
        txt_edit_name = (EditText) findViewById(R.id.txt_edit_name);
        txt_edit_email = (EditText) findViewById(R.id.txt_edit_email);
//        txt_edit_pin = (EditText) findViewById(R.id.txt_edit_pin);
        txt_edit_phone = (EditText) findViewById(R.id.txt_edit_phone);
//
        Gender_arraylist = new ArrayList<>();
        CategoriesNames_in_AddCause = new ArrayList<>();
        CategoriesIDs_in_AddCause = new ArrayList<>();
//
        pdialog = new ProgressDialog(EditProfileActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();
//
        SharedPreferences sharedPreferences = EditProfileActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        new_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
//
        Intent in = getIntent();
        Bundle b = in.getExtras();
        imageURL = b.getString("imageURL");
        Name = b.getString("Name");
        Email = b.getString("Email");
        mNumber = b.getString("mNumber");
//        Address = b.getString("Address");
        Gender = b.getString("Gender");
        InterestedCategory = b.getString("InterestedCategory");

        Picasso.with(EditProfileActivity.this).load(imageURL).into(new_image_profile);
        txt_edit_name.setText(Name);
        txt_edit_email.setText(Email);
//        txt_edit_pin.setText(Address);
        txt_edit_phone.setText(mNumber);

        CategoriesNames_in_AddCause.add("Interested Categories");
        CategoriesIDs_in_AddCause.add("IDs");

        GetAllCategories(UserID);

//        int pos = CategoriesIDs_in_AddCause.indexOf(InterestedCategory);
//        String value = CategoriesNames_in_AddCause.get(pos);
//
//        CategoriesIDs_in_AddCause.remove(pos);
//        CategoriesNames_in_AddCause.remove(pos);
//        CategoriesIDs_in_AddCause.add(0, InterestedCategory);
//        CategoriesNames_in_AddCause.add(0, value);

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

                if (!CategoriesNames_in_AddCause.get(position).equals("Interested Categories")) {
                    GetID = CategoriesIDs_in_AddCause.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Gender_arraylist.add("Male");
        Gender_arraylist.add("Female");

        int pos3 = Gender_arraylist.indexOf(Gender);
        Gender_arraylist.remove(pos3);
        Gender_arraylist.add(0, Gender);

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

                GetGender = Gender_arraylist.get(position);
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
                // Toast.makeText(EditProfileActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                Log.e("Message: ", t.getMessage(), t);
                pdialog.dismiss();
            }
        });
    }

    public void EditProfilePost(final String UserId, String Name, String EMail, String MobileNumber, String Gender, String InterestedCategory) {
        pdialog.show();
        mAPIService.EditProfile(UserId, Name, EMail, MobileNumber, Gender, InterestedCategory).enqueue(new Callback<EditProfileResponse>() {

            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        if (!"".equals(imagePath))
                            uploadImage(UserID);

                        Toast.makeText(EditProfileActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
                        Bundle b = new Bundle();
                        b.putBoolean("GoToProfile", true);
                        i.putExtras(b);
                        startActivity(i);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("From Camera"))
                        cameraIntent();
                    else if (userChoosenTask.equals("From Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"From Camera", "From Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditProfileActivity.this);

                if (items[item].equals("From Camera")) {
                    userChoosenTask = "From Camera";
                    if (result) {
                        cameraIntent();
                    }
                } else if (items[item].equals("From Library")) {
                    userChoosenTask = "From Library";
                    if (result) {
                        galleryIntent();
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), thumbnail, "Title", null);
        Uri tempUri = Uri.parse(path);

        Cursor cursor = getContentResolver().query(tempUri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            imagePath = cursor.getString(idx);
            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();

            cursor.close();
        } else {
            Toast.makeText(this, R.string.string_unable_to_load_image, Toast.LENGTH_SHORT).show();
        }
        new_image_profile.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri selectedImageUri = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();

            cursor.close();

        } else {
            Toast.makeText(this, R.string.string_unable_to_load_image, Toast.LENGTH_SHORT).show();
        }
        new_image_profile.setImageBitmap(bm);
    }

    private void uploadImage(String UserId) {

        //File creating from selected URL
        File file = new File(imagePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<ImageResponse> resultCall = mAPIService.UploadImageRegister(UserId, body);
        pdialog.show();
        // finally, execute the request
        resultCall.enqueue(new Callback<ImageResponse>() {

            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {

                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (!response.body().isSuccess())
                        Toast.makeText(EditProfileActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, R.string.string_upload_fail, Toast.LENGTH_SHORT).show();
                }

                pdialog.dismiss();

                imagePath = "";
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
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
            if (txt_edit_phone.getText().toString().isEmpty())
                txt_edit_phone.setError("enter here");
            if (GetID.equals(""))
                Toast.makeText(this, "Please select your Interested Category", Toast.LENGTH_SHORT).show();
            else {
                EditProfilePost(UserID, txt_edit_name.getText().toString(), txt_edit_email.getText().toString(),
                        txt_edit_phone.getText().toString(), GetGender, GetID);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
            Bundle b = new Bundle();
            b.putBoolean("GoToProfile", true);
            i.putExtras(b);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("GoToProfile", true);
        i.putExtras(b);
        startActivity(i);
        finish();
        return true;
    }
}
