package com.example.yuva;

public class UserProfileInfoModel {
    String userName,email,bloodGroup,phoneNumber;

    public UserProfileInfoModel() {
    }

    public UserProfileInfoModel(String userName, String email, String bloodGroup, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
