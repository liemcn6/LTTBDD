package com.example.serviceorientedsoftware.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("createAt")
    private String create_at;

    @SerializedName("status")
    private String state;

    @SerializedName("totalPrice")
    private int total_price;

    @SerializedName("userId")
    private String user_id;

    @SerializedName("orderProducts")
    private List<OrderPet> list;

    public Order() {
    }

    public Order(int total_price, String user_id, List<OrderPet> list) {
        this.total_price = total_price;
        this.user_id = user_id;
        this.list = list;
    }

    public Order(String id, String create_at, String state, int total_price, String user_id, List<OrderPet> list) {
        this.id = id;
        this.create_at = create_at;
        this.state = state;
        this.total_price = total_price;
        this.user_id = user_id;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getState() {
        return state;
    }

    public int getTotal_price() {
        return total_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public List<OrderPet> getList() {
        return list;
    }
}
