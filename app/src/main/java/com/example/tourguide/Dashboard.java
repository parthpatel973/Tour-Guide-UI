package com.example.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    RecyclerView CardRecyclerView, MostViewRecyclerView, CategoryRecyclerView;
    RecyclerView.Adapter adapter;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView menu_image;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        CardRecyclerView = findViewById(R.id.recycler_items);
        MostViewRecyclerView = findViewById(R.id.recycler_most_views);
        CategoryRecyclerView = findViewById(R.id.recycler_Category);
        login = findViewById(R.id.login);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        menu_image = findViewById(R.id.menu_icon);


        navigationView();
        recyclerView();
        MostViewRecyclerView();
        CategoryRecyclerView();
        login();
    }

    private void login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }

    private void navigationView() {
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_category:
                        Intent intent = new Intent(getApplicationContext(), AllCategory.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void recyclerView() {
        CardRecyclerView.setHasFixedSize(true);
        CardRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CardHelperClass> cardAdd = new ArrayList<>();

        cardAdd.add(new CardHelperClass(R.drawable.starbucks, "Starbucks", "Starbucks Coffee are the most popular coffee chains in the world."));
        cardAdd.add(new CardHelperClass(R.drawable.subway, "Subway", "It is mainly known for its sandwiches and salads."));
        cardAdd.add(new CardHelperClass(R.drawable.mcdonald, "Mcdonald's", "It is mainly known for its Burgers and French Fries."));

        adapter = new CardAdapter(cardAdd);
        CardRecyclerView.setAdapter(adapter);
    }

    private void MostViewRecyclerView() {
        MostViewRecyclerView.setHasFixedSize(true);
        MostViewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewHelperClass> ViewAdd = new ArrayList<>();

        ViewAdd.add(new MostViewHelperClass(R.drawable.starbucks, "Starbucks", "Starbucks Coffee are the most popular coffee chains in the world."));
        ViewAdd.add(new MostViewHelperClass(R.drawable.subway, "Subway", "It is mainly known for its sandwiches and salads."));
        ViewAdd.add(new MostViewHelperClass(R.drawable.mcdonald, "Mcdonald's", "It is mainly known for its Burgers and French Fries."));

        adapter = new MostViewAdapter(ViewAdd);
        MostViewRecyclerView.setAdapter(adapter);
    }

    private void CategoryRecyclerView() {
        CategoryRecyclerView.setHasFixedSize(true);
        CategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoryHelperClass> categoryAdd = new ArrayList<>();

        categoryAdd.add(new CategoryHelperClass(R.drawable.restaurant_image, "Restaurant"));
        categoryAdd.add(new CategoryHelperClass(R.drawable.shopping_image, "Shopping"));
        categoryAdd.add(new CategoryHelperClass(R.drawable.movie_theatre, "Movie Theatre"));
        categoryAdd.add(new CategoryHelperClass(R.drawable.transportation_image, "Transport"));

        adapter = new CategoryAdapter(categoryAdd);
        CategoryRecyclerView.setAdapter(adapter);
    }
}