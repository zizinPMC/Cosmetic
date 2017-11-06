package com.cosmetic.model;

/**
 * Created by yujeen on 2017. 11. 6..
 */

public class UserSave {
    public static String userName; // 사용자 이름
    public static String userPhotoUrl; // 사용자 사진 URL
    public static String userEmail; // 사용자 이메일주소
    public static String userEmailID; // email 주소에서 @ 이전까지의 값.
    public static String fcmToken;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserSave.userName = userName;
    }

    public static String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public static void setUserPhotoUrl(String userPhotoUrl) {
        UserSave.userPhotoUrl = userPhotoUrl;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        UserSave.userEmail = userEmail;
    }

    public static String getUserEmailID() {
        return userEmailID;
    }

    public static void setUserEmailID(String userEmailID) {
        UserSave.userEmailID = userEmailID;
    }

    public static String getFcmToken() {
        return fcmToken;
    }

    public static void setFcmToken(String fcmToken) {
        UserSave.fcmToken = fcmToken;
    }
}
