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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phone_text;
    TextInputLayout password_text;
    Button forgot_btn;
    Button login_btn;
    Button createAcc;
    ImageView back;
    ProgressBar progressBar;
    CheckBox remember_me;
    TextInputEditText phone_editText;
    TextInputEditText password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgot_btn = findViewById(R.id.forgot_pass);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        phone_text = findViewById(R.id.phoneNumber);
        password_text = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);
        back=findViewById(R.id.back_btn);
        createAcc=findViewById(R.id.createAccount);
        progressBar = findViewById(R.id.progress_bar);
        remember_me=findViewById(R.id.remember_me);
        phone_editText=findViewById(R.id.phoneNumberEditText);
        password_editText=findViewById(R.id.passwordEditText);

        setForgot_btn();
        setLogin_btn();
        setBack_btn();
        setCreateAcc();

        progressBar.setVisibility(View.GONE);

        SessionManager sessionManager=new SessionManager(login.this,SessionManager.remember_me);
        if (sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails=sessionManager.getRememberDetails();
            phone_editText.setText(rememberMeDetails.get(SessionManager.key_phoneNo_remember));
            password_editText.setText(rememberMeDetails.get(SessionManager.key_password_remember));
        }
    }


    public void setForgot_btn() {
        forgot_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(intent);
        });
    }

    public void setLogin_btn() {
        login_btn.setOnClickListener(v -> {

            if (!isConnected(login.this)) {
                showCustomDialog();
            }

            if (!passwordValidation() | !phoneNumberValidation()) {
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            String phoneNumber = phone_text.getEditText().getText().toString().trim();
            String password = password_text.getEditText().getText().toString().trim();
            final String fullPhoneNumber = "+" + countryCodePicker.getFullNumber() + phoneNumber;

            if (phoneNumber.charAt(0) == '0') {
                phoneNumber.substring(1);
            }

            if (remember_me.isChecked()){
                SessionManager sessionManager=new SessionManager(this,SessionManager.remember_me);
                sessionManager.rememberMeSession(phoneNumber,password);
            }
            Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(fullPhoneNumber);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        phone_text.setError(null);
                        phone_text.setErrorEnabled(false);

                        String userPassword = snapshot.child(fullPhoneNumber).child("password").getValue(String.class);
                        if (userPassword.equals(password)) {
                            password_text.setError(null);
                            password_text.setErrorEnabled(false);

                            String fullName = snapshot.child(fullPhoneNumber).child("fullName").getValue(String.class);
                            String UserName = snapshot.child(fullPhoneNumber).child("userName").getValue(String.class);
                            String email = snapshot.child(fullPhoneNumber).child("email").getValue(String.class);
                            String phone = snapshot.child(fullPhoneNumber).child("phoneNo").getValue(String.class);
                            String password=snapshot.child(fullPhoneNumber).child("password").getValue(String.class);

                            SessionManager sessionManager=new SessionManager(login.this,SessionManager.userSession);
                            sessionManager.creatingLoginSession(fullName,UserName,email,phone,password);

                            startActivity(new Intent(getApplicationContext(),BottomNavigation.class));

                            Toast.makeText(login.this, fullName + "\n" + UserName + "\n" + email + "\n" + phone, Toast.LENGTH_SHORT).show();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(login.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(login.this, "No Such User exist!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            phoneNumberValidation();
            passwordValidation();
            setCreateAcc();
        });
    }

    private boolean isConnected(login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(login.this);
        builder.setMessage("Please Connect to the Internet")
               .setTitle("No Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(),login.class));
                            finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean passwordValidation() {

        String val = password_text.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=_])" +
                "(?=\\S+$)" + ".{4,}" +
                "$";
        if (val.isEmpty()) {
            password_text.setError("password is empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password_text.setError("please provide valid password");
            return false;
        } else {
            password_text.setError(null);
            password_text.setErrorEnabled(false);
            return true;
        }
    }

    private boolean phoneNumberValidation() {

        String val = phone_text.getEditText().getText().toString().trim();
        String checkNumber = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            phone_text.setError("please provide PhoneNumber");
            return false;
        } else if (!val.matches(checkNumber)) {
            phone_text.setError("InValid PhoneNumber");
            return false;
        } else {
            phone_text.setError(null);
            phone_text.setErrorEnabled(false);
            return true;
        }
    }
    public void setCreateAcc(){
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
    }

    private void setBack_btn() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterScreen.class));
            }
        });
    }
}