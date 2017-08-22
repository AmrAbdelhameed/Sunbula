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
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
import com.example.amr.sunbula.Models.DBFlowModels.Following;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
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
public class FollowingFragment extends Fragment {

    ListView list_following;
    ArrayAdapter arrayAdapter;
    String UserID;
    APIService mAPIService;
    List<String> listofPepoleFollowingBeen;
    private ProgressDialog pdialog;

    Following following;
    List<Following> followings;

    public FollowingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_following, container, false);

        listofPepoleFollowingBeen = new ArrayList<String>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        list_following = (ListView) v.findViewById(R.id.list_item_following);

        ListofPepolePost(UserID);
        return v;
    }

    public void ListofPepolePost(String User_ID) {
        pdialog.show();
        mAPIService.ListofPepole(User_ID).enqueue(new Callback<ListofPepoleResponse>() {

            @Override
            public void onResponse(Call<ListofPepoleResponse> call, Response<ListofPepoleResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        ListofPepoleResponse listofPepoleResponse = response.body();

                        if (listofPepoleResponse.getListofPepoleFollowing().size() > 0) {

                            followings = (new Select().from(Following.class).queryList());

                            if (followings.size() > 0) {
                                Delete.table(Following.class);
                            }

                            for (int i = 0; i < listofPepoleResponse.getListofPepoleFollowing().size(); i++) {
                                following = new Following();
                                if (listofPepoleResponse.getListofPepoleFollowing().size() > 0) {

                                    following.setFollowID(listofPepoleResponse.getListofPepoleFollowing().get(i).getFollowID());
                                    following.setName(listofPepoleResponse.getListofPepoleFollowing().get(i).getName());

                                    following.save();
                                }
                            }

                            followings = (new Select().from(Following.class).queryList());

                            for (int i = 0; i < followings.size(); i++) {
                                listofPepoleFollowingBeen.add(followings.get(i).getName());
                            }


                            arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listofPepoleFollowingBeen);
                            list_following.setAdapter(arrayAdapter);

                            list_following.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(getActivity(), ShowDetailsUserActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("people_id", followings.get(pos).getFollowID());
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
            public void onFailure(Call<ListofPepoleResponse> call, Throwable t) {

                followings = (new Select().from(Following.class).queryList());

                if (followings.size() > 0) {
                    for (int i = 0; i < followings.size(); i++) {
                        listofPepoleFollowingBeen.add(followings.get(i).getName());
                    }

                    arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listofPepoleFollowingBeen);
                    list_following.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }
}
