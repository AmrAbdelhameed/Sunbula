package com.example.amr.sunbula.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.MakeReportResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeReportActivity extends AppCompatActivity {

    Button btSubmit;
    EditText ReportName, ReportBody;
    APIService mAPIService;
    String people_id = "", UserID = "", currentDateandTime;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_report);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            UserID = b.getString("UserID");
            people_id = b.getString("people_id");
        }

        ReportName = findViewById(R.id.ReportName);
        ReportBody = findViewById(R.id.ReportBody);
        btSubmit = findViewById(R.id.btSubmit);

        pdialog = new ProgressDialog(MakeReportActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        currentDateandTime = sdf.format(new Date());

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReportBody.getText().toString().isEmpty())
                    ReportBody.setError("Enter Here");
                else if (ReportName.getText().toString().isEmpty())
                    ReportName.setError("Enter Here");
                else
                    MakeReportPost(ReportBody.getText().toString(), ReportName.getText().toString()
                            , currentDateandTime, people_id, UserID);
            }
        });
    }

    public void MakeReportPost(String ReportBody, String ReportName, String ReportDate, String ReportedPerson, String ReporterPerson) {
        pdialog.show();
        mAPIService.MakeReport(ReportBody, ReportName, ReportDate, ReportedPerson, ReporterPerson).enqueue(new Callback<MakeReportResponse>() {

            @Override
            public void onResponse(Call<MakeReportResponse> call, Response<MakeReportResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(MakeReportActivity.this, "Make Report Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(MakeReportActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<MakeReportResponse> call, Throwable t) {
                Toast.makeText(MakeReportActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
