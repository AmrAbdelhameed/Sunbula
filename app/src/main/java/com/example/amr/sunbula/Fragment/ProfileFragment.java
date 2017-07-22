package com.example.amr.sunbula.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amr.sunbula.ProfilePagerAdapter;
import com.example.amr.sunbula.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_profile);
        toolbar.setTitle("View Profile");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.setTabTextColors(Color.BLACK, Color.rgb(255, 128, 0));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("My Causes"));
        tabLayout.addTab(tabLayout.newTab().setText("Joined Causes"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        final ProfilePagerAdapter adapter = new ProfilePagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
}
