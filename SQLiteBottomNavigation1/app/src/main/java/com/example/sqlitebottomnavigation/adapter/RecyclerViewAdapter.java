package com.example.sqlitebottomnavigation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitebottomnavigation.R;
import com.example.sqlitebottomnavigation.model.Book;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeViewHolder> {
    private List<Book> list;
    private ItemListener itemListener;

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Book> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Book getItemAtPosition(int position) {
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
        Book item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.author.setText(item.getAuthor());
        holder.producer.setText(item.getProducer());
        holder.price.setText(item.getPrice() +"VND");
//        if (item.isCooperated()) {
//            holder.isCooperated.setText("lam chung");
//        } else {
//            holder.isCooperated.setText("1 minh");
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,author,date,producer,price;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            date = itemView.findViewById(R.id.tvDate);
            author=itemView.findViewById(R.id.tvAuthor);
            producer = itemView.findViewById(R.id.tvProducer);
            price=itemView.findViewById(R.id.tvPrice);
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
