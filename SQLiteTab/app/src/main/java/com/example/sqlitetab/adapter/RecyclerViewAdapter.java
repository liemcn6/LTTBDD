package com.example.sqlitetab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitetab.R;
import com.example.sqlitetab.model.Room;


import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeViewHolder> {
    private List<Room> list;
    private ItemListener itemListener;

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Room> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Room getItemAtPosition(int position) {
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
        Room item = list.get(position);
        holder.diachi.setText(item.getDiachi());
        holder.dichvu.setText(item.getDichvu());
        holder.mota.setText(item.getMota());
        holder.dientich.setText(item.getDientich() +"m2");
        holder.gia.setText(item.getGia() +"VND");
        holder.maxpeo.setText("max: " +item.getMaxPeople()+" nguoi");
        holder.sdt.setText(item.getSdt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView diachi,dichvu,mota,dientich,gia,maxpeo,sdt;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            diachi = itemView.findViewById(R.id.tvDiachi);
            dichvu = itemView.findViewById(R.id.tvDichvu);
            mota = itemView.findViewById(R.id.tvMota);
            dientich = itemView.findViewById(R.id.tvDientich);
            gia = itemView.findViewById(R.id.tvGia);
            maxpeo = itemView.findViewById(R.id.tvmaxpeople);
            sdt = itemView.findViewById(R.id.tvSDT);
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

