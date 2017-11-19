package com.code.yashladha.android_user.Models;

import java.util.ArrayList;

/**
 * Created by yashladha on 19/11/17.
 */

public class Logs {
    private String timeStamp;
    private int quantity;
    private int amount;
    private ArrayList<String> items;
    private ArrayList<String> sellers;

    public Logs() {
    }

    public Logs(String timeStamp, int quantity, int amount) {
        this.timeStamp = timeStamp;
        this.quantity = quantity;
        this.amount = amount;
        this.items = new ArrayList<>();
        this.sellers = new ArrayList<>();
    }

    public Logs(String timeStamp, int quantity) {
        this.timeStamp = timeStamp;
        this.quantity = quantity;
        this.amount = 0;
        this.items = new ArrayList<>();
        this.sellers = new ArrayList<>();
    }

    public void addSeller(String sellerName) {
        this.sellers.add(sellerName);
    }

    public void addItem(String item) {
        this.items.add(item);
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
