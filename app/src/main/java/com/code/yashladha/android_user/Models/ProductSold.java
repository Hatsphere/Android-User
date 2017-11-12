package com.code.yashladha.android_user.Models;

/**
 * Created by User on 10/24/2017.
 */

public class ProductSold {

    private int item_id;
    private int order_id;
    private int pid;
    private int image_id;
    private String date;
    private int price;
    private String p_name;

    public ProductSold(int item_id, int order_id, int pid, int image_id, String date, int price, String p_name) {
        this.item_id = item_id;
        this.order_id = order_id;
        this.pid = pid;
        this.image_id = image_id;
        this.date = date;
        this.price = price;
        this.p_name = p_name;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getPid() {
        return pid;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public String getP_name() {
        return p_name;
    }
}
