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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.AddCauseActivity;
import com.example.amr.sunbula.EditProfileActivity;
import com.example.amr.sunbula.ListCategoriesActivity;
import com.example.amr.sunbula.LoginActivity;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowModels.AllCausesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.JoinedCasesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.MyCausesProfile;
import com.example.amr.sunbula.Models.DBFlowModels.NewsFeed;
import com.example.amr.sunbula.Models.DBFlowWrappers.AllCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.JoinedCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.MyCausesProfileWrapper;
import com.example.amr.sunbula.Models.DBFlowWrappers.NewsFeedWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.Reviews_Following_FollowersActivity;
import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.squareup.picasso.Picasso;

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
    APIService mAPIService;
    private ProgressDialog pdialog;
    Button btn_add_cause, btn_heart;

    List<UserDetailsResponse.AllCasesListBean> allCasesListBeen;
    AllCausesProfile allCausesProfile;
    List<AllCausesProfile> allCausesProfileList;
    List<AllCausesProfileWrapper> allCausesProfileWrappers;

    List<UserDetailsResponse.MyCasesBean> myCasesBeanList;
    MyCausesProfile myCausesProfile;
    List<MyCausesProfile> myCausesProfiles;
    List<MyCausesProfileWrapper> myCausesProfileWrappers;

    List<UserDetailsResponse.JoinedCasesBean> joinedCasesBeen;
    JoinedCasesProfile joinedCasesProfile;
    List<JoinedCasesProfile> joinedCasesProfiles;
    List<JoinedCausesProfileWrapper> joinedCausesProfileWrappers;

    TextView username_profile, text_reviews_profile, text_causes_profile, text_location_profile;
    de.hdodenhof.circleimageview.CircleImageView image_profile;

    String UserID, Name, Email, mNumber, Address, Gender, imageURL;

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

        allCausesProfileWrappers = new ArrayList<>();
        myCausesProfileWrappers = new ArrayList<>();
        joinedCausesProfileWrappers = new ArrayList<>();

        btn_add_cause = (Button) v.findViewById(R.id.btn_add_cause);
        btn_heart = (Button) v.findViewById(R.id.btn_heart);

        btn_add_cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddCauseActivity.class);
                startActivity(i);
            }
        });

        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListCategoriesActivity.class);
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
        text_reviews_profile = (TextView) v.findViewById(R.id.text_reviews_profile);
        text_causes_profile = (TextView) v.findViewById(R.id.text_causes_profile);
        text_location_profile = (TextView) v.findViewById(R.id.text_location_profile);
        image_profile = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.image_profile);

        //create tabs title
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("My Causes"));
        tabLayout.addTab(tabLayout.newTab().setText("Joined"));

        MyDetailsPost(UserID);

        text_reviews_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Reviews_Following_FollowersActivity.class);
                startActivity(intent);
            }
        });
        //handling tab click event
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new AllProfileFragment(allCausesProfileWrappers));
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new MycausesProfileFragment(myCausesProfileWrappers));
                } else {
                    replaceFragment(new JoinedcausesProfileFragment(joinedCausesProfileWrappers));
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

    public void MyDetailsPost(String UserId) {
        pdialog.show();
        mAPIService.UserDetails(UserId).enqueue(new Callback<UserDetailsResponse>() {

            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {

                        Name = response.body().getName();
                        Email = response.body().getEMail();
                        mNumber = response.body().getMobileNumber();
                        Address = response.body().getAddress();
                        Gender = response.body().getGender();

                        imageURL = response.body().getImgURL();
                        Picasso.with(getActivity()).load(imageURL).into(image_profile);
                        username_profile.setText(Name);
                        text_reviews_profile.setText(response.body().getReviewNumbers() + " Reviews");
                        text_location_profile.setText(String.valueOf(response.body().getAddress()));

                        UserDetailsResponse userDetailsResponse = response.body();

                        allCasesListBeen = new ArrayList<UserDetailsResponse.AllCasesListBean>();
                        myCasesBeanList = new ArrayList<UserDetailsResponse.MyCasesBean>();
                        joinedCasesBeen = new ArrayList<UserDetailsResponse.JoinedCasesBean>();

                        allCasesListBeen = userDetailsResponse.getAllCasesList();
                        myCasesBeanList = userDetailsResponse.getMyCases();
                        joinedCasesBeen = userDetailsResponse.getJoinedCases();
                        text_causes_profile.setText(myCasesBeanList.size() + " My Causes");

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserImage", imageURL);
                        editor.putString("UserName", Name);
                        editor.putString("Reviews", response.body().getReviewNumbers());
                        editor.putInt("Causes", myCasesBeanList.size());
                        editor.putString("Location", String.valueOf(response.body().getAddress()));
                        editor.apply();

                        allCausesProfileList = (new Select().from(AllCausesProfile.class).queryList());
                        if (allCausesProfileList.size() > 0) {
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
                            allCausesProfile = new AllCausesProfile();
                            if (allCasesListBeen.size() > 0) {

                                allCausesProfile.setCaseName(allCasesListBeen.get(i).getCaseName());
                                allCausesProfile.setCaseDescription(allCasesListBeen.get(i).getCaseDescription());
                                allCausesProfile.setJoined(allCasesListBeen.get(i).isIsJoined());
                                allCausesProfile.setOwner(allCasesListBeen.get(i).isIsOwner());
                                allCausesProfile.setAmount(allCasesListBeen.get(i).getAmount());
                                allCausesProfile.setCauseID(allCasesListBeen.get(i).getCauseID());
                                allCausesProfile.setEndDate(allCasesListBeen.get(i).getEndDate());
                                allCausesProfile.setIMG(allCasesListBeen.get(i).getIMG());
                                allCausesProfile.setNumberofjoins(allCasesListBeen.get(i).getNumberofjoins());
                                allCausesProfile.setStatus(allCasesListBeen.get(i).getStatus());

                                allCausesProfile.save();
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

                        allCausesProfileList = (new Select().from(AllCausesProfile.class).queryList());
                        myCausesProfiles = (new Select().from(MyCausesProfile.class).queryList());
                        joinedCasesProfiles = (new Select().from(JoinedCasesProfile.class).queryList());

                        //replace default fragment
                        for (int a = 0; a < allCausesProfileList.size(); a++) {
                            AllCausesProfileWrapper allCausesProfileWrapper = new AllCausesProfileWrapper(allCausesProfileList.get(a));
                            allCausesProfileWrappers.add(allCausesProfileWrapper);
                        }
                        replaceFragment(new AllProfileFragment(allCausesProfileWrappers));

                        for (int b = 0; b < myCausesProfiles.size(); b++) {
                            MyCausesProfileWrapper myCausesProfileWrapper = new MyCausesProfileWrapper(myCausesProfiles.get(b));
                            myCausesProfileWrappers.add(myCausesProfileWrapper);
                        }
                        for (int c = 0; c < joinedCasesProfiles.size(); c++) {
                            JoinedCausesProfileWrapper joinedCausesProfileWrapper = new JoinedCausesProfileWrapper(joinedCasesProfiles.get(c));
                            joinedCausesProfileWrappers.add(joinedCausesProfileWrapper);
                        }
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                String UserImage = sharedPreferences.getString("UserImage", "");
                String UserName = sharedPreferences.getString("UserName", "");
                String Reviews = sharedPreferences.getString("Reviews", "");
                int Causes = sharedPreferences.getInt("Causes", 0);
                String Location = sharedPreferences.getString("Location", "");

                Picasso.with(getActivity()).load(UserImage).into(image_profile);
                username_profile.setText(UserName);
                text_reviews_profile.setText(Reviews + " Reviews");
                text_location_profile.setText(Location);
                text_causes_profile.setText(Causes + " My Causes");


                allCausesProfileList = (new Select().from(AllCausesProfile.class).queryList());
                //replace default fragment
                for (int a = 0; a < allCausesProfileList.size(); a++) {
                    AllCausesProfileWrapper allCausesProfileWrapper = new AllCausesProfileWrapper(allCausesProfileList.get(a));
                    allCausesProfileWrappers.add(allCausesProfileWrapper);
                }

                replaceFragment(new AllProfileFragment(allCausesProfileWrappers));

                myCausesProfiles = (new Select().from(MyCausesProfile.class).queryList());
                joinedCasesProfiles = (new Select().from(JoinedCasesProfile.class).queryList());

                for (int b = 0; b < myCausesProfiles.size(); b++) {
                    MyCausesProfileWrapper myCausesProfileWrapper = new MyCausesProfileWrapper(myCausesProfiles.get(b));
                    myCausesProfileWrappers.add(myCausesProfileWrapper);
                }
                for (int c = 0; c < joinedCasesProfiles.size(); c++) {
                    JoinedCausesProfileWrapper joinedCausesProfileWrapper = new JoinedCausesProfileWrapper(joinedCasesProfiles.get(c));
                    joinedCausesProfileWrappers.add(joinedCausesProfileWrapper);
                }

                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                Bundle b = new Bundle();
                b.putString("imageURL", imageURL);
                b.putString("Name", Name);
                b.putString("Email", Email);
                b.putString("mNumber", mNumber);
                b.putString("Address", Address);
                b.putString("Gender", Gender);
                intent.putExtras(b);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UserID", "");
                editor.putString("UserImage", "");
                editor.putString("UserName", "");
                editor.putString("Reviews", "");
                editor.putInt("Causes", 0);
                editor.putString("Location", "");
                editor.putBoolean("isVerified", false);
                editor.putBoolean("facebookID", false);
                editor.apply();
                Delete.table(AllCausesProfile.class);
                Delete.table(MyCausesProfile.class);
                Delete.table(JoinedCasesProfile.class);
                Delete.table(NewsFeed.class);
                LoginManager.getInstance().logOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
