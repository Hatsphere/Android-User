package com.code.yashladha.android_user.Models;

public class WishList
{
    private long uid;
    private long pid;

    public WishList(long uid, long pid)
    {
        this.uid = uid;
        this.pid = pid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}