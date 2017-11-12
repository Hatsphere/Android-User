package com.code.yashladha.android_user.Models;

/**
 * Created by User on 10/25/2017.
 */

public class Product {
    private int pid;
    private String p_name;
    private int price;
    private String class1;
    private int availability;
    private int ratings;
    private int discount;
    private boolean onSale;
    private String details;
    private int image;

    public Product() {
    }

    public int getPid() {
        return pid;
    }

    public String getP_name() {
        return p_name;
    }

    public int getPrice() {
        return price;
    }

    public String getClass1() {
        return class1;
    }

    public int getAvailability() {
        return availability;
    }

    public int getRatings() {
        return ratings;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public String getDetails() {
        return details;
    }

    public int getImage() {
        return image;
    }
}
