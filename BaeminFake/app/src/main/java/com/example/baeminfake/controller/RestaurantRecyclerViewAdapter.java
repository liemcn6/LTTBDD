package com.example.baeminfake.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baeminfake.R;
import com.example.baeminfake.activity.GetMoreActivity;
import com.example.baeminfake.activity.OrderActivity;
import com.example.baeminfake.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantViewHolder> {

    private List<Restaurant> list;
    private Activity activity;

    public RestaurantRecyclerViewAdapter(Activity activity) {
        list = new ArrayList<>();
        this.activity = activity;
    }

    public void setRestaurant(List<Restaurant> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RestaurantRecyclerViewAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantRecyclerViewAdapter.RestaurantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantRecyclerViewAdapter.RestaurantViewHolder holder, int position) {
        Restaurant o = list.get(position);
        holder.txtName.setText(o.getName());
        holder.txtDetails.setText(o.getDetails());
        if (o.getFavorite() == 1) {
            holder.imgLove.setImageResource(R.drawable.ic_star);
        } else {
            holder.imgLove.setImageResource(R.drawable.ic_heart);
        }
        holder.imgItem.setImageResource(R.drawable.restaurant);

        Activity activity = this.activity;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GetMoreActivity.class);
                intent.putExtra("restaurant_name", o.getName());
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtDetails;
        private ImageButton imgLove;
        private ImageView imgItem;

        public RestaurantViewHolder(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.restaurant_name);
            imgItem = v.findViewById(R.id.restaurant_img);
            txtDetails = v.findViewById(R.id.restaurant_details);
            imgLove = v.findViewById(R.id.restaurant_love);

//            imgLove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }
}
