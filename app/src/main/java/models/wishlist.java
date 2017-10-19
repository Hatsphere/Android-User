package com.example.yashladha.android_user;

public class wishlist
{
    private long uid;
    private long pid;

    public wishlist(long uid, long pid)
    {
        this.uid = uid;
        this.pid = pid;
    }

    public long getUid()
    {
        return uid;
    }
    public long getPid()
    {
        return pid;
    }

}