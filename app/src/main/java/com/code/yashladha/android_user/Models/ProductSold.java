package com.code.yashladha.android_user.Models;

/**
 * Created by Jaya on 10/24/2017.
 */

public class ProductSold {

    private int item_id;

    private int price;
    private String p_name;
    private String status;

    private static final int PRODUCT_IMAGE = -1;
    private int image_id = PRODUCT_IMAGE;

    public ProductSold(int item_id, int image_id, int price, String p_name, String status) {
        this.item_id = item_id;
        this.image_id = image_id;
        this.price = price;
        this.p_name = p_name;
        this.status = status;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public int getPrice() {
        return price;
    }

    public String getP_name() {
        return p_name;
    }

    public String getStatus(){ return status;}

    public boolean hasImage()
    {
        if(image_id!=PRODUCT_IMAGE)
            return true;
        return false;
    }

}
