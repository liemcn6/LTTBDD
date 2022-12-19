package com.example.sqlitebottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sqlitebottomnavigation.dal.SQLiteHelper;
import com.example.sqlitebottomnavigation.model.Khoahoc;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    public Spinner sp;
    private EditText eName,eDate;
    private Button btAdd, btCancel;
    private CheckBox cbActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spChuyenNganh);
        eName = findViewById(R.id.tvName);
        eDate = findViewById(R.id.tvDate);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);
        cbActive = findViewById(R.id.cbActive);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.status)));
    }

    @Override
    public void onClick(View v) {
        if (v == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    eDate.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }

        if (v == btCancel) {
            finish();
        }

        if (v == btAdd) {
            String name = eName.getText().toString();
            String date =eDate.getText().toString();
            String chuyenNganh = sp.getSelectedItem().toString();
            int active = 0;
            if(cbActive.isChecked()){
                active = 1;
            }
            if(!name.isEmpty()  &&  !date.isEmpty()) {
                Khoahoc khoahoc = new Khoahoc(active, name, date, chuyenNganh);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addKhoaHoc(khoahoc);
                finish();
            } else {
                Snackbar.make(btAdd, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
    }
}