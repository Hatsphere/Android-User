package com.code.yashladha.android_user.Models;

/**
 * Created by User on 11/7/2017.
 */

public class OrderPlaced {

    private String order_id;
    private String order_date;
    private int total_cost;

    public OrderPlaced(String order_id, String order_date,int total_cost) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.total_cost = total_cost;
    }

    public int getTotal_cost() {
        return total_cost;
    }
    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_date() {
        return order_date;
    }
}
