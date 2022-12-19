package com.example.a11_03_alarmdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a11_03_alarmdemo.model.MyTodo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView txtEnd;
    private SQLiteHelper db;
    private Todo_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MyToDoList");

        db=new SQLiteHelper(this);
        adapter=new Todo_Adapter(this);
        adapter.setData(db.getAll());

        fab=findViewById(R.id.fabutton);
        recyclerView=findViewById(R.id.todo_list_view);
        txtEnd=findViewById(R.id.endpage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,
                        NewTodoActivity.class);
                startActivity(intent);
            }
        });
    }

    //@Override
//    protected void onRestart() {
//        List<MyTodo> list = db.getAll();
//        adapter.setData(list);
//        recyclerView.setAdapter(adapter);
//        super.onRestart();
//    }

    @Override
    protected void onResume() {
        List<MyTodo> list = db.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }
}
