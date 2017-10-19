package com.code.yashladha.android_user.Models;

public class User {

    private long contact;
    private String email;
    private String name;
    private String address;


    public User(long contact, String email, String name, String address)
    {
        this.contact = contact;
        this.email = email;
        this.name = name;
        this.address = address;

    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
