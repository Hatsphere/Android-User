package com.code.yashladha.android_user.Models;

/**
 * Created by yashladha on 17/11/17.
 * Model class for the trending object
 */

public class TrendingObject {

    private String productName;
    private Long quantity;
    private String sellerId;

    public TrendingObject() {
    }

    public TrendingObject(String productName, Long quantity, String sellerId) {
        this.productName = productName;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
