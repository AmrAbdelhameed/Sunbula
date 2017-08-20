package com.example.amr.sunbula.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amr.sunbula.R;

public class SplashActivity extends AppCompatActivity {

    TextView t_splash;
    ImageView i_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        i_splash = (ImageView) findViewById(R.id.image_splash);
        t_splash = (TextView) findViewById(R.id.text_splash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        t_splash.startAnimation(animation);
        i_splash.startAnimation(animation);

        final Intent i = new Intent(this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
