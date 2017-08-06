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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.AddCauseActivity;
import com.example.amr.sunbula.EditProfileActivity;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowModels.AllCausesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.JoinedCasesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.MyCausesProfile;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

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
    Button btn_add_cause;

    List<UserDetailsResponse.AllCasesListBean> allCasesListBeen;
    AllCausesProfile all;
    List<AllCausesProfile> list;

    List<UserDetailsResponse.MyCasesBean> myCasesBeanList;
    MyCausesProfile myCausesProfile;
    List<MyCausesProfile> myCausesProfiles;

    List<UserDetailsResponse.JoinedCasesBean> joinedCasesBeen;
    JoinedCasesProfile joinedCasesProfile;
    List<JoinedCasesProfile> joinedCasesProfiles;

    TextView username_profile, text_reviews_profile, text_causes_profile, text_location_profile, text_history_profile;
    ImageView image_profile;

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

        btn_add_cause = (Button) v.findViewById(R.id.btn_add_cause);

        btn_add_cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddCauseActivity.class);
                startActivity(i);
            }
        });

        FlowManager.init(getActivity());

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
                    replaceFragment(new AllProfileFragment(list));
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new MycausesProfileFragment(myCausesProfiles));
                } else {
                    replaceFragment(new JoinedcausesProfileFragment(joinedCasesProfiles));
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
                    if (response.body().isIsSuccess()) {
                        username_profile.setText(response.body().getName());
                        UserDetailsResponse userDetailsResponse = response.body();

                        allCasesListBeen = new ArrayList<UserDetailsResponse.AllCasesListBean>();
                        myCasesBeanList = new ArrayList<UserDetailsResponse.MyCasesBean>();
                        joinedCasesBeen = new ArrayList<UserDetailsResponse.JoinedCasesBean>();

                        allCasesListBeen = userDetailsResponse.getAllCasesList();
                        myCasesBeanList = userDetailsResponse.getMyCases();
                        joinedCasesBeen = userDetailsResponse.getJoinedCases();

                        list = (new Select().from(AllCausesProfile.class).queryList());
                        if (list.size() > 0) {
                            Delete.table(AllCausesProfile.class);
                        }

                        myCausesProfiles = (new Select().from(MyCausesProfile.class).queryList());
                        if (myCausesProfiles.size() > 0) {
                            Delete.table(MyCausesProfile.class);
                        }

                        joinedCasesProfiles = (new Select().from(JoinedCasesProfile.class).queryList());
                        if (joinedCasesProfiles.size() > 0) {
                            Delete.table(JoinedCasesProfile.class);
                        }

                        for (int i = 0; i < allCasesListBeen.size(); i++) {
                            all = new AllCausesProfile();
                            if (allCasesListBeen.size() > 0) {

                                all.setCaseName(allCasesListBeen.get(i).getCaseName());
                                all.setCaseDescription(allCasesListBeen.get(i).getCaseDescription());
                                all.setJoined(allCasesListBeen.get(i).isIsJoined());
                                all.setOwner(allCasesListBeen.get(i).isIsOwner());
                                all.setAmount(allCasesListBeen.get(i).getAmount());
                                all.setCauseID(allCasesListBeen.get(i).getCauseID());
                                all.setEndDate(allCasesListBeen.get(i).getEndDate());
                                all.setIMG(allCasesListBeen.get(i).getIMG());
                                all.setNumberofjoins(allCasesListBeen.get(i).getNumberofjoins());
                                all.setStatus(allCasesListBeen.get(i).getStatus());

                                all.save();
                            }
                        }

                        for (int i = 0; i < myCasesBeanList.size(); i++) {
                            myCausesProfile = new MyCausesProfile();
                            if (myCasesBeanList.size() > 0) {

                                myCausesProfile.setCaseName(myCasesBeanList.get(i).getCaseName());
                                myCausesProfile.setCaseDescription(myCasesBeanList.get(i).getCaseDescription());
                                myCausesProfile.setJoined(myCasesBeanList.get(i).isIsJoined());
                                myCausesProfile.setOwner(myCasesBeanList.get(i).isIsOwner());
                                myCausesProfile.setAmount(myCasesBeanList.get(i).getAmount());
                                myCausesProfile.setCauseID(myCasesBeanList.get(i).getCauseID());
                                myCausesProfile.setEndDate(myCasesBeanList.get(i).getEndDate());
                                myCausesProfile.setIMG(myCasesBeanList.get(i).getIMG());
                                myCausesProfile.setNumberofjoins(myCasesBeanList.get(i).getNumberofjoins());
                                myCausesProfile.setStatus(myCasesBeanList.get(i).getStatus());

                                myCausesProfile.save();
                            }
                        }

                        for (int i = 0; i < joinedCasesBeen.size(); i++) {
                            joinedCasesProfile = new JoinedCasesProfile();
                            if (joinedCasesBeen.size() > 0) {

                                joinedCasesProfile.setCaseName(joinedCasesBeen.get(i).getCaseName());
                                joinedCasesProfile.setCaseDescription(joinedCasesBeen.get(i).getCaseDescription());
                                joinedCasesProfile.setJoined(joinedCasesBeen.get(i).isIsJoined());
                                joinedCasesProfile.setOwner(joinedCasesBeen.get(i).isIsOwner());
                                joinedCasesProfile.setAmount(joinedCasesBeen.get(i).getAmount());
                                joinedCasesProfile.setCauseID(joinedCasesBeen.get(i).getCauseID());
                                joinedCasesProfile.setEndDate(joinedCasesBeen.get(i).getEndDate());
                                joinedCasesProfile.setIMG(joinedCasesBeen.get(i).getIMG());
                                joinedCasesProfile.setNumberofjoins(joinedCasesBeen.get(i).getNumberofjoins());
                                joinedCasesProfile.setStatus(joinedCasesBeen.get(i).getStatus());

                                joinedCasesProfile.save();
                            }
                        }

                        list = (new Select().from(AllCausesProfile.class).queryList());
                        myCausesProfiles = (new Select().from(MyCausesProfile.class).queryList());
                        joinedCasesProfiles = (new Select().from(JoinedCasesProfile.class).queryList());

                        //replace default fragment
                        replaceFragment(new AllProfileFragment(list));
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                list = (new Select().from(AllCausesProfile.class).queryList());
                //replace default fragment
                replaceFragment(new AllProfileFragment(list));

                myCausesProfiles = (new Select().from(MyCausesProfile.class).queryList());
                joinedCasesProfiles = (new Select().from(JoinedCasesProfile.class).queryList());

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
