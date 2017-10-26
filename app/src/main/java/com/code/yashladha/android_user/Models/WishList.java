package com.code.yashladha.android_user.Models;

public class WishList
{
    private String uid;
    private long pid;

    public WishList(String uid, long pid) {
        this.uid = uid;
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}