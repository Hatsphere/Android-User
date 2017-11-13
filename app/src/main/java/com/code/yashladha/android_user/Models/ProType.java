package com.code.yashladha.android_user.Models;

/**
 * Created by User on 11/1/2017.
 */

public class ProType {

    private String TypeId;
    private int availability;
    private String productName;

    public ProType(String TypeId, int availability, String p_name) {
        this.TypeId = TypeId;
        this.availability = availability;
        this.productName = p_name;
    }

    public String getTypeId() {
        return TypeId;
    }

    public int getAvailability() {
        return availability;
    }

    public String getProductName() {
        return productName;
    }
}
