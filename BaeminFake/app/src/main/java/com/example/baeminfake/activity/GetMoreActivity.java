package com.example.baeminfake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baeminfake.R;
import com.example.baeminfake.controller.FoodFullDetailRecyclerViewAdapter;
import com.example.baeminfake.controller.SQLiteFoodHelper;
import com.example.baeminfake.model.Food;

import java.util.List;

public class GetMoreActivity extends AppCompatActivity {

    private EditText search;
    private TextView title, turnback;
    private RecyclerView list_getmore;
    private int category;
    private String name;
    private SQLiteFoodHelper sqLite;
    private FoodFullDetailRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.getmore_activity);
        init();

        switch (category) {
            case 0: {
                setCase("đồ uống", 0);
                break;
            }
            case 1: {
                setCase("món tráng miệng", 1);
                break;
            }
            case 2: {
                setCase("bánh mì", 2);
                break;
            }
            case 3: {
                setCase("món chính", 3);
                break;
            }
            case 4: {
                setCase("pizza", 4);
                break;
            }
            case 5: {
                setCase("beefsteak", 5);
                break;
            }
            case 6: {
                setCase("sandwich", 6);
                break;
            }
            case 7: {
                setCase("đồ hộp", 7);
                break;
            }
            case 8: {
                setCase("cơm suất", 8);
                break;
            }
        }

        if (name != null && !name.equals("")) {
            title.setText("Quán " + name);
            FoodFullDetailRecyclerViewAdapter adapter = new FoodFullDetailRecyclerViewAdapter(this);
            list_getmore.setLayoutManager(new LinearLayoutManager(this));
            list_getmore.setAdapter(adapter);

            sqLite = new SQLiteFoodHelper(this);

            List<Food> foods = sqLite.getFoodsByRestaurant(name);
            adapter.setFoods(foods);
            list_getmore.setAdapter(adapter);
        }

        this.turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetMoreActivity.this, MainActivity.class));
                GetMoreActivity.this.finish();
            }
        });

        this.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (category != -1) {
                    List<Food> list = sqLite.getFoodsByNameAndCategory(category, str);
                    adapter.setFoods(list);
                    list_getmore.setAdapter(adapter);
                } else {
                    List<Food> list = sqLite.getFoodsByRestaurantAndName(str, name);
                    adapter.setFoods(list);
                    list_getmore.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        search = findViewById(R.id.search_getmore);
        title = findViewById(R.id.getmore_title);
        turnback = findViewById(R.id.back_to_main4);
        list_getmore = findViewById(R.id.list_getmore);

        Intent intent = getIntent();
        category = intent.getIntExtra("category", -1);
        name = intent.getStringExtra("restaurant_name");

        adapter = new FoodFullDetailRecyclerViewAdapter(this);
        list_getmore.setLayoutManager(new LinearLayoutManager(this));
        list_getmore.setAdapter(adapter);

        sqLite = new SQLiteFoodHelper(this);
    }

    private void setCase(String str, int cgr) {
        title.setText("Danh sách " + str);

        List<Food> foods = sqLite.getFoodsByCategory(cgr);
        adapter.setFoods(foods);
        list_getmore.setAdapter(adapter);
    }
}
