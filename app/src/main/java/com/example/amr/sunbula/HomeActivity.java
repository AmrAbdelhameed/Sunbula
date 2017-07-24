package com.example.amr.sunbula;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amr.sunbula.BottomNavigationClasses.BottomNavigationViewNew;
import com.example.amr.sunbula.Fragment.NotificationsFragment;
import com.example.amr.sunbula.Fragment.HomeFragment;
import com.example.amr.sunbula.Fragment.ProfileFragment;
import com.example.amr.sunbula.Fragment.MessagesFragment;

public class HomeActivity extends AppCompatActivity {

    int x = R.id.navigation_home;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationViewNew.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewNew.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            x = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.content, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentManager.beginTransaction().replace(R.id.content, new ProfileFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentManager.beginTransaction().replace(R.id.content, new MessagesFragment()).commit();
                    return true;
                case R.id.navigation_dashboard1:
                    fragmentManager.beginTransaction().replace(R.id.content, new NotificationsFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    private static final String KEY_POSITION_FRAGMENT = "POSITION_FRAGMENT";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION_FRAGMENT, x);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            x = savedInstanceState.getInt(KEY_POSITION_FRAGMENT);
        }
        BottomNavigationViewNew navigation = (BottomNavigationViewNew) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(x);
    }

}
