package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp_Screen3 extends AppCompatActivity {

    Button next_btn;
    TextInputLayout phoneNumber_Text;
    CountryCodePicker countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__screen3);

        next_btn = findViewById(R.id.next);
        phoneNumber_Text = findViewById(R.id.phoneNumber);
        countryCode=findViewById(R.id.countryCodePicker);

        next();
    }

    private void next() {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phoneNumberValidation()) {
                    return;
                }

                String fullName=getIntent().getStringExtra("fullName");
                String userName=getIntent().getStringExtra("userName");
                String email=getIntent().getStringExtra("email");
                String password=getIntent().getStringExtra("password");
                String gender=getIntent().getStringExtra("gender");
                String age=getIntent().getStringExtra("age");

                String phoneNumber=phoneNumber_Text.getEditText().getText().toString().trim();
                String phoneNo="+"+countryCode.getFullNumber()+phoneNumber;

                Intent intent = new Intent(getApplicationContext(), otp_screen.class);
                
                intent.putExtra("fullName",fullName);
                intent.putExtra("userName",userName);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("gender",gender);
                intent.putExtra("age",age);
                intent.putExtra("phoneNo",phoneNo);

                startActivity(intent);
            }
        });
    }

    private boolean phoneNumberValidation() {

        String val = phoneNumber_Text.getEditText().getText().toString().trim();
        String checkNumber = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            phoneNumber_Text.setError("Please provide phoneNumber");
            return false;
        } else if (!val.matches(checkNumber)) {
            phoneNumber_Text.setError("Invalid PhoneNumber");
            return false;
        } else {
            phoneNumber_Text.setError(null);
            phoneNumber_Text.setErrorEnabled(false);
            return true;
        }
    }
}