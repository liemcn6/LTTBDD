package com.example.baeminfake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baeminfake.R;

public class SplashActivity extends AppCompatActivity {

    private Runnable runnable;
    private Handler handler;
    public static int login_code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginOptionActivity.class));
                finish();
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 3000);
    }
}
