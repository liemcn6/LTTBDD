package com.example.sqlitetab.model;

import java.io.Serializable;

public class Tour implements Serializable {
    private int id;
    private String from,to,time,phuongtien,date,money;

    public Tour(String from, String to, String time, String phuongtien, String date, String money) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.phuongtien = phuongtien;
        this.date = date;
        this.money = money;
    }

    public Tour(int id, String from, String to, String time, String phuongtien, String date, String money) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.time = time;
        this.phuongtien = phuongtien;
        this.date = date;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhuongtien() {
        return phuongtien;
    }

    public void setPhuongtien(String phuongtien) {
        this.phuongtien = phuongtien;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
