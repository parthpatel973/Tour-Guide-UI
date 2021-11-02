package com.example.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

public class AllCategory extends AppCompatActivity {

    RecyclerView AllCategoryRecyclerView;
    RecyclerView.Adapter adapter;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        AllCategoryRecyclerView=findViewById(R.id.recycler_all_category);
        back=findViewById(R.id.back_btn);
        allCategoryRecycler();
        GoBack();
    }

    private void GoBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategory.super.onBackPressed();
            }
        });
    }

    private void allCategoryRecycler(){
        AllCategoryRecyclerView.setHasFixedSize(true);
        AllCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        ArrayList<AllCategoryHelperClass> addCategory=new ArrayList<>();
        addCategory.add(new AllCategoryHelperClass(R.drawable.doctor_img,"Hospital"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.library_icon,"Library"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.water_park,"Water Park"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.gym,"Gym"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.resorts,"Resorts"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.transportation_image,"Transport"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.movie_theatre,"Movie Theatre"));
        addCategory.add(new AllCategoryHelperClass(R.drawable.shopping_image,"Shops"));

        adapter=new AllCategoryAdapter(addCategory);
        AllCategoryRecyclerView.setAdapter(adapter);
    }
}