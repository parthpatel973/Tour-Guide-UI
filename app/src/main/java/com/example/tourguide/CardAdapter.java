package com.example.tourguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    ArrayList<CardHelperClass> helperClassArrayList;

    public CardAdapter(ArrayList<CardHelperClass> helperClassArrayList) {
        this.helperClassArrayList = helperClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        CardHelperClass cardHelperClass=helperClassArrayList.get(position);
        holder.image.setImageResource(cardHelperClass.getImages());
        holder.title.setText(cardHelperClass.getTitle());
        holder.desc.setText(cardHelperClass.getDesc());
    }

    @Override
    public int getItemCount() {
        return helperClassArrayList.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.card_images);
            title=itemView.findViewById(R.id.card_title);
            desc=itemView.findViewById(R.id.card_desc);
        }
    }

}





