package com.example.tourguide;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.ViewHolder> {
    ArrayList<AllCategoryHelperClass> allCategoryHelperClassArrayList;

    public AllCategoryAdapter(ArrayList<AllCategoryHelperClass> allCategoryHelperClassArrayList) {
        this.allCategoryHelperClassArrayList = allCategoryHelperClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.all_category_card, parent, false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCategoryHelperClass allCategoryHelperClass=allCategoryHelperClassArrayList.get(position);
        holder.title.setText(allCategoryHelperClass.getTitle());
        holder.image.setImageResource(allCategoryHelperClass.getImage());
        if (position==0){
            holder.itemView.setBackgroundResource(R.drawable.hospital);
        } else if(position==1){
            holder.itemView.setBackgroundResource(R.drawable.library);
        }else if (position==2){
            holder.itemView.setBackgroundResource(R.drawable.water_park_card);
        }else if (position==3){
            holder.itemView.setBackgroundResource(R.drawable.gym_card);
        }else if (position==4){
            holder.itemView.setBackgroundResource(R.drawable.resorts_card);
        }else if (position==5){
            holder.itemView.setBackgroundResource(R.drawable.trasnport_card);
        }else if (position==6){
            holder.itemView.setBackgroundResource(R.drawable.movie_card);
        }else if (position==7){
            holder.itemView.setBackgroundResource(R.drawable.shops_card);
        }
    }

    @Override
    public int getItemCount() {
        return  allCategoryHelperClassArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.all_category_card);
            title = itemView.findViewById(R.id.all_category_title);
        }
    }
}