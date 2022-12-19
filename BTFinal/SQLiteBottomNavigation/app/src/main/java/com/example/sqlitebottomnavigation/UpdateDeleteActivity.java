package com.example.sqlitebottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    public Spinner sp;
    private EditText eName,eDate;
    private Button btUpdate, btBack, btRemove;
    private Khoahoc khoahoc;
    private CheckBox cbActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);

        Intent intent = getIntent();
        khoahoc = (Khoahoc) intent.getSerializableExtra("work");
        eName.setText(khoahoc.getName());
        eDate.setText(khoahoc.getDate());
        if(khoahoc.getActive() == 1){
            cbActive.setChecked(true);
        }
        else{
            cbActive.setChecked(false);
        }
        int position = 0;
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(khoahoc.getChuyenNganh())){
                position = i;
                break;
            }
        }
        sp.setSelection(position);
    }

    private void initView() {
        sp = findViewById(R.id.spChuyenNganh);
        eName = findViewById(R.id.tvName);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        btBack = findViewById(R.id.btBack);
        cbActive = findViewById(R.id.cbActive);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.status)));
    }

    @Override
    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);

        if (v == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        if (v == btBack) {
            finish();
        }

        if (v == btUpdate) {
            String name = eName.getText().toString();
            String date =eDate.getText().toString();
            String chuyenNganh = sp.getSelectedItem().toString();
            int active = 0;
            if(cbActive.isChecked()){
                active = 1;
            }
            if(!name.isEmpty()  && !date.isEmpty()) {
                int id = this.khoahoc.getId();
                Khoahoc khoahoc = new Khoahoc(id,active, name, date, chuyenNganh);
                db.updateKhoaHoc(khoahoc);
                finish();
            } else {
                Snackbar.make(btUpdate, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }

        if (v == btRemove) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa công việc " + khoahoc.getName() + " không?");
            builder.setIcon(R.drawable.ic_remove);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteKhoaHoc(khoahoc.getId());
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}