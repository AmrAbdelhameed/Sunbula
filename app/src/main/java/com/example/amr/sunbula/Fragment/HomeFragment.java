package com.example.amr.sunbula.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.amr.sunbula.Adapters.HomeFragmentAdapter;
import com.example.amr.sunbula.Models.DBFlowModels.NewsFeed;
import com.example.amr.sunbula.Models.APIResponses.NewsfeedResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.NewsFeedWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.Activities.SearchCauses_People;
import com.google.firebase.crash.FirebaseCrash;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listView;
    private HomeFragmentAdapter adapter;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;
    NewsFeed n;
    List<NewsFeed> list;
    List<NewsFeedWrapper> newsFeedWrappers;
    List<NewsfeedResponse.MyANDJoinedCasesListBean> myANDJoinedCasesListBeen;
    List<NewsfeedResponse.FollowingCassesListBean> followingCassesListBeen;
    boolean check_con = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        setHasOptionsMenu(true);

        newsFeedWrappers = new ArrayList<>();

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        listView = (ListView) v.findViewById(R.id.list_item_home);

        News_FeedPost(UserID);

        return v;
    }

    public void News_FeedPost(String UserId) {
        pdialog.show();
        mAPIService.News_Feed(UserId).enqueue(new Callback<NewsfeedResponse>() {

            @Override
            public void onResponse(Call<NewsfeedResponse> call, Response<NewsfeedResponse> response) {

                if (response.isSuccessful()) {
                    check_con = true;

                    NewsfeedResponse newsfeedResponse = response.body();

                    list = (new Select().from(NewsFeed.class).queryList());

                    if (list.size() > 0) {
                        Delete.table(NewsFeed.class);
                    }

                    if (newsfeedResponse.getMyANDJoinedCasesList().size() > 0) {
                        myANDJoinedCasesListBeen = new ArrayList<NewsfeedResponse.MyANDJoinedCasesListBean>();

                        myANDJoinedCasesListBeen = newsfeedResponse.getMyANDJoinedCasesList();


                        for (int i = 0; i < myANDJoinedCasesListBeen.size(); i++) {
                            n = new NewsFeed();
                            if (myANDJoinedCasesListBeen.size() > 0) {

                                n.setCaseName(myANDJoinedCasesListBeen.get(i).getCaseName());
                                n.setCaseDescription(myANDJoinedCasesListBeen.get(i).getCaseDescription());
                                n.setJoined(myANDJoinedCasesListBeen.get(i).isIsJoined());
                                n.setOwner(myANDJoinedCasesListBeen.get(i).isIsOwner());
                                n.setAmount(myANDJoinedCasesListBeen.get(i).getAmount());
                                n.setCauseID(myANDJoinedCasesListBeen.get(i).getCauseID());
                                n.setEndDate(myANDJoinedCasesListBeen.get(i).getEndDate());
                                n.setIMG(myANDJoinedCasesListBeen.get(i).getIMG());
                                n.setNumberofjoins(myANDJoinedCasesListBeen.get(i).getNumberofjoins());
                                n.setStatus(myANDJoinedCasesListBeen.get(i).getStatus());

                                n.save();
                            }
                        }
                    }
                    if (newsfeedResponse.getFollowingCassesList().size() > 0) {
                        followingCassesListBeen = new ArrayList<NewsfeedResponse.FollowingCassesListBean>();
                        followingCassesListBeen = newsfeedResponse.getFollowingCassesList();

                        for (int i = 0; i < followingCassesListBeen.size(); i++) {
                            n = new NewsFeed();
                            if (followingCassesListBeen.size() > 0) {

                                n.setCaseName(followingCassesListBeen.get(i).getCaseName());
                                n.setCaseDescription(followingCassesListBeen.get(i).getCaseDescription());
                                n.setJoined(followingCassesListBeen.get(i).isIsJoined());
                                n.setOwner(followingCassesListBeen.get(i).isIsOwner());
                                n.setAmount(followingCassesListBeen.get(i).getAmount());
                                n.setCauseID(followingCassesListBeen.get(i).getCauseID());
                                n.setEndDate(followingCassesListBeen.get(i).getEndDate());
                                n.setIMG(followingCassesListBeen.get(i).getIMG());
                                n.setNumberofjoins(followingCassesListBeen.get(i).getNumberofjoins());
                                n.setStatus(followingCassesListBeen.get(i).getStatus());

                                n.save();
                            }
                        }
                    }
                    list = (new Select().from(NewsFeed.class).queryList());

                    if (list.size() > 0) {
                        for (int aa = 0; aa < list.size(); aa++) {
                            NewsFeedWrapper newsFeedWrapper = new NewsFeedWrapper(list.get(aa));
                            newsFeedWrappers.add(newsFeedWrapper);
                        }
                        adapter = new HomeFragmentAdapter(getActivity(), newsFeedWrappers);
                        listView.setDivider(null);
                        listView.setAdapter(adapter);
                    } else
                        Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<NewsfeedResponse> call, Throwable t) {

                list = (new Select().from(NewsFeed.class).queryList());

                if (list.size() > 0) {
                    for (int aa = 0; aa < list.size(); aa++) {
                        NewsFeedWrapper newsFeedWrapper = new NewsFeedWrapper(list.get(aa));
                        newsFeedWrappers.add(newsFeedWrapper);
                    }
                    adapter = new HomeFragmentAdapter(getActivity(), newsFeedWrappers);
                    listView.setDivider(null);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                if (check_con) {
                    Intent intent = new Intent(getActivity(), SearchCauses_People.class);
                    startActivity(intent);
                } else
                    Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
