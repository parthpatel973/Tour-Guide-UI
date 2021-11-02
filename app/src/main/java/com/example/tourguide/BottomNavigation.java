package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class BottomNavigation extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        chipNavigationBar=findViewById(R.id.bottom_nav);
        bottomNav();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,new DashboardFragment()).commit();
    }
    public void bottomNav(){

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i){
                    case R.id.nav_dashboard:
                        fragment=new DashboardFragment();
                        break;
                    case R.id.nav_profile:
                        fragment=new ProfileFragment();
                        break;
                    case R.id.nav_manage:
                        fragment=new ManageFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,fragment).commit();
            }
        });
    }
}