package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView back_image;
    TextView create_acc_text;
    Button next_btn;
    Button login_btn;


    TextInputLayout fullName_text, username_text, email_text, password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back_image = findViewById(R.id.back_btn);
        create_acc_text = findViewById(R.id.create_acc);
        next_btn = findViewById(R.id.next);
        login_btn = findViewById(R.id.login);

        fullName_text = findViewById(R.id.fullname);
        username_text = findViewById(R.id.username);
        email_text = findViewById(R.id.email);
        password_text = findViewById(R.id.password);

        nextScreen();
        setLogin_btn();
        setBack_btn();

    }


    private void nextScreen() {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!FullNameValidation() | !usernameValidation() | !EmailValidation() | !PasswordValidation()) {
                    return;
                }

                String fullName=fullName_text.getEditText().getText().toString();
                String userName=username_text.getEditText().getText().toString();
                String email=email_text.getEditText().getText().toString();
                String password=password_text.getEditText().getText().toString();

                Intent intent = new Intent(getApplicationContext(), SignUp_Screen2.class);

                intent.putExtra("fullName",fullName);
                intent.putExtra("userName",userName);
                intent.putExtra("email",email);
                intent.putExtra("password",password);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(back_image, "transition_back");
                pairs[1] = new Pair<View, String>(create_acc_text, "transition_create_acc");
                pairs[2] = new Pair<View, String>(next_btn, "transition_next");
                pairs[3] = new Pair<View, String>(login_btn, "transition_login");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });

    }

    private boolean FullNameValidation() {
        String val = fullName_text.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName_text.setError("Field can not be empty");
            return false;
        } else {
            fullName_text.setError(null);
            fullName_text.setErrorEnabled(false);
            return true;
        }
    }

    private boolean usernameValidation() {
        String val = username_text.getEditText().getText().toString().trim();
        String checkSpace = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username_text.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username_text.setError("Username is too long!");
            return false;
        } else if (!val.matches(checkSpace)) {
            username_text.setError("No space are allowed!");
            return false;
        } else {
            username_text.setError(null);
            username_text.setErrorEnabled(false);
            return true;
        }
    }

    private boolean EmailValidation() {

        String val = email_text.getEditText().getText().toString().trim();
        String checkEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";

        if (val.isEmpty()) {
            email_text.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email_text.setError("Email-id is not valid!");
            return false;
        } else {
            email_text.setError(null);
            email_text.setErrorEnabled(false);
            return true;
        }
    }

    private boolean PasswordValidation() {
        String val = password_text.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=_])" +
                "(?=\\S+$)" + ".{4,}" +
                "$";

        if (val.isEmpty()) {
            password_text.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password_text.setError("password too weak");
            return false;
        } else {
            password_text.setError(null);
            password_text.setErrorEnabled(false);
            return true;
        }
    }

    public void setLogin_btn(){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }


    private void setBack_btn() {

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterScreen.class));
            }
        });
    }




}