package com.example.sqlitebottomnavigation.model;

import java.io.Serializable;

public class Khoahoc implements Serializable {
    private int id,active;
    private String name, date , chuyenNganh;

    public Khoahoc() {
    }

    public Khoahoc(int id, int active, String name, String date, String chuyenNganh) {
        this.id = id;
        this.active = active;
        this.name = name;
        this.date = date;
        this.chuyenNganh = chuyenNganh;
    }

    public Khoahoc(int active, String name, String date, String chuyenNganh) {
        this.active = active;
        this.name = name;
        this.date = date;
        this.chuyenNganh = chuyenNganh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }
}
