package com.example.baeminfake.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.baeminfake.R;
import com.example.baeminfake.fragment.MailLoginFragment;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.author_activity);

        Fragment current = new MailLoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.author_holder, current, "TAG")
                .addToBackStack(null)
                .commit();
    }
}
