package com.example.a11_03_alarmdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.a11_03_alarmdemo.model.MyTodo;

import java.util.Calendar;

public class NewTodoActivity extends AppCompatActivity {
    private EditText titledoes, descdoes, datedoes;
    private Button btnSaveTask, btnCancel,btnSelectDate;
    private SQLiteHelper db;

    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        db=new SQLiteHelper(this);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
         btnSelectDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final Calendar c=Calendar.getInstance();
                 mYear=c.get(Calendar.YEAR);
                 mMonth=c.get(Calendar.MONTH);
                 mDay=c.get(Calendar.DAY_OF_MONTH);
                 DatePickerDialog datePickerDialog=new DatePickerDialog(NewTodoActivity.this,
                         new DatePickerDialog.OnDateSetListener() {
                             @Override
                             public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                 datedoes.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                             }
                         },mYear, mMonth, mDay);
                 datePickerDialog.show();
             }
         });
         btnSaveTask.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String title=titledoes.getText().toString();
                 String time=datedoes.getText().toString();
                 String desc=descdoes.getText().toString();
                 MyTodo t=new MyTodo(title,time,desc);
                 db.addTodo(t);

                 String [] time_spilt=time.split("/");
                 int date=Integer.parseInt(time_spilt[0]);
                 int month=Integer.parseInt(time_spilt[1])-1;
                 int year=Integer.parseInt(time_spilt[2]);
                 Calendar calendar = Calendar.getInstance();
                 calendar.setTimeInMillis(System.currentTimeMillis());
                 calendar.set(year,month,date);

                 AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                 Intent intent = new Intent(NewTodoActivity.this,
                         MyReceiver.class);
                 intent.putExtra("myAction", "mDoNotify");
                 intent.putExtra("Title",titledoes.getText().toString());
                 intent.putExtra("Description", descdoes.getText().toString());

                 PendingIntent pendingIntent = PendingIntent.getBroadcast(NewTodoActivity.this,
                         0, intent, 0);
                 am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                 finish();
             }
         });
    }

    private void initView() {
        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);
        btnSelectDate=findViewById(R.id.btnChosseDay);
    }
}
