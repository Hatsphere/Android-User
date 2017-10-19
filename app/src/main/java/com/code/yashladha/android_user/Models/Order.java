package com.code.yashladha.android_user.Models;

public class Order {
    private long order_id;
    private String status;
    private long pay_id;
    private long uid;
    private String order_date;

    public Order(long order_id, String status, long pay_id, long uid, String order_date) {
        this.order_id = order_id;
        this.status = status;
        this.pay_id = pay_id;
        this.uid = uid;
        this.order_date = order_date;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPay_id() {
        return pay_id;
    }

    public void setPay_id(long pay_id) {
        this.pay_id = pay_id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}