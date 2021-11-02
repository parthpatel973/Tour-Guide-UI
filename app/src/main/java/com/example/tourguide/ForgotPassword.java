package com.example.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgotPassword extends AppCompatActivity {

    Button next_btn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phone_text;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        next_btn = findViewById(R.id.next);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        phone_text = findViewById(R.id.phoneNumber);
        progressBar = findViewById(R.id.progress_bar);

        setNext_btn();
        progressBar.setVisibility(View.GONE);
    }

    public void setNext_btn() {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConnected(ForgotPassword.this)) {
                    showAlertDialog();
                }

                if (!phoneNumberValidation()) {
                    return;
                }
                String phoneNo = phone_text.getEditText().getText().toString().trim();

                if (phoneNo.charAt(0) == '0') {
                    phoneNo.substring(1);
                }

                final String fullPhoneNumber = "+" + countryCodePicker.getFullNumber() + phoneNo;
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(fullPhoneNumber);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            phone_text.setError(null);
                            phone_text.setErrorEnabled(false);

                            Intent intent = new Intent(getApplicationContext(), otp_screen.class);
                            intent.putExtra("phoneNo", fullPhoneNumber);
                            intent.putExtra("forget_otp","updateData");
                            startActivity(intent);
                            finish();

                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            phone_text.setError("No Such User exist!");
                            phone_text.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ForgotPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                phoneNumberValidation();
            }
        });
    }


    private boolean isConnected(ForgotPassword forgotPassword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) forgotPassword.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
        builder.setMessage("please connect to the internet")
                .setTitle("No internet")
                .setCancelable(false)
                .setPositiveButton("connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean phoneNumberValidation() {
        String val = phone_text.getEditText().getText().toString().trim();
        String checkPhoneNumber = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            phone_text.setError("please provide PhoneNumber");
            return false;
        } else if (!val.matches(checkPhoneNumber)) {
            phone_text.setError("Invalid PhoneNumber");
            return false;
        } else {
            phone_text.setError(null);
            phone_text.setErrorEnabled(false);
            return true;
        }
    }
}