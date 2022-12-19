package com.example.sqlitebottomnavigation.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitebottomnavigation.model.Khoahoc;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KhoaHoc.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE khoaHoc(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "active INT," +
                "chuyenNganh TEXT," +
                "date TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Khoahoc> getAll() {
        List<Khoahoc> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("khoaHoc", null, null,
                null, null, null, order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            int active = rs.getInt(2);
            String chuyenNganh = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Khoahoc(id, active, name, date, chuyenNganh));
        }
        return list;
    }

    public long addKhoaHoc(Khoahoc i) {
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("active", i.getActive());
        values.put("date", i.getDate());
        values.put("chuyenNganh", i.getChuyenNganh());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("khoaHoc", null, values);
    }

    public int updateKhoaHoc(Khoahoc i) {
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("active", i.getActive());
        values.put("date", i.getDate());
        values.put("chuyenNganh", i.getChuyenNganh());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return db.update("khoaHoc", values, whereClause, whereArgs);
    }

    public int deleteKhoaHoc(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("khoaHoc", whereClause, whereArgs);
    }

    public List<Khoahoc> searchByName(String key) {
        List<Khoahoc> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgument = {"%"+key+"%"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("khoaHoc", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            int active = rs.getInt(2);
            String chuyenNganh = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Khoahoc(id, active, name, date, chuyenNganh));
        }
        return list;
    }

    public List<Khoahoc> searchBychuyenNganh(String status) {
        List<Khoahoc> list = new ArrayList<>();
        String whereClause = "chuyenNganh like ?"; // Dieu kien where
        String[] whereArgument = {status}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("khoaHoc", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            int active = rs.getInt(2);
            String chuyenNganh = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Khoahoc(id, active, name, date, chuyenNganh));
        }
        return list;
    }


    public List<Khoahoc> searchByDateFromTo(String from, String to) {
        List<Khoahoc> list = new ArrayList<>();
        String whereClause = "date BETWEEN (?) AND (?)"; // Dieu kien where
        String[] whereArgument = {from.trim(), to.trim()}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("khoaHoc", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            int active = rs.getInt(2);
            String chuyenNganh = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Khoahoc(id, active, name, date, chuyenNganh));
        }
        return list;
    }

}
