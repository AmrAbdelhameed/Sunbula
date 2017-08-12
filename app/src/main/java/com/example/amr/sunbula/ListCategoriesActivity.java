package com.example.amr.sunbula;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;
import com.example.amr.sunbula.Adapters.List_CategoriesAdapter;
import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoriesActivity extends AppCompatActivity {
    String UserID;
    ListView listView_allCategoriesBeen;
    APIService mAPIService;
    private ProgressDialog pdialog;
    List_CategoriesAdapter adapter;
    List<AllCategoriesResponse.AllCategoriesBean> allCategoriesBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_categories);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_categories);
        toolbar.setTitle("List Categories");
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = ListCategoriesActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        pdialog = new ProgressDialog(ListCategoriesActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        listView_allCategoriesBeen = (ListView) findViewById(R.id.list_categories);

        GetAllCategories(UserID);
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

                        adapter = new List_CategoriesAdapter(ListCategoriesActivity.this, R.layout.item_in_list_categories, allCategoriesBeen);
                        listView_allCategoriesBeen.setAdapter(adapter);

                    } else
                        Toast.makeText(ListCategoriesActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
                Toast.makeText(ListCategoriesActivity.this, R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
