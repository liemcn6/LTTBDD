package com.example.baeminfake.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baeminfake.R;
import com.example.baeminfake.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartsViewHolder> {

    private List<Cart> list;
    public static int[] chooseid = new int[100];

    public CartRecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setCarts(List<Cart> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartsViewHolder holder, int position) {
        Cart o = list.get(position);
        if (o.getPayment() == 1) {
            holder.checkpay.setChecked(true);
        } else {
            holder.checkpay.setChecked(false);
        }
        holder.txtsoluong.setText(o.getSoluong() + "");
        holder.txtName.setText(o.getName());
        holder.txtPrice.setText("" + o.getPrice());
        holder.txtRestaurant.setText(o.getRestaurant());
        holder.rating.setRating(o.getRate());
        holder.txtOrder.setText("(" + o.getOrders() + "+)");
        switch (o.getCategory()) {
            case 0: {
                holder.imgItem.setImageResource(R.drawable.trasua);
                break;
            }
            case 1: {
                holder.imgItem.setImageResource(R.drawable.anvat);
                break;
            }
            case 2: {
                holder.imgItem.setImageResource(R.drawable.banhmi);
                break;
            }
            case 3: {
                holder.imgItem.setImageResource(R.drawable.monchinh);
                break;
            }
            case 4: {
                holder.imgItem.setImageResource(R.drawable.pizza);
                break;
            }
            case 5: {
                holder.imgItem.setImageResource(R.drawable.beefsteak);
                break;
            }
            case 6: {
                holder.imgItem.setImageResource(R.drawable.sandwich);
                break;
            }
            case 7: {
                holder.imgItem.setImageResource(R.drawable.dohop);
                break;
            }
            case 8: {
                holder.imgItem.setImageResource(R.drawable.comsuat);
                break;
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkpay.isChecked() == true) {
                    holder.checkpay.setChecked(false);
                    chooseid[o.getId()] = 0;
                } else {
                    holder.checkpay.setChecked(true);
                    chooseid[o.getId()] = 1;
                }
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

    class CartsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtOrder, txtPrice, txtRestaurant, txtsoluong, txtDate;
        private ImageView imgItem;
        private RatingBar rating;
        private CheckBox checkpay;

        public CartsViewHolder(@NonNull View v) {
            super(v);
            txtsoluong = v.findViewById(R.id.soluong_pay);
            checkpay = v.findViewById(R.id.check_pay);
            txtName = v.findViewById(R.id.cart_name);
            txtPrice = v.findViewById(R.id.cart_price);
            txtRestaurant = v.findViewById(R.id.cart_restaurant);
            imgItem = v.findViewById(R.id.cart_img);
            rating = v.findViewById(R.id.cart_rate);
            txtOrder = v.findViewById(R.id.cart_order);
            txtDate = v.findViewById(R.id.cart_date);
        }
    }
}
