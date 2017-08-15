package com.example.amr.sunbula.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.All_inProfileFragmentAdapter;
import com.example.amr.sunbula.Adapters.List_CategoriesAdapter;
import com.example.amr.sunbula.ListCategoriesActivity;
import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.Models.APIResponses.GetAllReviewsResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.AllCausesProfileWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    ListView list_reviews;
    ArrayAdapter arrayAdapter;
    String UserID;
    APIService mAPIService;
    List<String> list_reviewsBeen;
    private ProgressDialog pdialog;

    public ReviewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reviews, container, false);

        list_reviewsBeen = new ArrayList<String>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        list_reviews = (ListView) v.findViewById(R.id.list_item_reviews);
        GetAllReviewsPost(UserID);
        return v;
    }

    public void GetAllReviewsPost(String User_ID) {
        pdialog.show();
        mAPIService.GetAllReviews(User_ID).enqueue(new Callback<GetAllReviewsResponse>() {

            @Override
            public void onResponse(Call<GetAllReviewsResponse> call, Response<GetAllReviewsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        GetAllReviewsResponse listofPepoleResponse = response.body();

                        for (int i = 0; i < listofPepoleResponse.getAllUsersReview().size(); i++) {
                            list_reviewsBeen.add(listofPepoleResponse.getAllUsersReview().get(i).getReviewBody());
                        }
                        Gson gson = new Gson();
                        String jsonReviews = gson.toJson(list_reviewsBeen);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("AllReviews", jsonReviews);
                        editor.apply();

                        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_reviewsBeen);
                        list_reviews.setAdapter(arrayAdapter);
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAllReviewsResponse> call, Throwable t) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                String jsonReviews = sharedPreferences.getString("AllReviews", "");

                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                list_reviewsBeen = gson.fromJson(jsonReviews, type);
                arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_reviewsBeen);
                list_reviews.setAdapter(arrayAdapter);
                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
