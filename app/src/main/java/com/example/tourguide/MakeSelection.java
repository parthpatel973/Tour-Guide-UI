package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MakeSelection extends AppCompatActivity {

    Button sms;
    Button mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);

        sms = findViewById(R.id.via_sms);
        mail = findViewById(R.id.via_mail);
        setSms();
        setMail();
    }

    public void setSms() {
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), otp_screen.class);
                startActivity(intent);
            }
        });
    }

    public void setMail() {
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), otp_screen.class);
                startActivity(intent);
            }
        });
    }
}