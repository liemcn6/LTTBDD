package com.example.sqlitebottomnavigation.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title,author,date,producer,price;

    public Book() {
    }

    public Book(int id, String title, String author, String date, String producer, String price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.producer = producer;
        this.price = price;
    }

    public Book(String title, String author, String date, String producer, String price) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.producer = producer;
        this.price = price;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
