package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.MakeReviewResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeReviewActivity extends AppCompatActivity {

    Button btSubmit, bt1, bt2, bt3, bt4, bt5;
    int value = 0;
    EditText ReviewBody;
    APIService mAPIService;
    String people_id = "";
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_review);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            people_id = b.getString("people_id");
        }

        btSubmit = findViewById(R.id.btSubmit);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);

        ReviewBody = findViewById(R.id.ReviewBody);

        pdialog = new ProgressDialog(MakeReviewActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt2.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                bt3.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                bt4.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                bt5.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                value = 1;
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt2.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt3.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                bt4.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                bt5.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                value = 2;
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt2.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt3.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt4.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                bt5.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                value = 3;
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt2.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt3.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt4.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt5.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                value = 4;
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt2.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt3.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt4.setBackgroundResource(R.drawable.ic_star_black_24dp);
                bt5.setBackgroundResource(R.drawable.ic_star_black_24dp);
                value = 5;
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReviewBody.getText().toString().isEmpty())
                    ReviewBody.setError("Enter here");
                else
                    MakeReviewPost(people_id, String.valueOf(value), ReviewBody.getText().toString());
            }
        });
    }

    public void MakeReviewPost(String ReviweeID, String ReviewStars, String ReviewBody) {
        pdialog.show();
        mAPIService.MakeReview(ReviweeID, ReviewStars, ReviewBody).enqueue(new Callback<MakeReviewResponse>() {

            @Override
            public void onResponse(Call<MakeReviewResponse> call, Response<MakeReviewResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(MakeReviewActivity.this, "Make Review Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(MakeReviewActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<MakeReviewResponse> call, Throwable t) {
                Toast.makeText(MakeReviewActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
