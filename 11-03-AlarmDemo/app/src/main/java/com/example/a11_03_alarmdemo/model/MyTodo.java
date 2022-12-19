package com.example.a11_03_alarmdemo.model;

import java.io.Serializable;

public class MyTodo implements Serializable {
    private int id;
    private String title;
    private String date;
    private String describe;

    public MyTodo() {
    }

    public MyTodo(String title, String date, String describe) {
        this.title = title;
        this.date = date;
        this.describe = describe;
    }

    public MyTodo(int id, String title, String date, String describe) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
