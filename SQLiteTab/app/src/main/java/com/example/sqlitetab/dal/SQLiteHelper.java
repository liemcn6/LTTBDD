package com.example.sqlitetab.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitetab.model.Room;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Phongthue.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE room(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "diachi TEXT," +
                "dichvu TEXT," +
                "mota TEXT," +
                "dientich TEXT," +
                "gia TEXT," +
                "maxPeople TEXT," +
                "sdt TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // get all + order by date descending
    public List<Room> getAll() {
        List<Room> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "gia DESC";
        Cursor rs = st.query("room", null, null,
                null, null, null, order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String diachi = rs.getString(1);
            String dichvu  = rs.getString(2);
            String mota = rs.getString(3);
            String dientich = rs.getString(4);
            String gia = rs.getString(5);
            String maxPeople = rs.getString(6);
            String sdt = rs.getString(7);

            list.add(new Room(id,diachi,dichvu,mota,dientich,gia,maxPeople,sdt));
        }
        return list;
    }

    // add
    public long addRoom(Room i) {
        ContentValues values = new ContentValues();
        values.put("diachi", i.getDiachi());
        values.put("dichvu", i.getDichvu());
        values.put("mota", i.getMota());
        values.put("dientich", i.getDientich());
        values.put("gia", i.getGia());
        values.put("maxPeople", i.getMaxPeople());
        values.put("sdt", i.getSdt());

        SQLiteDatabase db = getWritableDatabase();
        return db.insert("room", null, values);
    }


    // update
    public int updateRoom(Room i) {
        ContentValues values = new ContentValues();
        values.put("diachi", i.getDiachi());
        values.put("dichvu", i.getDichvu());
        values.put("mota", i.getMota());
        values.put("dientich", i.getDientich());
        values.put("gia", i.getGia());
        values.put("maxPeople", i.getMaxPeople());
        values.put("sdt", i.getSdt());

        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return db.update("room", values, whereClause, whereArgs);
    }

    // delete
    public int delete(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("room", whereClause, whereArgs);
    }

    // lay cac item theo title

    // lay cac item theo status
//    public List<Room> searchByCategory(String nxb) {
//        List<Room> list = new ArrayList<>();
//        String whereClause = "nxb like ?"; // Dieu kien where
//        String[] whereArgument = {nxb}; // Cho ? argument
//        String order = "price DESC";
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor rs = db.query("Room", null, whereClause, whereArgument,
//                null, null, order);
//        while(rs != null && rs.moveToNext()) {
//            int id = rs.getInt(0);
//            String lichtrinhFrom = rs.getString(1);
//            String lichtrinhTo  = rs.getString(2);
//            String time = rs.getString(3);
//            String phuongtien = rs.getString(4);
//            String date = rs.getString(5);
//            String money = rs.getString(6);
//
//            list.add(new Room(id,lichtrinhFrom,lichtrinhTo,time,phuongtien,date,money));
//        }
//        return list;
//    }


    // lay cac item theo date bat dau + date ket thuc
    // Để so sánh bắt buộc tất cả các trường liên quan đến ngày phải để ở dạng yyyy-MM-dd
//    public List<Room> searchBypriceFromTo(String from, String to) {
//        List<Room> list = new ArrayList<>();
//        String whereClause = "(price >= ?) AND (price <= ?)"; // Dieu kien where
//        String[] whereArgument = {from.trim(), to.trim()}; // Cho ? argument
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor rs = db.query("Room", null, whereClause, whereArgument,
//                null, null, null);
//        while(rs != null && rs.moveToNext()) {
//            int id = rs.getInt(0);
//            String name = rs.getString(1);
//            String tgia = rs.getString(2);
//            String date = rs.getString(3);
//            String nxb=rs.getString(4);
//            String price = rs.getString(5);
//            list.add(new Room(id,name,tgia,date,nxb,price));
//        }
//        return list;
//    }
/*
    // Chuyển dd/MM/yyyy trong ô Search thành yyyy-MM-dd
    public String getFormattedDate(String date) {
        String[] s = date.split("/");
        StringBuilder res = new StringBuilder();
        for (int i = s.length - 1; i >= 0; i--) {
            res.append(s[i]);
            res.append("-");
        }
        res.deleteCharAt(res.length() - 1);
        System.out.println(res.toString());
        return res.toString();
    }
     */
}