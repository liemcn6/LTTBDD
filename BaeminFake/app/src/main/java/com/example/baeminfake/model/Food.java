package com.example.baeminfake.model;

public class Food {

    private int id;
    private int category;
    private String name;
    private double price;
    private String restaurant;
    private int rate;
    private int orders;

    public Food() {
    }

    public Food(int id, int category, String name, double price, String restaurant, int rate, int orders) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        this.rate = rate;
        this.orders = orders;
    }

    public Food(int category, String name, double price, String restaurant, int rate, int orders) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        this.rate = rate;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}
