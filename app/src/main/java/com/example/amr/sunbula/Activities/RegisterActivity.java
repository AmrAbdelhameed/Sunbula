package com.example.amr.sunbula.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.Models.APIResponses.AllCitiesResponse;
import com.example.amr.sunbula.Models.APIResponses.AllCountriesResponse;
import com.example.amr.sunbula.Models.APIResponses.ImageResponse;
import com.example.amr.sunbula.Models.APIResponses.RegistrationResponse;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.Utility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    de.hdodenhof.circleimageview.CircleImageView user_profile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String imagePath = "";
    EditText username, password, Email;
    Button btn_register;
    LoginButton btn_login_facebok;
    CallbackManager c;
    private APIService mAPIService;
    private ProgressDialog pdialog;
    ArrayList<String> CountryIDs, CountryNames;
    String GetIDCountry = "";
    List<AllCountriesResponse.AllCountriesBean> allCountriesBeen;
    ArrayList<String> CityIDs, CityNames;
    String GetIDCity = "";
    List<AllCitiesResponse.AllCitiesBean> allCitiesBeen;

    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    //uri to store file
    private Uri filePath;
    String imageurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

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

        CountryNames = new ArrayList<>();
        CountryIDs = new ArrayList<>();

        CountryNames.add("Select your country");
        CountryIDs.add("IDs");

//        GetAllCountries();

        Spinner spinner_countries = (Spinner) findViewById(R.id.spinner_countries);
        ArrayAdapter spinner_countriesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CountryNames);
        spinner_countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_countries.setAdapter(spinner_countriesAdapter);

        spinner_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (!CountryNames.get(position).equals("Select your country")) {
                    GetIDCountry = CountryIDs.get(position);
                    Toast.makeText(RegisterActivity.this, GetIDCountry, Toast.LENGTH_SHORT).show();
                } else
                    GetIDCountry = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        CityIDs = new ArrayList<>();
        CityNames = new ArrayList<>();

        CityNames.add("Select your city");
        CityIDs.add("IDs");

        GetAllCities("9ffec365-09d9-40a7-bb8d-028d246f12d5");

        Spinner spinner_cities = (Spinner) findViewById(R.id.spinner_cities);
        ArrayAdapter spinner_citiesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CityNames);
        spinner_citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cities.setAdapter(spinner_citiesAdapter);

        spinner_cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (!CityNames.get(position).equals("Select your city")) {
                    GetIDCity = CityIDs.get(position);
                    Toast.makeText(RegisterActivity.this, GetIDCity, Toast.LENGTH_SHORT).show();
                } else
                    GetIDCity = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

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
                } else {
//                    RegisterPost(1, Name, Password, EMail);
                    (firebaseAuth.createUserWithEmailAndPassword(EMail, Password))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        if (!imagePath.isEmpty())
                                            uploadFile();
                                        else
                                            DataSavedonFirebase();
                                    } else {
                                        Log.e("ERROR", task.getException().toString());
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

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

                        Profile profile = Profile.getCurrentProfile();
                        String imageURL = "https://graph.facebook.com/" + profile.getId() + "/picture?type=large";
                        String fbemail = profile.getId() + "@facebook.com";
                        FacebookPost(2, profile.getName(), profile.getId(), fbemail, imageURL);

                    }

                    @Override
                    public void onCancel() {
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onError(FacebookException exception) {
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
        filePath = tempUri;
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
            filePath = data.getData();
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
                        if (!imagePath.equals(""))
                            uploadImage(response.body().getUserID());
                        else {
                            pdialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Please check your mail to confirmation your email", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, ConfirmEmailActivity.class);
                            Bundle b = new Bundle();
                            b.putString("UserID", response.body().getUserID());
                            i.putExtras(b);
                            startActivity(i);
                            finish();
                        }
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
                        editor.putBoolean("isVerified", false);
                        editor.putBoolean("facebookID", true);
                        editor.apply();
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

    private void uploadImage(final String UserId) {

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
                        Bundle b = new Bundle();
                        b.putString("UserID", UserId);
                        i.putExtras(b);
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

    public void GetAllCountries() {
        pdialog.show();
        mAPIService.AllCountries().enqueue(new Callback<AllCountriesResponse>() {

            @Override
            public void onResponse(Call<AllCountriesResponse> call, Response<AllCountriesResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        AllCountriesResponse allCategoriesResponse = response.body();

                        allCountriesBeen = new ArrayList<AllCountriesResponse.AllCountriesBean>();

                        allCountriesBeen = allCategoriesResponse.getAllCountries();

                        for (int i = 0; i < allCountriesBeen.size(); i++) {
                            CountryIDs.add(allCountriesBeen.get(i).getCountryID());
                            CountryNames.add(allCountriesBeen.get(i).getCountryname());
                        }
                    } else
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllCountriesResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void GetAllCities(String CountryID) {
        pdialog.show();
        mAPIService.AllCities(CountryID).enqueue(new Callback<AllCitiesResponse>() {

            @Override
            public void onResponse(Call<AllCitiesResponse> call, Response<AllCitiesResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        AllCitiesResponse allCitiesResponse = response.body();

                        allCitiesBeen = new ArrayList<AllCitiesResponse.AllCitiesBean>();

                        allCitiesBeen = allCitiesResponse.getAllCities();

                        for (int i = 0; i < allCitiesBeen.size(); i++) {
                            CityIDs.add(allCitiesBeen.get(i).getCityID());
                            CityNames.add(allCitiesBeen.get(i).getCityName());
                        }
                    } else
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllCitiesResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // el m2sood be file upload hwa el image ene agebha mn el gallery w b3d ma tkon m7tota fel image view awdeha lel firebase w a5odha fe string as url
    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child("users/" + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    //displaying success toast
                    //       Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                    // ba5odha fe string as url
                    imageurl = taskSnapshot.getDownloadUrl().toString();

                    DataSavedonFirebase();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //displaying the upload progress
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "File Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void DataSavedonFirebase() {
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String s = mDatabase.push().getKey();

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

        userDetailsResponse.setEMail(Email.getText().toString());
        userDetailsResponse.setImgURL(imageurl);
        userDetailsResponse.setName(username.getText().toString());
        userDetailsResponse.setPassword(password.getText().toString());
        userDetailsResponse.setFacebookID("123");

        // b3ml save fel firebase lel object of admin
        mDatabase.child("users").child(s).setValue(userDetailsResponse);
        mDatabase.child("users").child(s).child("Login_Type").setValue(1);

        SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserID", s);
        editor.putString("UserName", username.getText().toString());
        editor.putString("UserImgURL", imageurl);
        editor.putString("UserEmail", Email.getText().toString());
        editor.apply();

        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
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