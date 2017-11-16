package com.code.yashladha.android_user.Models;

/**
 * Created by User on 11/7/2017.
 */

public class OrderPlaced {

    private long order_id;
    private String order_date;

    public OrderPlaced(long order_id, String order_date) {
        this.order_id = order_id;
        this.order_date = order_date;
    }

    public long getOrder_id() {
        return order_id;
    }

    public String getOrder_date() {
        return order_date;
    }
}
