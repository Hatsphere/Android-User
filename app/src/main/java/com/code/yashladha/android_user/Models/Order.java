package com.code.yashladha.android_user.Models;

public class Order {
    private long order_id;
    private String status;
    private long pay_id;
    private String uid;
    private String order_date;

    public Order() {
    }

    public Order(long order_id, String status, long pay_id, String uid, String order_date) {
        this.order_id = order_id;
        this.status = status;
        this.pay_id = pay_id;
        this.uid = uid;
        this.order_date = order_date;
    }

    public long getOrder_id() {
        return order_id;
    }

    public String getStatus() {
        return status;
    }

    public long getPay_id() {
        return pay_id;
    }

    public String getUid() {
        return uid;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPay_id(long pay_id) {
        this.pay_id = pay_id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}