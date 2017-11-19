package com.code.yashladha.android_user.Models;

public class Order {
    private String order_id;
    private String status;
    private String pay_id;
    private String uid;
    private String order_date;
    private String del_date;
    private int total_cost;

    public Order(String order_id, String status, String pay_id, String uid, String order_date,String del_date, int total_cost) {
        this.order_id = order_id;
        this.status = status;
        this.pay_id = pay_id;
        this.uid = uid;
        this.order_date = order_date;
        this.del_date = del_date;
        this.total_cost = total_cost;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getStatus() {
        return status;
    }

    public String getPay_id() {
        return pay_id;
    }

    public String getUid() {
        return uid;
    }

    public String getDel_date() {
        return del_date;
    }

    public String getOrder_date() {
        return order_date;
    }

    public int getTotal_cost() {
        return total_cost;
    }
}