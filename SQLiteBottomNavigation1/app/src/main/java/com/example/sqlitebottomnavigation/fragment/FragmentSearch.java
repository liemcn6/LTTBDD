package com.example.sqlitebottomnavigation.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.sqlitebottomnavigation.AddActivity;
import com.example.sqlitebottomnavigation.R;
import com.example.sqlitebottomnavigation.adapter.RecyclerViewAdapter;
import com.example.sqlitebottomnavigation.dal.SQLiteHelper;
import com.example.sqlitebottomnavigation.model.Book;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private TextView tvTong;
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
        List<Book> list=db.getAll();
        tvTong.setText("So luong sach :" + list.size());
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
                List<Book> list1=db.getByTitle(s);
                adapter.setList(list1);
                tvTong.setText("So luong sach :" + list1.size());
                return true;
            }
        });
       // eFrom.setOnClickListener(this);
        //eTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate=spinner.getItemAtPosition(position).toString();
                List<Book> list;
                if(!cate.equalsIgnoreCase("all")){
                    list=db.getByCategory(cate);
                }
                else list=db.getAll();

                adapter.setList(list);
                tvTong.setText("So luong sach :" + list.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //adapter.setItemListener();
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.recycleView);
        tvTong=view.findViewById(R.id.tvTong);
        searchView=view.findViewById(R.id.searchView);
        btSearch=view.findViewById(R.id.button);
        eFrom=view.findViewById(R.id.eFrom);
        eTo=view.findViewById(R.id.eTo);
        spinner=view.findViewById(R.id.spinner);
        String[] arr=getResources().getStringArray(R.array.nxb);
        String[] arr1=new String[arr.length+1];
        arr1[0]="All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));

    }

    @Override
    public void onClick(View view) {
//        if(view==eFrom){
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    String date = "";
//                    String day = "";
//                    if (dayOfMonth < 10) {
//                        day = "0" + dayOfMonth;
//                    } else {
//                        day += dayOfMonth;
//                    }
//                    if (month > 8) {
//                        date = year + "-" + (month + 1) + "-" + day;
//                    } else {
//                        date = year + "-0" + (month + 1) + "-" + day;
//                    }
//                    eFrom.setText(date);
//                }
//            }, year, month, day);
//            dialog.show();
//        }
//        if(view==eTo){
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    String date = "";
//                    String day = "";
//                    if (dayOfMonth < 10) {
//                        day = "0" + dayOfMonth;
//                    } else {
//                        day += dayOfMonth;
//                    }
//                    if (month > 8) {
//                        date = year + "-" + (month + 1) + "-" + day;
//                    } else {
//                        date = year + "-0" + (month + 1) + "-" + day;
//                    }
//                    eTo.setText(date);
//                }
//            }, year, month, day);
//            dialog.show();
//        }
        if(view==btSearch){
            String from=eFrom.getText().toString();
            String to=eTo.getText().toString();
            if(!from.isEmpty()&&!to.isEmpty()){
                List<Book> list=db.getByPricefromTo(from,to);
                adapter.setList(list);
                tvTong.setText("So luong sach :" + list.size());
            }

        }
    }
}