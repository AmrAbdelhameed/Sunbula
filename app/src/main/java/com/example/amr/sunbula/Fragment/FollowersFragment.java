package com.example.amr.sunbula.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Activities.ShowDetailsUserActivity;
import com.example.amr.sunbula.Models.APIResponses.ListofFollowersResponse;
import com.example.amr.sunbula.Models.DBFlowModels.Followers;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;
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
public class FollowersFragment extends Fragment {

    ListView list_followers;
    ArrayAdapter arrayAdapter;
    String UserID;
    APIService mAPIService;
    List<String> listofPepoleFollowersBeen;
    private ProgressDialog pdialog;

    Followers follower;
    List<Followers> followerses;

    public FollowersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_followers, container, false);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

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

                        if (followersResponse.getListOFFollowers().size() > 0) {

                            followerses = (new Select().from(Followers.class).queryList());

                            if (followerses.size() > 0) {
                                Delete.table(Followers.class);
                            }

                            for (int i = 0; i < followersResponse.getListOFFollowers().size(); i++) {
                                follower = new Followers();
                                if (followersResponse.getListOFFollowers().size() > 0) {

                                    follower.setFollowID(followersResponse.getListOFFollowers().get(i).getFollowID());
                                    follower.setName(followersResponse.getListOFFollowers().get(i).getName());

                                    follower.save();
                                }
                            }

                            followerses = (new Select().from(Followers.class).queryList());

                            for (int i = 0; i < followerses.size(); i++) {
                                listofPepoleFollowersBeen.add(followerses.get(i).getName());
                            }

                            arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listofPepoleFollowersBeen);
                            list_followers.setAdapter(arrayAdapter);

                            list_followers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(getActivity(), ShowDetailsUserActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("people_id", followerses.get(pos).getFollowID());
                                    i.putExtras(b);
                                    getActivity().startActivity(i);
                                }
                            });
                        }
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListofFollowersResponse> call, Throwable t) {

                followerses = (new Select().from(Followers.class).queryList());

                if (followerses.size() > 0) {
                    for (int i = 0; i < followerses.size(); i++) {
                        listofPepoleFollowersBeen.add(followerses.get(i).getName());
                    }

                    arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listofPepoleFollowersBeen);
                    list_followers.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
