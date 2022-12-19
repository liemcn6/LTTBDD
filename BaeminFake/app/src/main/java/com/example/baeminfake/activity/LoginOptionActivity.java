package com.example.baeminfake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baeminfake.R;

public class LoginOptionActivity extends AppCompatActivity {

    private TextView lgInsta, lgFaceb, lgMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_option_activity);

        lgInsta = findViewById(R.id.with_insta);
        lgFaceb = findViewById(R.id.with_fb);
        lgMail = findViewById(R.id.with_mail);

        this.lgMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginOptionActivity.this, AuthorActivity.class));
                LoginOptionActivity.this.finish();
            }
        });

        this.lgFaceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginOptionActivity.this, FacebookLoginActivity.class));
                LoginOptionActivity.this.finish();
            }
        });
    }
}