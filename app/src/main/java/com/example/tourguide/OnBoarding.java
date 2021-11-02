package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    LinearLayout dotsIndicator;
    Button btn;
    Button next;
    TextView[] dots;
    int nextPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        //Hooks
         viewPager=findViewById(R.id.slider);
         dotsIndicator=findViewById(R.id.dotsIndicator);
        btn=findViewById(R.id.LetsGo);
        next=findViewById(R.id.next);

         //call adapter using constructor
        sliderAdapter =new  SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        setDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void next(View view) {
        viewPager.setCurrentItem(nextPosition+1);
    }

    private  void setDots(int position) {
        dots = new TextView[4];
        dotsIndicator.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(50);
            dotsIndicator.addView(dots[i]);
            dots[i].setTextColor(getResources().getColor(R.color.purple_200));
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.purple_700));
        }
    }
    ViewPager.OnPageChangeListener changeListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDots(position);
            nextPosition=position;
            if(position==0){
                btn.setVisibility(View.INVISIBLE);
            }else if (position==1){
                btn.setVisibility(View.INVISIBLE);
            }else if (position==2){
                btn.setVisibility(View.INVISIBLE);
            }
            else{
                btn.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public void LetsStart(View view) {
        Intent intent=new Intent(OnBoarding.this,Dashboard.class);
        startActivity(intent);
        finish();
    }
}