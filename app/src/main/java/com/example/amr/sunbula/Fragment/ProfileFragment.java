package com.example.amr.sunbula.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amr.sunbula.EditProfileActivity;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TabLayout tabLayout;
    private LinearLayout container;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;
    List<UserDetailsResponse.AllCasesListBean> allCasesListBeen;
    List<UserDetailsResponse.MyCasesBean> myCasesBeanList;

    TextView username_profile, text_reviews_profile, text_causes_profile, text_location_profile, text_history_profile;
    ImageView image_profile;
    String s;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        setHasOptionsMenu(true);

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        container = (LinearLayout) v.findViewById(R.id.fragment_container);
        username_profile = (TextView) v.findViewById(R.id.username_profile);

        //create tabs title
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("My Causes"));
        tabLayout.addTab(tabLayout.newTab().setText("Joined"));

        MyDetailsPost(UserID);

        //handling tab click event
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new AllProfileFragment(allCasesListBeen));
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new MycausesProfileFragment(myCasesBeanList));
                } else {
                    replaceFragment(new JoinedcausesProfileFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    public void MyDetailsPost(String UserId) {
        pdialog.show();
        mAPIService.UserDetails(UserId).enqueue(new Callback<UserDetailsResponse>() {

            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if (response.isSuccessful()) {
                    username_profile.setText(response.body().getName());
                    s = response.body().getName();

                    allCasesListBeen = new ArrayList<UserDetailsResponse.AllCasesListBean>();
                    myCasesBeanList = new ArrayList<UserDetailsResponse.MyCasesBean>();

                    UserDetailsResponse userDetailsResponse = response.body();
                    allCasesListBeen = userDetailsResponse.getAllCasesList();
                    myCasesBeanList = userDetailsResponse.getMyCases();

                    //replace default fragment
                    replaceFragment(new AllProfileFragment(allCasesListBeen));
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("UserID", "");
//                editor.putBoolean("isVerified", false);
//                editor.putBoolean("facebookID", false);
//                editor.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
