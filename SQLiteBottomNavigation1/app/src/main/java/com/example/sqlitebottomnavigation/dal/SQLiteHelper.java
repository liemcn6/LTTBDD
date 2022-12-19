package com.example.sqlitebottomnavigation.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitebottomnavigation.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Sach.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE book(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "author TEXT," +
                "date TEXT," +
                "producer TEXT," +
                "price TEXT)";
               // "isCooperated INT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // get all + order by date descending ASC tang, DESC giam
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "price DESC";
        Cursor rs = st.query("book", null, null,
                null, null, null, order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String author = rs.getString(2);
            String date = rs.getString(3);
            String producer = rs.getString(4);
            String price = rs.getString(5);
      //      String pric=String.valueOf(rs.getInt(6));
//            int coop = rs.getInt(5);
//            boolean isCooperated;
//            if (coop == 0) {
//                isCooperated = false;
//            } else {
//                isCooperated = true;
//            }
            list.add(new Book(id,title,author,date,producer,price));
        }
        return list;
    }

    // add
    public long addBook(Book i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("author", i.getAuthor());
        values.put("date", i.getDate());
        values.put("producer", i.getProducer());
        values.put("price",i.getPrice());

//        if (i.isCooperated()) {
//            values.put("isCooperated", 1);
//        } else {
//            values.put("isCooperated", 0);
//        }
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("book", null, values);
    }

    // lay cac item theo date
    public List<Book> getByDate(String date) {
        List<Book> list = new ArrayList<>();
        String whereClause = "date like ?"; // Dieu kien where
        String[] whereArgument = {date}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("book", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String author = rs.getString(2);
            //String date = rs.getString(3);
            String producer = rs.getString(4);
            String price = rs.getString(5);
//            int coop = rs.getInt(5);
//            boolean isCooperated;
//            if (coop == 0) {
//                isCooperated = false;
//            } else {
//                isCooperated = true;
//            }
            list.add(new Book(id,title,author,date,producer,price));
        }
        return list;
    }

    // update
    public int updateBook(Book i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("author", i.getAuthor());
        values.put("date", i.getDate());
        values.put("producer", i.getProducer());
        values.put("price",i.getPrice());

//        if (i.isCooperated()) {
//            values.put("isCooperated", 1);
//        } else {
//            values.put("isCooperated", 0);
//        }
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return db.update("book", values, whereClause, whereArgs);
    }

    // delete
    public int delete(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("book", whereClause, whereArgs);
    }
    public List<Book> getByTitle(String key){
        List<Book> list =new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        String WHERE_Clause="title like ?";
        String[] WHERE_args={"%"+key+"%"};
        Cursor cursor=database.query("book",null,WHERE_Clause,WHERE_args,null,null,null);
        while ((cursor!=null)&&cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String date=cursor.getString(3);
            String author=cursor.getString(2);
            String producer=cursor.getString(4);
            String price = cursor.getString(5);

            list.add(new Book(id,title,author,date,producer,price));
        }
        return list;
    }
    public List<Book> getByCategory(String category){
        List<Book> list =new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        String WHERE_Clause="producer like ?";
        String[] WHERE_args={category};
        Cursor cursor=database.query("book",null,WHERE_Clause,WHERE_args,null,null,null);
        while ((cursor!=null)&&cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String date=cursor.getString(3);
            String author=cursor.getString(2);

            String price = cursor.getString(5);

            list.add(new Book(id,title,author,date,category,price));
        }
        return list;
    }
    public List<Book> getByDatefromTo(String from,String to){
        List<Book> list =new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        String WHERE_Clause="date BETWEEN ? AND ?";
        String[] WHERE_args={from.trim(),to.trim()};
        Cursor cursor=database.query("book",null,WHERE_Clause,WHERE_args,null,null,null);
        while ((cursor!=null)&&cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String date=cursor.getString(3);
            String author=cursor.getString(2);
            String producer=cursor.getString(4);
            String price = cursor.getString(5);

            list.add(new Book(id,title,author,date,producer,price));
        }
        return list;
    }

    public List<Book> getByPricefromTo(String from,String to){
        List<Book> list =new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        String whereClause = "(price >= ?) AND (price <= ?)";
        String orderClause=" price DESC";
        String[] WHERE_args={from.trim(),to.trim()};
        Cursor cursor=database.query("book",null,whereClause,WHERE_args,null,null,orderClause);
        while ((cursor!=null) && cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String date=cursor.getString(3);
            String author=cursor.getString(2);
            String producer=cursor.getString(4);
            String price = cursor.getString(5);

            list.add(new Book(id,title,author,date,producer,price));
        }
        return list;
    }

    // lay cac item theo title
    /*
    public List<Book> searchByTitle(String key) {
        List<Book> list = new ArrayList<>();
        String whereClause = "title like ?"; // Dieu kien where
        String[] whereArgument = {"%"+key+"%"}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("Book", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Book(id, title, content, date, status, isCooperated));
        }
        return list;
    }

    // lay cac item theo status
    public List<Book> searchByCategory(String status) {
        List<Book> list = new ArrayList<>();
        String whereClause = "status like ?"; // Dieu kien where
        String[] whereArgument = {status}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("Book", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Book(id, title, content, date, status, isCooperated));
        }
        return list;
    }
*/
    /*
    // lay cac item theo date bat dau + date ket thuc
    // Để so sánh bắt buộc tất cả các trường liên quan đến ngày phải để ở dạng yyyy-MM-dd
    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN (?) AND (?)"; // Dieu kien where
        String[] whereArgument = {getFormattedDate(from.trim()), getFormattedDate(to.trim())}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("items", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

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
    public Item getItemById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
        null, whereClause, whereArgs,
                null, null, null);
        if (rs != null && rs.moveToFirst()) {
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            rs.close();
            return new Item(id,title,category,price,date);
         }
        return null;
    }
     */
}
