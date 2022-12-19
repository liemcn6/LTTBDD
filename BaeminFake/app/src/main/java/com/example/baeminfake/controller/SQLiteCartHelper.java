package com.example.baeminfake.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.baeminfake.model.Cart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SQLiteCartHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABSE_VERSION = 1;

    public SQLiteCartHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String sql = "create table cart(" +
                "id integer primary key autoincrement," +
                "uid text," +
                "payment integer," +
                "soluong integer," +
                "category integer," +
                "name text," +
                "price double," +
                "restaurant text," +
                "rate integer," +
                "orders integer" +
                ")";
        db.execSQL(sql);
    }

    public long addCart(Cart o) {
        ContentValues c = new ContentValues();
        c.put("payment", o.getPayment());
        c.put("uid", o.getUid());
        c.put("soluong", o.getSoluong());
        c.put("category", o.getCategory());
        c.put("name", o.getName());
        c.put("price", o.getPrice());
        c.put("restaurant", o.getRestaurant());
        c.put("rate", o.getRate());
        c.put("orders", o.getOrders());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("cart", null, c);
    }

    public List<Cart> getAllByUid(String uid) {
        String whereClause = "uid like ?";
        String[] whereArgs = {"%" + uid + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("cart", null, whereClause, whereArgs, null, null, null);
        List<Cart> list = new ArrayList<>();
        while ((rs != null) && rs.moveToNext()) {
            int id = rs.getInt(0);
            String uid1 = rs.getString(1);
            int payment = rs.getInt(2);
            int soluong = rs.getInt(3);
            int category = rs.getInt(4);
            String name = rs.getString(5);
            double price = rs.getDouble(6);
            String restaurant = rs.getString(7);
            int rating = rs.getInt(8);
            int orders = rs.getInt(9);
            list.add(new Cart(id, uid1, payment, soluong, category, name, price, restaurant, rating, orders));
        }
        return list;
    }

    public Cart getCartUserById(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("cart", null, whereClause,
                whereArgs, null, null, null);
        if (rs.moveToNext()) {
            String uid1 = rs.getString(1);
            int payment = rs.getInt(2);
            int soluong = rs.getInt(3);
            int category = rs.getInt(4);
            String name = rs.getString(5);
            double price = rs.getDouble(6);
            String restaurant = rs.getString(7);
            int rating = rs.getInt(8);
            int orders = rs.getInt(9);
            Cart o = new Cart(id, uid1, payment, soluong, category, name, price, restaurant, rating, orders);
            return o;
        }
        return null;
    }

    public List<Cart> getCartUserPay(String uid, int checkpay) {
        String whereClause = "uid like ? and payment like ?";
        String[] whereArgs = {"%" + uid + "%", "%" + checkpay + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("cart", null, whereClause, whereArgs, null, null, null);
        List<Cart> carts = new ArrayList<>();
        while (rs.moveToNext()) {
            int id = rs.getInt(0);
            String uid1 = rs.getString(1);
            int payment = rs.getInt(2);
            int soluong = rs.getInt(3);
            int category = rs.getInt(4);
            String oname = rs.getString(5);
            double price = rs.getDouble(6);
            String restaurant = rs.getString(7);
            int rating = rs.getInt(8);
            int orders = rs.getInt(9);
            Cart o = new Cart(id, uid, payment, soluong, category, oname, price, restaurant, rating, orders);
            carts.add(o);
        }
        return carts;
    }

    public int update(Cart o) {
        ContentValues c = new ContentValues();
        c.put("uid", o.getUid());
        c.put("payment", o.getPayment());
        c.put("soluong", o.getSoluong());
        c.put("category", o.getCategory());
        c.put("name", o.getName());
        c.put("price", o.getPrice());
        c.put("restaurant", o.getRestaurant());
        c.put("rate", o.getRate());
        c.put("orders", o.getOrders());
        SQLiteDatabase st = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(o.getId())};
        return st.update("cart", c, whereClause, whereArgs);
    }

    public int delete(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("cart", whereClause, whereArgs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
