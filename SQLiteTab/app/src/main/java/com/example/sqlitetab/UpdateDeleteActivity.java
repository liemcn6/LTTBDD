package com.example.sqlitetab;

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

import com.example.sqlitetab.dal.SQLiteHelper;
import com.example.sqlitetab.model.Room;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText eDiachi, eMota,eDientich,eGia,eMaxpeo,eSDT;
    private CheckBox wifi,dieuhoa,maygiat;
    private Button btUpdate, btBack, btRemove;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btRemove.setOnClickListener(this);

        Intent intent = getIntent();
        room = (Room) intent.getSerializableExtra("room");
        eDiachi.setText(room.getDiachi());
        if(room.getDichvu().equals("Wifi")){
            wifi.setChecked(true);
        }
        if(room.getDichvu().equals("Dieu hoa")){
            dieuhoa.setChecked(true);
        }
        if(room.getDichvu().equals("May giat")){
            maygiat.setChecked(true);
        }
        eMota.setText(room.getMota());
        eDientich.setText(room.getDientich());
        eGia.setText(room.getGia());
        eMaxpeo.setText(room.getMaxPeople());
        eSDT.setText(room.getSdt());

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
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        btBack = findViewById(R.id.btBack);
    }

    @Override

    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);


        if (v == btBack) {
            finish();
        }

        if (v == btUpdate) {
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
                int id = room.getId();
                Room room = new Room(id,diachi,phuongtien,mota,dientich,gia,maxpeo,sdt);
                db.updateRoom(room);
                finish();
            } else {
                Snackbar.make(btUpdate, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }

        if (v == btRemove) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa công việc " + room.getDiachi() + " không?");
            builder.setIcon(R.drawable.ic_remove);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(room.getId());
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