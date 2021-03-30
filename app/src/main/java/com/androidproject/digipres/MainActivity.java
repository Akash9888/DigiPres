package com.androidproject.digipres;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private  static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                int userID = sessionManagement.getSession();

                if(userID != -1){
                    Intent intent = new Intent (MainActivity.this,DeciderActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent (MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);
    }
}