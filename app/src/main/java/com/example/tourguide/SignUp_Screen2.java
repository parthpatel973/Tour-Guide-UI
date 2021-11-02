package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp_Screen2 extends AppCompatActivity {

    ImageView back_image;
    TextView create_acc_text;
    Button next_btn;
    Button login_btn;

    RadioGroup radioGroup;
    RadioButton radioButton;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__screen2);

        back_image = findViewById(R.id.back_btn);
        create_acc_text = findViewById(R.id.create_acc);
        next_btn = findViewById(R.id.next);
        login_btn = findViewById(R.id.login);

        radioGroup = findViewById(R.id.radioGroup_gender);
        radioButton = findViewById(R.id.select_gender);
        datePicker = findViewById(R.id.date_picker);

        next();
    }

    public void next() {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GenderValidation() | !AgeValidation()) {
                    return;
                }

                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String gender = radioButton.getText().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String date = day + "/" + month + "/" + year;

                String fullName = getIntent().getStringExtra("fullName");
                String userName=getIntent().getStringExtra("userName");
                String email=getIntent().getStringExtra("email");
                String password=getIntent().getStringExtra("password");


                Intent intent = new Intent(getApplicationContext(), SignUp_Screen3.class);

                intent.putExtra("fullName",fullName);
                intent.putExtra("userName",userName);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("gender", gender);
                intent.putExtra("date", date);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(back_image, "transition_back");
                pairs[1] = new Pair<View, String>(create_acc_text, "transition_create_acc");
                pairs[2] = new Pair<View, String>(next_btn, "transition_next");
                pairs[3] = new Pair<View, String>(login_btn, "transition_login");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp_Screen2.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private boolean GenderValidation() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select your Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean AgeValidation() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "you are not eligible", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}