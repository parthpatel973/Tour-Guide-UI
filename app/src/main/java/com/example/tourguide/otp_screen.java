package com.example.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class otp_screen extends AppCompatActivity {

    Button verify_btn;
    PinView pinView_otp;
    String otpId;
    String phoneNo, fullName, userName, email, password, gender, age, forget_otp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);

        verify_btn = findViewById(R.id.verify);
        pinView_otp = findViewById(R.id.OTP_Num);
        mAuth = FirebaseAuth.getInstance();


        fullName = getIntent().getStringExtra("fullName");
        userName = getIntent().getStringExtra("userName");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        gender = getIntent().getStringExtra("gender");
        age = getIntent().getStringExtra("age");
        phoneNo = getIntent().getStringExtra("phoneNo");
        forget_otp = getIntent().getStringExtra("forget_otp");


        sentOtp(phoneNo);
        setVerify_btn();

    }

    private void sentOtp(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            otpId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pinView_otp.setText(code);
                VerifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_screen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void VerifyCode(String code) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otpId, code);
        signInWithPhoneAuthCredential(phoneAuthCredential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (forget_otp.equals("updateData")) {
                                updateOldUserData();
                            } else {
                                storeNewUserData();
                            }
//                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                            startActivity(intent);
//                            finish();
                        } else {
                            Toast.makeText(otp_screen.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateOldUserData() {
        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        intent.putExtra("phoneNo", phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference("Users");


        StoreDataHelperClass storeDataHelperClass = new StoreDataHelperClass(phoneNo, fullName, userName, email, password, gender, age);
        root.child(phoneNo).setValue(storeDataHelperClass);

    }

    public void setVerify_btn() {
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pinView_otp.getText().toString();
                if (!code.isEmpty()) {
                    VerifyCode(code);
                }
            }
        });
    }
}