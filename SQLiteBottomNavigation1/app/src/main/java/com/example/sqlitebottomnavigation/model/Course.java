package com.example.sqlitebottomnavigation.model;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String ten,date,chuyennganh,fee;
    private boolean kichhoat;

    public Course(int id, String ten, String date, String chuyennganh, String fee, boolean kichhoat) {
        this.id = id;
        this.ten = ten;
        this.date = date;
        this.chuyennganh = chuyennganh;
        this.fee = fee;
        this.kichhoat = kichhoat;
    }

    public Course(String ten, String date, String chuyennganh, String fee, boolean kichhoat) {
        this.ten = ten;
        this.date = date;
        this.chuyennganh = chuyennganh;
        this.fee = fee;
        this.kichhoat = kichhoat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChuyennganh() {
        return chuyennganh;
    }

    public void setChuyennganh(String chuyennganh) {
        this.chuyennganh = chuyennganh;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public boolean isKichhoat() {
        return kichhoat;
    }

    public void setKichhoat(boolean kichhoat) {
        this.kichhoat = kichhoat;
    }
}
