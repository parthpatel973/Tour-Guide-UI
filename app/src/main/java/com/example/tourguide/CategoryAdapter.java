package com.example.tourguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryHelperClass> categoryHelperClassArrayList;

    public CategoryAdapter(ArrayList<CategoryHelperClass> categoryHelperClassArrayList) {
        this.categoryHelperClassArrayList = categoryHelperClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.category_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryHelperClass categoryHelperClass=categoryHelperClassArrayList.get(position);
        holder.image.setImageResource(categoryHelperClass.getImage());
        holder.title.setText(categoryHelperClass.getTitle());

        if(position==0){
            holder.itemView.setBackgroundResource(R.color.restaurant);
        }else  if (position==1){
            holder.itemView.setBackgroundResource(R.color.shops);
        }else  if (position==2){
            holder.itemView.setBackgroundResource(R.color.movie);
        }else{
            holder.itemView.setBackgroundResource(R.color.transport);
        }
    }

    @Override
    public int getItemCount() {
        return categoryHelperClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.restaurant);
            image=itemView.findViewById(R.id.restaurant_image);


        }
    }
}
