package com.example.amr.sunbula.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.amr.sunbula.Fragment.AllProfileFragment;
import com.example.amr.sunbula.Fragment.JoinedcausesProfileFragment;
import com.example.amr.sunbula.Fragment.MycausesProfileFragment;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ProfilePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllProfileFragment tab1 = new AllProfileFragment();
                return tab1;
            case 1:
                MycausesProfileFragment tab2 = new MycausesProfileFragment();
                return tab2;
            case 2:
                JoinedcausesProfileFragment tab3 = new JoinedcausesProfileFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}