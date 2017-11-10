package com.code.yashladha.android_user.Models;

public class Order {
    private long order_id;
    private String status;
    private long pay_id;
    private String uid;
    private String order_date;
    private String del_date;

    public Order(long order_id, String status, long pay_id, String uid, String order_date,String del_date) {
        this.order_id = order_id;
        this.status = status;
        this.pay_id = pay_id;
        this.uid = uid;
        this.order_date = order_date;
        this.del_date = del_date;
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

    public String getDel_date() {
        return del_date;
    }

    public String getOrder_date() {
        return order_date;
    }

}