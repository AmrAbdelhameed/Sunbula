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

import com.example.amr.sunbula.Models.APIResponses.ListofFollowersResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
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
public class FollowersFragment extends Fragment {

    ListView list_followers;
    ArrayAdapter arrayAdapter;
    String UserID;
    APIService mAPIService;
    List<String> listofPepoleFollowersBeen;
    private ProgressDialog pdialog;

    public FollowersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_followers, container, false);

        listofPepoleFollowersBeen = new ArrayList<String>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        list_followers = (ListView) v.findViewById(R.id.list_item_followers);

        ListofFollowersPost(UserID);

        return v;
    }

    public void ListofFollowersPost(String User_ID) {
        pdialog.show();
        mAPIService.ListofFollowers(User_ID).enqueue(new Callback<ListofFollowersResponse>() {

            @Override
            public void onResponse(Call<ListofFollowersResponse> call, Response<ListofFollowersResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        ListofFollowersResponse followersResponse = response.body();

                        for (int i = 0; i < followersResponse.getListOFFollowers().size(); i++) {
                            listofPepoleFollowersBeen.add(followersResponse.getListOFFollowers().get(i).getName());
                        }

                        Gson gson = new Gson();
                        String jsonFollowers = gson.toJson(listofPepoleFollowersBeen);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("AllFollowers", jsonFollowers);
                        editor.apply();

                        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listofPepoleFollowersBeen);
                        list_followers.setAdapter(arrayAdapter);
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListofFollowersResponse> call, Throwable t) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                String jsonFollowers = sharedPreferences.getString("AllFollowers", "");

                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                listofPepoleFollowersBeen = gson.fromJson(jsonFollowers, type);
                arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listofPepoleFollowersBeen);
                list_followers.setAdapter(arrayAdapter);
                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
