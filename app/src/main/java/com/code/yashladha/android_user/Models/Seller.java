package com.code.yashladha.android_user.Models;

/**
 * Created by yashladha on 14/11/17.
 * Database Model for the Seller
 */

public class Seller {

    private String Address;
    private String ContactNo;
    private String Name;
    private String PlanChosen;
    private String profileImage;

    public Seller(String address, String contactNo, String name, String planChosen, String profileImage) {
        Address = address;
        ContactNo = contactNo;
        Name = name;
        PlanChosen = planChosen;
        this.profileImage = profileImage;
    }

    public Seller() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlanChosen() {
        return PlanChosen;
    }

    public void setPlanChosen(String planChosen) {
        PlanChosen = planChosen;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
