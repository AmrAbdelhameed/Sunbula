package com.example.amr.sunbula.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.Others.Utility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    String imagePath = "", imageURL = "";
    EditText username, password, Email, txtphone;
    Button btn_register;
    LoginButton btn_login_facebok;
    CallbackManager c;
    ArrayList<String> CountryIDs, CountryNames, CityIDs, CityNames;
    String GetItemGenderList = "", GetIDCountry = "", GetIDCity = "";
    List<AllCountriesResponse.AllCountriesBean> allCountriesBeen;
    List<AllCitiesResponse.AllCitiesBean> allCitiesBeen;
    List<String> CategoriesIDs_in_AddCause, CategoriesNames_in_AddCause, GenderList;
    List<AllCategoriesResponse.AllCategoriesBean> allCategoriesBeen;
    String GetIDCategoires = "";
    private int REQUEST_CAMERA = 100, SELECT_FILE = 1;
    private String userChoosenTask;
    private APIService mAPIService;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FacebookSdk.sdkInitialize(RegisterActivity.this);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(RegisterActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        txtphone = findViewById(R.id.txtphone);
        user_profile = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageregister);
        username = (EditText) findViewById(R.id.txtusernameregister);
        password = (EditText) findViewById(R.id.txtpasswordregister);
        Email = (EditText) findViewById(R.id.txtemailregister);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login_facebok = (LoginButton) findViewById(R.id.btn_login_facebok);
        c = CallbackManager.Factory.create();

        GenderList = new ArrayList<>();

        GenderList.add("Select your sex");
        GenderList.add("Male");
        GenderList.add("Female");

        Spinner spinner_Gender = (Spinner) findViewById(R.id.spinner_Gender);
        ArrayAdapter spinner_GenderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GenderList);
        spinner_GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Gender.setAdapter(spinner_GenderAdapter);

        spinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (!GenderList.get(position).equals("Select your sex")) {
                    GetItemGenderList = GenderList.get(position);
                } else
                    GetItemGenderList = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        CountryNames = new ArrayList<>();
        CountryIDs = new ArrayList<>();

        CountryNames.add("Select your country");
        CountryIDs.add("IDs");

        GetAllCountries();

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
                    SpinnerCity(GetIDCountry);
                } else
                    GetIDCountry = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        CategoriesNames_in_AddCause = new ArrayList<>();
        CategoriesIDs_in_AddCause = new ArrayList<>();

        CategoriesNames_in_AddCause.add("Categories");
        CategoriesIDs_in_AddCause.add("IDs");

        GetAllCategories(null);

        Spinner staticSpinner = (Spinner) findViewById(R.id.spinner_Categories_Register);
        ArrayAdapter staticAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CategoriesNames_in_AddCause);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (!CategoriesNames_in_AddCause.get(position).equals("Categories")) {
                    GetIDCategoires = CategoriesIDs_in_AddCause.get(position);
//                    Toast.makeText(RegisterActivity.this, GetIDCategoires, Toast.LENGTH_SHORT).show();
                } else
                    GetIDCategoires = "";
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

                if (Name.isEmpty())
                    username.setError("Please enter here");
                else if (EMail.isEmpty())
                    Email.setError("Please enter here");
                else if (Password.isEmpty())
                    password.setError("Please enter here");
                else if (GetIDCountry.isEmpty())
                    Toast.makeText(RegisterActivity.this, "Please select your country", Toast.LENGTH_SHORT).show();
                else if (GetIDCity.isEmpty())
                    Toast.makeText(RegisterActivity.this, "Please select your city", Toast.LENGTH_SHORT).show();
                else if (GetIDCategoires.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please select your Interested Category", Toast.LENGTH_SHORT).show();
                else if (GetItemGenderList.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please select your Interested Category", Toast.LENGTH_SHORT).show();
                else
                    RegisterPost(1, Name, Password, EMail, GetIDCity, txtphone.getText().toString(), GetItemGenderList, GetIDCategoires, imageURL);

            }
        });

        btn_login_facebok.setReadPermissions("email");
        btn_login_facebok.setReadPermissions("public_profile");
        btn_login_facebok.setReadPermissions("user_friends");

        LoginManager.getInstance().registerCallback(c,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
//                          1076606079140782

                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken", accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("RegisterActivity", response.toString());
                                // Get facebook data from login
                                Bundle bFacebookData = getFacebookData(object);

                                String id = loginResult.getAccessToken().getUserId();
                                String imageURL = "https://graph.facebook.com/" + id + "/picture?type=large";
                                String fbemail = id + "@facebook.com";

                                assert bFacebookData != null;
                                String Name = bFacebookData.getString("first_name") + " " + bFacebookData.getString("last_name");
                                String Gender = bFacebookData.getString("gender");

                                assert Gender != null;
                                Gender = Gender.substring(0, 1).toUpperCase() + Gender.substring(1).toLowerCase();

                                FacebookPost(2, Name, id, fbemail, imageURL, GetIDCity, txtphone.getText().toString(), Gender, GetIDCategoires);
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();

//                        Profile profile = Profile.getCurrentProfile();
//                        String imageURL = "https://graph.facebook.com/" + profile.getId() + "/picture?type=large";
//                        String fbemail = profile.getId() + "@facebook.com";
//
//                        if (GetIDCountry.isEmpty())
//                            Toast.makeText(RegisterActivity.this, "Please select your country", Toast.LENGTH_SHORT).show();
//                        else if (GetIDCity.isEmpty())
//                            Toast.makeText(RegisterActivity.this, "Please select your city", Toast.LENGTH_SHORT).show();
//                        else if (GetIDCategoires.equals(""))
//                            Toast.makeText(RegisterActivity.this, "Please select your Interested Category", Toast.LENGTH_SHORT).show();
//
//                        else {
//                            FacebookPost(2, profile.getName(), profile.getId(), fbemail, imageURL, GetIDCity, GetIDCategoires);
//                        }

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

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            Log.d(TAG, "Error parsing JSON");
        }
        return null;
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
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
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

    private void SpinnerCity(String body) {
        CityIDs = new ArrayList<>();
        CityNames = new ArrayList<>();

        CityNames.add("Select your city");
        CityIDs.add("IDs");

        GetAllCities(body);

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
                } else
                    GetIDCity = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
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
//            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();

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

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert bm != null;
        bm.compress(Bitmap.CompressFormat.JPEG, REQUEST_CAMERA, bytes);
        Uri uri;
        Cursor cursor = RegisterActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED, null, "date_added DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                imagePath = uri.toString();
//                Log.d("pathatka", uri.toString());
//                Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
                break;
            } while (cursor.moveToNext());
            cursor.close();

        } else {
            Toast.makeText(this, R.string.string_unable_to_load_image, Toast.LENGTH_SHORT).show();
        }
        user_profile.setImageBitmap(bm);
    }

    public void RegisterPost(int login_type, String name, String password, String email, String cityID, String MobileNumber, String Gender, String CategoryID, String ImgURL) {
        pdialog.show();
        mAPIService.Register(login_type, name, password, email, cityID, MobileNumber, Gender, CategoryID, ImgURL).enqueue(new Callback<RegistrationResponse>() {

            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getIsSuccess()) {
                        uploadImage(response.body().getUserID());
                        Toast.makeText(RegisterActivity.this, "Please check your mail to confirmation your email", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, ConfirmEmailActivity.class);
                        Bundle b = new Bundle();
                        b.putString("UserID", response.body().getUserID());
                        i.putExtras(b);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else
                        Toast.makeText(RegisterActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    public void FacebookPost(int login_type, String name, String fcid, String emailface, String imgurl, String cityID, String MobileNumber, String Gender, String CategoryID) {
        mAPIService.LoginAsFacebook(login_type, name, fcid, emailface, imgurl, cityID, MobileNumber, Gender, CategoryID).enqueue(new Callback<RegistrationResponse>() {

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
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        pdialog.show();
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
                        imageURL = response.body().getImageURL();
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
        mAPIService.AllCountries(null).enqueue(new Callback<AllCountriesResponse>() {

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}