package com.example.amr.sunbula;

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
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.ImageResponse;
import com.example.amr.sunbula.Models.APIResponses.RegistrationResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    de.hdodenhof.circleimageview.CircleImageView user_profile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    EditText username, password, Email;
    Button btn_register;
    LoginButton btn_login_facebok;
    CallbackManager c;
    private APIService mAPIService;
    private ProgressDialog pdialog;
    String imagePath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(RegisterActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        user_profile = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageregister);
        username = (EditText) findViewById(R.id.txtusernameregister);
        password = (EditText) findViewById(R.id.txtpasswordregister);
        Email = (EditText) findViewById(R.id.txtemailregister);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login_facebok = (LoginButton) findViewById(R.id.btn_login_facebok);
        c = CallbackManager.Factory.create();

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = username.getText().toString();
                String EMail = Email.getText().toString();
                String Password = password.getText().toString();

                if (Name.isEmpty()) {
                    username.setError("Please enter here");
                }
                if (EMail.isEmpty()) {
                    Email.setError("Please enter here");
                }
                if (Password.isEmpty()) {
                    password.setError("Please enter here");
                }
                if (imagePath.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please select image for you", Toast.LENGTH_SHORT).show();
                else
                    RegisterPost(1, Name, Password, EMail);

            }
        });

        btn_login_facebok.setReadPermissions("user_friends");
        btn_login_facebok.setReadPermissions("public_profile");
        btn_login_facebok.setReadPermissions("email");
        btn_login_facebok.setReadPermissions("user_birthday");

        LoginManager.getInstance().registerCallback(c,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
//                      1076606079140782
//                      AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();
                        String imageURL = "https://graph.facebook.com/" + profile.getId() + "/picture?type=large";
                        String fbemail = profile.getId() + "@facebook.com";
                        FacebookPost(2, profile.getName(), profile.getId(), fbemail, imageURL);

//                        username.setText(profile.getName());
//                        Picasso.with(RegisterActivity.this)
//                                .load("https://graph.facebook.com/" + profile.getId() + "/picture?type=large")
//                                .into(user_profile);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegisterActivity.this);

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
        c.onActivityResult(requestCode, resultCode, data);
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

            cursor.close();
        } else {
            Toast.makeText(this, R.string.string_unable_to_load_image, Toast.LENGTH_SHORT).show();
        }
        user_profile.setImageBitmap(thumbnail);
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

            cursor.close();

        } else {
            Toast.makeText(this, R.string.string_unable_to_load_image, Toast.LENGTH_SHORT).show();
        }
        user_profile.setImageBitmap(bm);
    }

    public void RegisterPost(int login_type, String name, String password, String email) {
        pdialog.show();
        mAPIService.Register(login_type, name, password, email).enqueue(new Callback<RegistrationResponse>() {

            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getIsSuccess()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        uploadImage(response.body().getUserID());

                        SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserID", response.body().getUserID());
                        editor.commit();
                    } else {
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                        pdialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(RegisterActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void FacebookPost(int login_type, String name, String fcid, String emailface, String imgurl) {
        mAPIService.LoginAsFacebook(login_type, name, fcid, emailface, imgurl).enqueue(new Callback<RegistrationResponse>() {

            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getIsSuccess()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        uploadImage(response.body().getUserID());

                        SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserID", response.body().getUserID());
                        editor.putBoolean("facebookID", true);
                        editor.commit();
                        Toast.makeText(RegisterActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(RegisterActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
            }
        });
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

        // finally, execute the request
        resultCall.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {

                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "Please check your mail to confirmation your email", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, ConfirmEmailActivity.class);
                        startActivity(i);
                        finish();

                    } else
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterActivity.this, R.string.string_upload_fail, Toast.LENGTH_SHORT).show();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}