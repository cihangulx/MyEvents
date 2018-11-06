package com.cihangul.myevents.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cihangul.myevents.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         *
         * simulate progress for 2 seconds
         *
         * if user logged in redirect to MainActivity else redirect to Login
         *
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAuth()) {
                    startActivity(new Intent(SplashScreen.this, Login.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }

    /**
     *
     * @return if user logged in return true else false
     */
    private boolean isAuth() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        return sharedPreferences.getString("user", null) != null;
    }

}
