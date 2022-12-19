package com.example.sqlitetab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.sqlitetab.dal.SQLiteHelper;
import com.example.sqlitetab.model.Room;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    
    private EditText eDiachi, eMota,eDientich,eGia,eMaxpeo,eSDT;
    private CheckBox wifi,dieuhoa,maygiat;
    private Button btAdd, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    private void initView() {
        eDiachi = findViewById(R.id.tvDiachi);
        eMota = findViewById(R.id.tvMota);
        eDientich = findViewById(R.id.tvDientich);
        wifi=findViewById(R.id.wifi);
        dieuhoa=findViewById(R.id.dieuhoa);
        maygiat=findViewById(R.id.maygiat);
        //ePhuongtien = findViewById(R.id.tvPhuongtien);
        eGia = findViewById(R.id.tvGia);
        eMaxpeo =findViewById(R.id.tvmaxpeople);
        eSDT =findViewById(R.id.tvSDT);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);
        
    }

    @Override
    public void onClick(View v) {
        

        if (v == btCancel) {
            finish();
        }

        if (v == btAdd) {
            String diachi = eDiachi.getText().toString();
            String mota = eMota.getText().toString();
            String dientich = eDientich.getText().toString();
            String gia = eGia.getText().toString();
            String phuongtien = "";
            if(wifi.isChecked()){
                phuongtien += wifi.getText().toString()+",";
            }
            if(dieuhoa.isChecked()){
                phuongtien += dieuhoa.getText().toString()+",";
            }
            if(maygiat.isChecked()){
                phuongtien += maygiat.getText().toString();
            }
            String maxpeo = eMaxpeo.getText().toString();
            String sdt = eSDT.getText().toString();

            if(!diachi.isEmpty() && !mota.isEmpty() && !dientich.isEmpty() && !gia.isEmpty() && !maxpeo.isEmpty() && !sdt.isEmpty()) {
                Room room = new Room(diachi,phuongtien,mota,dientich,gia,maxpeo,sdt);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addRoom(room);
                finish();
            } else {
                Snackbar.make(btAdd, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
    }
}