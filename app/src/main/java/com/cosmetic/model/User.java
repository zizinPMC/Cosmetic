package com.cosmetic.model;

/**
 * Created by yujeen on 2017. 11. 6..
 */

public class User {
    public String userName; // 사용자 이름
    public String userPhotoUrl; // 사용자 사진 URL
    public String userEmail; // 사용자 이메일주소
    public String userEmailID; // email 주소에서 @ 이전까지의 값.
    public String fcmToken;

    public User(String userName, String userPhotoUrl, String userEmail, String userEmailID, String fcmToken) {
        this.userName = userName;
        this.userPhotoUrl = userPhotoUrl;
        this.userEmail = userEmail;
        this.userEmailID = userEmailID;
        this.fcmToken = fcmToken;
    }


    public String getUserName() {
        return userName;
    }
/*
    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public String getFcmToken() {
        return fcmToken;
    }*/
}
