package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class GuideActivity extends AppCompatActivity {

    Timer timer;
    SharedPreferences OnBoarding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                OnBoarding=getSharedPreferences("OnBoarding",MODE_PRIVATE);
                Boolean CheckFirstTime=OnBoarding.getBoolean("firstTime",true);

                if(CheckFirstTime){
                    SharedPreferences.Editor editor=OnBoarding.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent=new Intent(getApplicationContext(),OnBoarding.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        },5000);
    }
}