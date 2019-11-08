package com.example.com.leapmenaassignment.View;

import android.content.Intent;
import android.os.Bundle;

import com.example.com.leapmenaassignment.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    /**
     * Duration of wait *
     */
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        startSplashTimer();
    }

    public void startSplashTimer() {

        /**
         * thread for displaying the SplashScreen
         */
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_DISPLAY_LENGTH);
                    startHomeActivity();
                } catch (InterruptedException e) {

                } finally {

                }
            }

        };
        splashThread.start();
    }

    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
