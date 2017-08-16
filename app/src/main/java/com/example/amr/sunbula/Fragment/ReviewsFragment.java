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
import com.example.amr.sunbula.Models.DBFlowModels.Reviews;
import com.example.amr.sunbula.Models.DBFlowWrappers.AllCausesProfileWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

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

    Reviews reviews;
    List<Reviews> reviewsList;

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

        FlowManager.init(getActivity());

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

                        reviewsList = (new Select().from(Reviews.class).queryList());

                        if (reviewsList.size() > 0) {
                            Delete.table(Reviews.class);
                        }

                        for (int i = 0; i < listofPepoleResponse.getAllUsersReview().size(); i++) {
                            reviews = new Reviews();
                            if (listofPepoleResponse.getAllUsersReview().size() > 0) {

                                reviews.setReviewID(listofPepoleResponse.getAllUsersReview().get(i).getReviewID());
                                reviews.setReviedPerson(listofPepoleResponse.getAllUsersReview().get(i).getReviedPerson());
                                reviews.setReviewBody(listofPepoleResponse.getAllUsersReview().get(i).getReviewBody());
                                reviews.setReviewStar(listofPepoleResponse.getAllUsersReview().get(i).getReviewStar());

                                reviews.save();
                            }
                        }

                        reviewsList = (new Select().from(Reviews.class).queryList());

                        for (int i = 0; i < reviewsList.size(); i++) {
                            list_reviewsBeen.add(reviewsList.get(i).getReviewBody());
                        }

                        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_reviewsBeen);
                        list_reviews.setAdapter(arrayAdapter);
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetAllReviewsResponse> call, Throwable t) {

                reviewsList = (new Select().from(Reviews.class).queryList());

                if (reviewsList.size() > 0) {
                    for (int i = 0; i < reviewsList.size(); i++) {
                        list_reviewsBeen.add(reviewsList.get(i).getReviewBody());
                    }

                    arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_reviewsBeen);
                    list_reviews.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
