package com.example.a11_03_alarmdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a11_03_alarmdemo.model.MyTodo;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TodoDB.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          String sqlCreateDatabase="CREATE TABLE TodoTable(" +
                  "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                  "title TEXT," +
                  "date TEXT," +
                  "describe TEXT)";
          db.execSQL(sqlCreateDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long addTodo(MyTodo t) {
        ContentValues values = new ContentValues();
        values.put("title", t.getTitle());
        values.put("date", t.getDate());
        values.put("describe", t.getDescribe());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("TodoTable",
                null, values);
    }

    public MyTodo getTodo(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("TodoTable",
                null, whereClause, whereArgs,
                null, null, null);
        if (rs != null && rs.moveToFirst()) {
            String title = rs.getString(1);
            String date = rs.getString(2);
            String desc = rs.getString(3);
            rs.close();
            return new MyTodo(id,title,date,desc);
        }
        return null;
    }

    public List<MyTodo> getAll() {
        List<MyTodo> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("TodoTable",
                null, null, null,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String date = rs.getString(2);
            String desc = rs.getString(3);
            list.add(new MyTodo(id,title,date,desc));
            }
        return list;
        }

    public int updateTodo(MyTodo t) {
        ContentValues values = new ContentValues();
        values.put("title", t.getTitle());
        values.put("date", t.getDate());
        values.put("describe", t.getDescribe());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(t.getId())};
        return sqLiteDatabase.update("TodoTable",
                values, whereClause, whereArgs);
    }

    public int deleteTodo(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("TodoTable",
                whereClause, whereArgs);
    }
}
