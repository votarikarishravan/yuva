package com.example.yuva;

public class BloodDonerModel {
    String name,mobileNo,group;

    public BloodDonerModel() {
    }

    public BloodDonerModel(String name, String   mobileNo, String group) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobNo(String mobilNo) {
        this.mobileNo = mobileNo;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
