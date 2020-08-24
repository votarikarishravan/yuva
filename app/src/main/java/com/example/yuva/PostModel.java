package com.example.yuva;

import android.util.Log;

public class PostModel {
    String userName,userId,title,time,postImage,description,date;
    private static final String TAG = "PostModel";
    public PostModel() {
        Log.d(TAG,"constructor");
    }

    public PostModel(String userName,String userId, String title, String time, String postImage, String description, String date) {
        Log.d(TAG,"param Constructor");
        this.userName = userName;
        this.userId = userId;
        this.title = title;
        this.time = time;
        this.postImage = postImage;
        this.description = description;
        this.date = date;
    }
    public String getUserName(){return userName;}

    public void setUserName(){this.userName = userName;}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
