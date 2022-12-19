package com.example.prudentialfinance.Container;

import com.example.prudentialfinance.Model.CategoryMonthly;
import com.example.prudentialfinance.Model.Summary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryMonthlyResponse {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("summary")
    @Expose
    private Summary summary;

    @SerializedName("msg")
    @Expose
    private  String msg;

    @SerializedName("data")
    @Expose
    private ArrayList<CategoryMonthly> data;

    @SerializedName("method")
    @Expose
    private String method;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<CategoryMonthly> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryMonthly> data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "CategoryMonthlyResponse{" +
                "result=" + result +
                ", summary=" + summary +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", method='" + method + '\'' +
                '}';
    }
}
