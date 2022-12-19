package com.example.sqlitebottomnavigation.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Spinner;

import com.example.sqlitebottomnavigation.R;
import com.example.sqlitebottomnavigation.adapter.RecyclerViewAdapter;
import com.example.sqlitebottomnavigation.dal.SQLiteHelper;
import com.example.sqlitebottomnavigation.model.Khoahoc;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;


public class FragmentSearch extends Fragment implements View.OnClickListener {

    private SearchView search;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SQLiteHelper db;
    private EditText eFrom,eTo;
    private Button searchButton;
    private Spinner sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecyclerViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Khoahoc> listDb = db.getAll();
        adapter.setList(listDb);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Khoahoc> list = db.searchByName(newText);
                adapter.setList(list);
                return true;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chuyenNganh = sp.getItemAtPosition(position).toString();
                List<Khoahoc> list;
                        if(!chuyenNganh.equalsIgnoreCase("All")){
                            list = db.searchBychuyenNganh(chuyenNganh);
                        }
                        else{
                            list = db.getAll();
                        }
                        adapter.setList(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initView (View view) {
        search = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        eFrom = view.findViewById(R.id.tvFrom);
        eTo = view.findViewById(R.id.tvTO);
        searchButton = view.findViewById(R.id.searchbutton);
        sp = view.findViewById(R.id.spChuyenNganh);
        String [] arr = getResources().getStringArray(R.array.status);
        String [] arr1 = new String[arr.length + 1];
        arr1 [0] = "All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        sp.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));
    };


    @Override
    public void onResume() {
        super.onResume();
        List<Khoahoc> list = db.getAll();
        adapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        if (v == eFrom) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = "";
                    String day = "";
                    if (dayOfMonth < 10) {
                        day = "0" + dayOfMonth;
                    } else {
                        day += dayOfMonth;
                    }
                    if (month > 8) {
                        date = year + "-" + (month + 1) + "-" + day;
                    } else {
                        date = year + "-0" + (month + 1) + "-" + day;
                    }
                    eFrom.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }
        if (v == eTo) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = "";
                    String day = "";
                    if (dayOfMonth < 10) {
                        day = "0" + dayOfMonth;
                    } else {
                        day += dayOfMonth;
                    }
                    if (month > 8) {
                        date = year + "-" + (month + 1) + "-" + day;
                    } else {
                        date = year + "-0" + (month + 1) + "-" + day;
                    }
                    eTo.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }
        if(v == searchButton){
            String fromDate = eFrom.getText().toString();
            String toDate = eTo.getText().toString();
            if(!fromDate.isEmpty()  && !toDate.isEmpty()) {
                List<Khoahoc> list = db.searchByDateFromTo(fromDate,toDate);
                adapter.setList(list);
            } else {
                Snackbar.make(searchButton, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }

        }
    }
}