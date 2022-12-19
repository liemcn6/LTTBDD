package com.example.a08_02_loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.a08_02_loginregister.model.User;

public class MainActivity extends AppCompatActivity {
    private TextView txtInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtInfor=findViewById(R.id.txtInfor);
        Intent receiveIntent=getIntent();
        if(receiveIntent.getSerializableExtra("acount")!=null){
            User u=(User)receiveIntent.getSerializableExtra("acount");
            User u1=(User)receiveIntent.getSerializableExtra("Data");
            if(u.getUsername().equalsIgnoreCase(u1.getUsername())&& u.getPassword().equals(u1.getPassword())) {
                txtInfor.setText("HELLO " + u.getUsername() + " DA DANG NHAP VAO HE THONG");
            }else{
                txtInfor.setText("Dang nhap sai!!!");
            }
        } else {
            txtInfor.setText("TAI KHOAN NAY KHONG TON TAI!");
        }
    }
}
