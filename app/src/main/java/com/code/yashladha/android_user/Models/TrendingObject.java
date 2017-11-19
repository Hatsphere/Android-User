package com.code.yashladha.android_user.Models;

/**
 * Created by yashladha on 17/11/17.
 * Model class for the trending object
 */

public class TrendingObject {

   private String productName;
   private Long soldItems;
   private String sellerId;

    public TrendingObject() {
    }

    public TrendingObject(String productName, Long soldItems, String sellerId) {
        this.productName = productName;
        this.soldItems = soldItems;
        this.sellerId = sellerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(Long soldItems) {
        this.soldItems = soldItems;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
