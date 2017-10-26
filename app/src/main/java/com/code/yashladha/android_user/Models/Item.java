package com.code.yashladha.android_user.Models;

public class Item {
    private long item_id;
    private String order_date;
    private String uid;
    private long pid;
    private long order_id;

    public Item(long item_id, String order_date, String uid, long pid, long order_id) {
        this.item_id = item_id;
        this.order_date = order_date;
        this.uid = uid;
        this.pid = pid;
        this.order_id = order_id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
}