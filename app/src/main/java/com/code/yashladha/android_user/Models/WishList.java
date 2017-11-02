package com.code.yashladha.android_user.Models;

public class WishList {
    private String uid;
    private long pid;

    public WishList(String uid, long pid) {
        this.uid = uid;
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public long getPid() {
        return pid;
    }

}