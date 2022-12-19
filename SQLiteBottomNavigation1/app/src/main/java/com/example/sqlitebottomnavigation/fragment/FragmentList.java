package com.example.sqlitebottomnavigation.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sqlitebottomnavigation.R;
import com.example.sqlitebottomnavigation.UpdateDeleteActivity;
import com.example.sqlitebottomnavigation.adapter.RecyclerViewAdapter;
import com.example.sqlitebottomnavigation.dal.SQLiteHelper;
import com.example.sqlitebottomnavigation.model.Book;

import java.util.List;

public class FragmentList extends Fragment implements RecyclerViewAdapter.ItemListener {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Book> listDb = db.getAll();
        adapter.setList(listDb);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Book item = adapter.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("book", item);
        startActivity(intent);
    }

    // Lam tuoi moi lan co doi tuong them vao
    @Override
    public void onResume() {
        super.onResume();
        List<Book> list = db.getAll();
        adapter.setList(list);
    }
}