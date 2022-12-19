package com.example.baeminfake.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.baeminfake.model.Restaurant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SQLiteRestaurantHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABSE_VERSION = 1;

    public SQLiteRestaurantHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String sql = "create table restaurant(" +
                "id integer primary key autoincrement," +
                "name text," +
                "details text," +
                "favorite integer" +
                ")";
        db.execSQL(sql);
    }

    public long addRestaurant(Restaurant o) {
        ContentValues c = new ContentValues();
        c.put("name", o.getName());
        c.put("details", o.getDetails());
        c.put("favorite", o.getFavorite());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("restaurant", null, c);
    }

    public List<Restaurant> getAll() {
        List<Restaurant> list = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor rs = statement.query("restaurant", null,
                null, null,
                null, null, null);
        while ((rs != null) && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String details = rs.getString(2);
            int favorite = rs.getInt(3);
            list.add(new Restaurant(id, name, details, favorite));
        }
        return list;
    }

    public Restaurant getRestaurantById(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("restaurant", null, whereClause,
                whereArgs, null, null, null);
        if (rs.moveToNext()) {
            String name = rs.getString(1);
            String details = rs.getString(2);
            int favorite = rs.getInt(3);
            Restaurant o = new Restaurant(id, name, details, favorite);
            return o;
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
