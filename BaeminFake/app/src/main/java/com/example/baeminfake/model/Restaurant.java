package com.example.baeminfake.model;

public class Restaurant {

    private int id;
    private String name;
    private String details;
    private int favorite;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String details, int favorite) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.favorite = favorite;
    }

    public Restaurant(String name, String details, int favorite) {
        this.name = name;
        this.details = details;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
