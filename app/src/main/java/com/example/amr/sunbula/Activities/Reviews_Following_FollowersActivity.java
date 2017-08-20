package com.example.amr.sunbula.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amr.sunbula.Fragment.AllProfileFragment;
import com.example.amr.sunbula.Fragment.FollowersFragment;
import com.example.amr.sunbula.Fragment.FollowingFragment;
import com.example.amr.sunbula.Fragment.JoinedcausesProfileFragment;
import com.example.amr.sunbula.Fragment.MycausesProfileFragment;
import com.example.amr.sunbula.Fragment.ReviewsFragment;
import com.example.amr.sunbula.R;

public class Reviews_Following_FollowersActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews__following__followers);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        container = (LinearLayout) findViewById(R.id.fragment_container);

        //create tabs title
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("Followers"));

        replaceFragment(new ReviewsFragment());
        //handling tab click event
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new ReviewsFragment());
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new FollowingFragment());
                } else {
                    replaceFragment(new FollowersFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }
}
