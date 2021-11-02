package com.example.tourguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.ViewHolder> {

    ArrayList<MostViewHelperClass> mostViewHelperClassArrayList;

    public MostViewAdapter(ArrayList<MostViewHelperClass> mostViewHelperClassArrayList) {
        this.mostViewHelperClassArrayList = mostViewHelperClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.most_viewed,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MostViewHelperClass mostViewHelperClass=mostViewHelperClassArrayList.get(position);
        holder.image.setImageResource(mostViewHelperClass.getImage());
        holder.title.setText(mostViewHelperClass.title);
        holder.desc.setText(mostViewHelperClass.desc);
    }

    @Override
    public int getItemCount() {
        return mostViewHelperClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.most_view_image);
            title=itemView.findViewById(R.id.most_view_title);
            desc=itemView.findViewById(R.id.most_view_desc);
        }
    }
}
