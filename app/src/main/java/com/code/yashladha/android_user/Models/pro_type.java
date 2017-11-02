package com.code.yashladha.android_user.Models;

/**
 * Created by User on 11/1/2017.
 */

public class pro_type {

    private String type_id;
    private int availability;
    private String p_name;

    public pro_type(String type_id, int availability, String p_name) {
        this.type_id = type_id;
        this.availability = availability;
        this.p_name = p_name;
    }

    public String getType_id() {
        return type_id;
    }

    public int getAvailability() {
        return availability;
    }

    public String getP_name() {
        return p_name;
    }
}
