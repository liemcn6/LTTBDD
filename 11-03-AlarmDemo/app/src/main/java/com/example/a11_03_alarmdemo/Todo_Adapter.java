package com.example.a11_03_alarmdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11_03_alarmdemo.model.MyTodo;

import java.util.ArrayList;
import java.util.List;

public class Todo_Adapter extends
        RecyclerView.Adapter<Todo_Adapter.TodoViewHolder> {
    Context mContext;
    List<MyTodo> myTodoArrayList;
    public Todo_Adapter(Context mContext) {
        this.mContext = mContext;
        myTodoArrayList=new ArrayList<>();
    }
    public void setData(List<MyTodo> myTodoArrayList){
        this.myTodoArrayList=myTodoArrayList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.item_todo,parent,false);
        TodoViewHolder todoViewHolder=new TodoViewHolder(view);
        return todoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, final int position) {
        holder.title.setText(myTodoArrayList.get(position).getTitle());
        holder.desc.setText(myTodoArrayList.get(position).getDescribe());
        holder.date.setText(myTodoArrayList.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, EditDeleteTodoActivity.class);
                intent.putExtra("todo",myTodoArrayList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myTodoArrayList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, date;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titletodo);
            desc = (TextView) itemView.findViewById(R.id.desctodo);
            date = (TextView) itemView.findViewById(R.id.datetodo);

        }
    }
}
