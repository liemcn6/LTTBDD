package com.example.sqlitetab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitetab.R;
import com.example.sqlitetab.adapter.RecyclerViewAdapter;
import com.example.sqlitetab.dal.SQLiteHelper;
import com.example.sqlitetab.model.Room;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SearchView searchView;
    private Button btSearch;
    private EditText eFrom,eTo;
    private Spinner spinner;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter=new RecyclerViewAdapter();
        db=new SQLiteHelper(getContext());
        List<Room> list=db.getAll();
        adapter.setList(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        btSearch.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate=spinner.getItemAtPosition(position).toString();
                List<Room> list;
                if(!cate.equalsIgnoreCase("all")){
                    //list=db.searchByCategory(cate);
                }
                else list=db.getAll();
                //adapter.setList(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //adapter.setItemListener();
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.recycleView);
        searchView=view.findViewById(R.id.searchView);
        btSearch=view.findViewById(R.id.button);
        eFrom=view.findViewById(R.id.tvFrom);
        eTo=view.findViewById(R.id.tvTo);
        spinner=view.findViewById(R.id.spinner);
       // String[] arr=getResources().getStringArray(R.array.nxb);
        String[] arr={"Giáo va dục",
                "Khoa học và xã hội",
                "Bưu điện",
                "Thống"};

        String[] arr1=new String[arr.length+1];
        arr1[0]="All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));

    }

    @Override
    public void onClick(View view) {

        if(view==btSearch){
            String from=eFrom.getText().toString();
            String to=eTo.getText().toString();
            if(!from.isEmpty()&&!to.isEmpty()){
               // List<Room> list=db.searchBypriceFromTo(from,to);
               // adapter.setList(list);
            }

        }
    }
}
