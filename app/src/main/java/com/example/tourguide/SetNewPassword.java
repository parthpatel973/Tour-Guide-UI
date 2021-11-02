package com.example.tourguide;

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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    Button update_btn;
    TextInputLayout newPassword;
    TextInputLayout confirmPassword;
    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        update_btn = findViewById(R.id.update);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        setUpdate_btn();
    }

    public void setUpdate_btn() {
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConnected(SetNewPassword.this)) {
                    showAlertDialog();
                }
                if (!newPasswordValidation() | !confirmPassword()) {
                    return;
                }


                String newPasswords=newPassword.getEditText().getText().toString().trim();
                phoneNo = getIntent().getStringExtra("phoneNo");

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(phoneNo).child("password").setValue(newPasswords);
                

                startActivity(new Intent(getApplicationContext(),Password_Updated.class));
                finish();
            }
        });
    }


    private boolean isConnected(SetNewPassword setNewPassword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) setNewPassword.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please Connect to the Internet")
                .setTitle("No Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), SetNewPassword.class));
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean newPasswordValidation() {
        String val = newPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=_])" +
                "(?=\\S+$)" + ".{4,}" +
                "$";
        if (val.isEmpty()) {
            newPassword.setError("Password is empty");
            return false;
        }else if (!val.equals(confirmPassword.getEditText().getText().toString().trim())){
            newPassword.setError("password is not match");
            return  false;
        }
        else if (!val.matches(checkPassword)) {
            newPassword.setError("password is not strong");
            return false;
        } else {
            newPassword.setError(null);
            newPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean confirmPassword() {
        String val = confirmPassword.getEditText().getText().toString().trim();
        String  checkConfirmPassword="^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=_])" +
                "(?=\\S+$)" + ".{4,}" +
                "$";

        if (val.isEmpty()) {
            confirmPassword.setError("Password is empty");
            return false;
        } else if (!val.equals(newPassword.getEditText().getText().toString().trim())) {
            confirmPassword.setError("password is not match");
            return false;
        }else if(!val.matches(checkConfirmPassword)){
            confirmPassword.setError("password is not strong");
            return  false;
        }
        else {
            confirmPassword.setError(null);
            confirmPassword.setErrorEnabled(false);
            return true;
        }
    }
}