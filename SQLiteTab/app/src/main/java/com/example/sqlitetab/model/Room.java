package com.example.sqlitetab.model;

import java.io.Serializable;

public class Room implements Serializable {
    private int id;
    private String diachi,dichvu,mota,dientich,gia,maxPeople,sdt;

    public Room(String diachi, String dichvu, String mota, String dientich, String gia, String maxPeople, String sdt) {
        this.diachi = diachi;
        this.dichvu = dichvu;
        this.mota = mota;
        this.dientich = dientich;
        this.gia = gia;
        this.maxPeople = maxPeople;
        this.sdt = sdt;
    }

    public Room(int id, String diachi, String dichvu, String mota, String dientich, String gia, String maxPeople, String sdt) {
        this.id = id;
        this.diachi = diachi;
        this.dichvu = dichvu;
        this.mota = mota;
        this.dientich = dientich;
        this.gia = gia;
        this.maxPeople = maxPeople;
        this.sdt = sdt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDichvu() {
        return dichvu;
    }

    public void setDichvu(String dichvu) {
        this.dichvu = dichvu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(String maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
