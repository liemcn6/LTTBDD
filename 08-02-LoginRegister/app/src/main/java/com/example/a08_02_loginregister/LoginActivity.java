package com.example.a08_02_loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a08_02_loginregister.model.User;


public class LoginActivity extends AppCompatActivity
   implements View.OnClickListener{
    private Button btnLogin, btnRegister;
    private TextView txtUsername, txtPassword;
    private final static int REQUEST_CODE = 10000;
    private User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin =  findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtUsername =  findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Intent loginIntent = new Intent(getApplicationContext(),
                        MainActivity.class);
                User user = new User(txtUsername.getText().toString(),
                        txtPassword.getText().toString());
                loginIntent.putExtra("acount", user);
                loginIntent.putExtra("Data",u);
                startActivity(loginIntent);
                break;
            case R.id.btnRegister:
                Intent registerIntent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivityForResult(registerIntent, REQUEST_CODE);
        }
        }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, @Nullable
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(data == null) {
                    Toast.makeText(getApplicationContext(), "Ban da huy dang ki", Toast.LENGTH_LONG).show();
                } else {
                    u = (User) data.getSerializableExtra("Data");
                    txtUsername.setText(u.getUsername());
                    txtPassword.setText(u.getPassword());
                }
            }
        }
    }
}
