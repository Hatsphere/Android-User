package com.code.yashladha.android_user.Models;

import java.io.Serializable;

/**
 * Created by yashladha on 19/11/17.
 */

public class Filter implements Serializable {
    private String pName;
    private String sellerId;
    private String planId;

    public Filter(String pName, String sellerId, String planId) {
        this.pName = pName;
        this.sellerId = sellerId;
        this.planId = planId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
