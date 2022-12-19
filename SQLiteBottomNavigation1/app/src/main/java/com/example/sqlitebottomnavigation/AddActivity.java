package com.example.sqlitebottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.sqlitebottomnavigation.dal.SQLiteHelper;
import com.example.sqlitebottomnavigation.model.Book;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    public Spinner sp;
    private EditText eTitle, eDate,eAuthor,ePrice;
    //private RadioButton rd1minh,rdlamchung;
    private Button btAdd, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spProducer);
        eTitle = findViewById(R.id.tvTitle);
        eDate = findViewById(R.id.tvDate);
        eAuthor= findViewById(R.id.tvAuthor);
        ePrice=findViewById(R.id.tvPrice);
//        rd1minh=findViewById(R.id.rd1minh);
//        rdlamchung=findViewById(R.id.rdLamchung);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.nxb)));
    }

    @Override
    public void onClick(View v) {
        if (v == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = "";
                    String day = "";
                    if (dayOfMonth < 10) {
                        day = "0" + dayOfMonth;
                    } else {
                        day += dayOfMonth;
                    }
                    if (month > 8) {
                        date = year + "-" + (month + 1) + "-" + day;
                    } else {
                        date = year + "-0" + (month + 1) + "-" + day;
                    }
                    eDate.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }

        if (v == btCancel) {
            finish();
        }

        if (v == btAdd) {
            String title = eTitle.getText().toString();
            String author = eAuthor.getText().toString();
            String producer = sp.getSelectedItem().toString();
            String date = eDate.getText().toString();
            String price = ePrice.getText().toString();
//            boolean isCooperated = false;
//            if (rdlamchung.isChecked()) {
//                isCooperated = true;
//            }
            if(!title.isEmpty() && !author.isEmpty() && !producer.isEmpty() && !date.isEmpty() && !price.isEmpty()) {
                Book book = new Book(title,author,date,producer,price);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addBook(book);
                finish();
            } else {
                Snackbar.make(btAdd, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
    }
}