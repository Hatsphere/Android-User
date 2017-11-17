package com.code.yashladha.android_user.Models;

import java.io.Serializable;

/**
 * Created by yashladha on 12/11/17.
 * Name : Name of product
 * Sale: Flag for sale
 * Description : Description of the product
 * Price : Price of the product
 * Availability : Flag for availability of product
 * ProductClass : Class of Product
 * primaryImage : Primary Image to be shown (url)
 * leftImage : Left Image url
 * rightImage : Right Image url
 */

public class Product implements Serializable {
    private String sellerId;
    private String Name;
    private boolean Sale;
    private String Description;
    private int Price;
    private boolean Availability;
    private String ProductClass;
    private String primaryImage;
    private String leftImage;
    private String rightImage;

    public Product() {
    }

    public Product(String sellerId, String aProduct, boolean sale, String description, int price, boolean availability, String aClass, String primaryImage, String leftImage, String rightImage) {
        this.sellerId = sellerId;
        this.Name = aProduct;
        Sale = sale;
        Description = description;
        Price = price;
        Availability = availability;
        ProductClass = aClass;
        this.primaryImage = primaryImage;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSale() {
        return Sale;
    }

    public void setSale(boolean sale) {
        Sale = sale;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public boolean isAvailability() {
        return Availability;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    public String getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(String leftImage) {
        this.leftImage = leftImage;
    }

    public String getRightImage() {
        return rightImage;
    }

    public void setRightImage(String rightImage) {
        this.rightImage = rightImage;
    }
}
