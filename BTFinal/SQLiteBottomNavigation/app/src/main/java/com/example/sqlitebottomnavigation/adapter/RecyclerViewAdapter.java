package com.example.sqlitebottomnavigation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitebottomnavigation.R;
import com.example.sqlitebottomnavigation.model.Khoahoc;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeViewHolder> {
    private List<Khoahoc> list;
    private ItemListener itemListener;

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Khoahoc> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Khoahoc getItemAtPosition(int position) {
        return list.get(position);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Khoahoc item = list.get(position);
        holder.name.setText(item.getName());
        if(item.getActive() == 0){
            holder.active.setText("Chưa kích hoạt học phí");
        }
        else if(item.getActive() == 1){
            holder.active.setText("Đã kích hoạt học phí");
        }
        holder.chuyenNganh.setText(item.getChuyenNganh());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name,active,chuyenNganh,date;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            active = itemView.findViewById(R.id.tvActive);
            chuyenNganh = itemView.findViewById(R.id.tvChuyenNganh);
            date = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        public void onItemClick(View view, int position);
    }
}
