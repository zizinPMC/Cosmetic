package com.cosmetic.model;

/**
 * Created by yujeen on 2017. 11. 6..
 */

public class User {
    public static String userName; // 사용자 이름
    public static String userPhotoUrl; // 사용자 사진 URL
    public static String userEmail; // 사용자 이메일주소
    public static String userEmailID; // email 주소에서 @ 이전까지의 값.
    public String fcmToken;

    public static String getUserName() {
        return userName;
    }

    public static String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static String getUserEmailID() {
        return userEmailID;
    }

    public User(String userName, String userPhotoUrl, String userEmail, String userEmailID, String fcmToken) {
        this.userName = userName;
        this.userPhotoUrl = userPhotoUrl;
        this.userEmail = userEmail;
        this.userEmailID = userEmailID;
        this.fcmToken = fcmToken;
    }



}
