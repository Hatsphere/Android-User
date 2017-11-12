package com.code.yashladha.android_user.Models;

/**
 * Created by User on 11/10/2017.
 */

public class Product {
    private long pid;
    private String p_name;
    private int price;
    private int discount;
    private boolean on_sale;
    private int image_id;
    private String details;
    private int quantity;


    public Product(long pid, String p_name, int price, int discount, boolean on_sale, int image_id, String details, int quantity) {
        this.pid = pid;
        this.p_name = p_name;
        this.price = price;
        this.discount = discount;
        this.on_sale = on_sale;
        this.image_id = image_id;
        this.details = details;
        this.quantity = quantity;
    }

    public long getPid() {
        return pid;
    }

    public String getP_name() {
        return p_name;
    }

    public int getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isOn_sale() {
        return on_sale;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getDetails() {
        return details;
    }

    public int getQuantity() {
        return quantity;
    }

}
