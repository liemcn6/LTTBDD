package com.example.a08_02_loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a08_02_loginregister.model.User;

public class RegisterActivity extends AppCompatActivity
    implements View.OnClickListener{
    private Button btnCancel, btnRegister;
    private TextView txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        btnCancel.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                User user = new User(txtUsername.getText().toString(),
                        txtPassword.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("Data", user);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED, null);
                finish();
                break;
        }
    }
}
