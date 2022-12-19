package com.example.a11_03_alarmdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.a11_03_alarmdemo.model.MyTodo;

import java.util.Calendar;

public class EditDeleteTodoActivity extends AppCompatActivity {
    private EditText titledoes, descdoes, datedoes;
    private Button btnEdit, btnDelete,btnCancel,btnSelectDate;
    private SQLiteHelper db;
    private int mYear, mMonth, mDay;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_todo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        id=0;
        db=new SQLiteHelper(this);
        final Intent intent=getIntent();
        if(intent.getSerializableExtra("todo")!=null) {
            MyTodo t = (MyTodo) intent.getSerializableExtra("todo");
            id=t.getId();
            titledoes.setText(t.getTitle());
            datedoes.setText(t.getDate());
            descdoes.setText(t.getDescribe());
        }
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(EditDeleteTodoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                datedoes.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id>0){
                    db.deleteTodo(id);
                    finish();
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id>0){
                    MyTodo t=new MyTodo(id,titledoes.getText().toString(),
                            datedoes.getText().toString(),descdoes.getText().toString());
                    db.updateTodo(t);
                    finish();
                }
            }
        });
    }

    private void initView() {
        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);

        btnEdit=findViewById(R.id.btnEdit);
        btnDelete=findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancelUpdate);
        btnSelectDate=findViewById(R.id.btnChosseDay);
    }
}
