package com.example.amr.sunbula.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.Models.APIResponses.EditCauseResponse;
import com.example.amr.sunbula.Models.APIResponses.ImageResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.Others.Utility;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCauseActivity extends AppCompatActivity {

    TextView txt_calender;
    EditText txt_add_description_editcause, name_editcause, amount_editcause;
    ArrayList<String> CategoriesNames_in_AddCause;
    ArrayList<String> CategoriesIDs_in_AddCause;
    String GetID = "";
    Calendar myCalendar;
    String UserID;
    String CauseID;
    APIService mAPIService;
    List<AllCategoriesResponse.AllCategoriesBean> allCategoriesBeen;
    DatePickerDialog.OnDateSetListener date;
    de.hdodenhof.circleimageview.CircleImageView image_editcause;
    String imagePath = "", imageURL = "";
    private ProgressDialog pdialog;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cause);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_cause);
        toolbar.setTitle("Edit Case");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pdialog = new ProgressDialog(EditCauseActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        txt_add_description_editcause = (EditText) findViewById(R.id.txt_add_description_editcause);
        amount_editcause = (EditText) findViewById(R.id.amount_editcause);
        name_editcause = (EditText) findViewById(R.id.name_editcause);
        txt_calender = (TextView) findViewById(R.id.txt_calender_edit);

        SharedPreferences sharedPreferences = EditCauseActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        myCalendar = Calendar.getInstance();
        image_editcause = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.image_editcause);

        image_editcause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        Intent in = getIntent();
        Bundle b = in.getExtras();
        CauseID = b.getString("CauseID");
        String Name = b.getString("Name");
        int Amount = b.getInt("Amount");
        String EndDate = b.getString("EndDate");
        String CauseDescription = b.getString("CauseDescription");
        String Image = b.getString("Image");

//        Toast.makeText(this, Image, Toast.LENGTH_SHORT).show();
        Picasso.with(EditCauseActivity.this).load(Image).into(image_editcause);

        txt_add_description_editcause.setText(CauseDescription);
        name_editcause.setText(Name);
        amount_editcause.setText(String.valueOf(Amount));
        txt_calender.setText(EndDate);
//        Toast.makeText(this, CauseID, Toast.LENGTH_SHORT).show();

        txt_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditCauseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        CategoriesNames_in_AddCause = new ArrayList<>();
        CategoriesIDs_in_AddCause = new ArrayList<>();

        CategoriesNames_in_AddCause.add("Categories");
        CategoriesIDs_in_AddCause.add("IDs");

        GetAllCategories(UserID);

        Spinner staticSpinner = (Spinner) findViewById(R.id.spinner_EditCategories);

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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
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

        AlertDialog.Builder builder = new AlertDialog.Builder(EditCauseActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditCauseActivity.this);

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
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        image_editcause.setImageBitmap(thumbnail);
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
//        Uri selectedImageUri = data.getData();
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            imagePath = cursor.getString(columnIndex);
//            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
//
//            cursor.close();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert bm != null;
        bm.compress(Bitmap.CompressFormat.JPEG, REQUEST_CAMERA, bytes);
        Uri uri;
        Cursor cursor = EditCauseActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED, null, "date_added DESC");
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

        image_editcause.setImageBitmap(bm);
    }

    public void uploadImage(final String UserId) {
        pdialog.show();
        //File creating from selected URL
        File file = new File(imagePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<ImageResponse> resultCall = mAPIService.UploadPicture(UserId, body);

        // finally, execute the request
        resultCall.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {

                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        imageURL = response.body().getImageURL();
                        EditCause(CauseID, name_editcause.getText().toString(), amount_editcause.getText().toString(), GetID,
                                txt_calender.getText().toString(), txt_add_description_editcause.getText().toString(), imageURL);

                    } else
                        Toast.makeText(EditCauseActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(EditCauseActivity.this, R.string.string_upload_fail, Toast.LENGTH_SHORT).show();
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

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_calender.setText(sdf.format(myCalendar.getTime()));
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
                        Toast.makeText(EditCauseActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }

    public void EditCause(String cause_id, String name, String amount, String cat_id, String end_date, String cat_desc, String IMG) {
        pdialog.show();
        mAPIService.EditCause(cause_id, name, amount, cat_id, end_date, cat_desc, IMG, 1).enqueue(new Callback<EditCauseResponse>() {

            @Override
            public void onResponse(Call<EditCauseResponse> call, Response<EditCauseResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        {
                            Toast.makeText(EditCauseActivity.this, "Updated case successfully", Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(EditCauseActivity.this, HomeActivity.class);
//                            Bundle b = new Bundle();
//                            b.putBoolean("GoToProfile", true);
//                            i.putExtras(b);
//                            startActivity(i);
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    } else
                        Toast.makeText(EditCauseActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<EditCauseResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_cause, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addcause) {
            if (txt_add_description_editcause.getText().toString().isEmpty())
                txt_add_description_editcause.setError("enter here");
            if (name_editcause.getText().toString().isEmpty())
                name_editcause.setError("enter here");
            if (amount_editcause.getText().toString().isEmpty())
                amount_editcause.setError("enter here");
            else {
                uploadImage(UserID);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
