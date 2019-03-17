package com.example.app.ksugym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LogoPage extends AppCompatActivity {

    private int TIME_OUT = 3000; //Time to launch the another activity (4 seconds)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                    Intent i = new Intent(LogoPage.this, WelcomePage.class);
                    startActivity(i);
                    finish();

            }
        }, TIME_OUT);
    }
}
