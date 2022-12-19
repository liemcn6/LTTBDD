package com.example.sqlitebottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sqlitebottomnavigation.dal.SQLiteHelper;
import com.example.sqlitebottomnavigation.model.Book;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    public Spinner sp;
    private EditText eTitle, eAuthor, eDate, ePrice;
    //private RadioButton rd1minh,rdlamchung;
    private Button btUpdate, btBack, btRemove;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        eTitle.setText(book.getTitle());
        eDate.setText(book.getDate());
        eAuthor.setText(book.getAuthor());
        ePrice.setText(book.getPrice());
//        if (Book.isCooperated()) {
//            rdlamchung.setChecked(true);
//        } else {
//            rd1minh.setChecked(true);
//        }
        int position = 0;
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(book.getProducer())){
                position = i;
                break;
            }
        }
        sp.setSelection(position);
    }

    private void initView() {
        sp = findViewById(R.id.spProducer);
        eTitle = findViewById(R.id.tvTitle);
        eDate = findViewById(R.id.tvDate);
        eAuthor = findViewById(R.id.tvAuthor);
        ePrice=findViewById(R.id.tvPrice);
//        rdlamchung=findViewById(R.id.rdLamchung);
//        rd1minh=findViewById(R.id.rd1minh);
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        btBack = findViewById(R.id.btBack);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.nxb)));
    }

    @Override
    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);

        if (v == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        if (v == btBack) {
            finish();
        }

        if (v == btUpdate) {
            String title = eTitle.getText().toString();
            String author = eAuthor.getText().toString();
            String producer = sp.getSelectedItem().toString();
            String date = eDate.getText().toString();
            String price = ePrice.getText().toString();
//            boolean isCooperate = false;
//            if (rdlamchung.isChecked()) {
//                isCooperate = true;
//            }
            if(!title.isEmpty() && !author.isEmpty() && !producer.isEmpty() && !date.isEmpty() && !price.isEmpty()) {
                int id = book.getId();
                Book Book = new Book(id,title,author,date,producer,price);
                db.updateBook(Book);
                finish();
            } else {
                Snackbar.make(btUpdate, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }

        if (v == btRemove) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa công việc " + book.getTitle() + " không?");
            builder.setIcon(R.drawable.ic_remove);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(book.getId());
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