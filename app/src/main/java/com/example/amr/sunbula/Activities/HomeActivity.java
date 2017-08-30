package com.example.amr.sunbula.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amr.sunbula.BottomNavigationClasses.BottomNavigationViewNew;
import com.example.amr.sunbula.Fragment.NotificationsFragment;
import com.example.amr.sunbula.Fragment.HomeFragment;
import com.example.amr.sunbula.Fragment.ProfileFragment;
import com.example.amr.sunbula.Fragment.MessagesFragment;
import com.example.amr.sunbula.R;
import com.google.firebase.crash.FirebaseCrash;

public class HomeActivity extends AppCompatActivity {

    int x = R.id.navigation_home;
    Toolbar toolbar;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    boolean homeChecked = false, profileChecked = false, messagesChecked = false, notificationChecked = false;

    private BottomNavigationViewNew.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewNew.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            x = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!homeChecked) {
                        fragmentManager.beginTransaction().replace(R.id.content, new HomeFragment()).commit();
                        toolbar.setTitle("News Feed");
                        setSupportActionBar(toolbar);
                        homeChecked = true;
                        profileChecked = false;
                        messagesChecked = false;
                        notificationChecked = false;
                    }
                    return true;
                case R.id.navigation_profile:
                    if (!profileChecked) {
                        fragmentManager.beginTransaction().replace(R.id.content, new ProfileFragment()).commit();
                        toolbar.setTitle("View Profile");
                        setSupportActionBar(toolbar);
                        profileChecked = true;
                        homeChecked = false;
                        messagesChecked = false;
                        notificationChecked = false;
                    }
                    return true;
                case R.id.navigation_message:
                    if (!messagesChecked) {
                        fragmentManager.beginTransaction().replace(R.id.content, new MessagesFragment()).commit();
                        toolbar.setTitle("Messages");
                        setSupportActionBar(toolbar);
                        messagesChecked = true;
                        homeChecked = false;
                        profileChecked = false;
                        notificationChecked = false;
                    }
                    return true;
                case R.id.navigation_notification:
                    if (!notificationChecked) {
                        fragmentManager.beginTransaction().replace(R.id.content, new NotificationsFragment()).commit();
                        toolbar.setTitle("Notification");
                        setSupportActionBar(toolbar);
                        notificationChecked = true;
                        homeChecked = false;
                        profileChecked = false;
                        messagesChecked = false;
                    }
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

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        if (savedInstanceState != null) {
            x = savedInstanceState.getInt(KEY_POSITION_FRAGMENT);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle("News Feed");
        setSupportActionBar(toolbar);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        BottomNavigationViewNew navigation = (BottomNavigationViewNew) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (b != null) {
            boolean GoToProfile = b.getBoolean("GoToProfile");
            if (GoToProfile)
                navigation.setSelectedItemId(R.id.navigation_profile);
            else {
                navigation.setSelectedItemId(R.id.navigation_notification);
                String body = b.getString("NotificationMessage");
                Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
            }
        } else
            navigation.setSelectedItemId(x);
    }
}
