package com.cosmetic.model;


public class UserInfo{
    private static String userID;
    private static String userName;
    private static String userProfile;
    private static int userBoardCnt;
    private static int userCosCnt;
    private static int userAutoLogin;
    private static String userInterestBrand;



    public UserInfo(String userID, String userName, String userProfile, int userBoardCnt, int userCosCnt, int userAutoLogin, String userInterestBrand) {
        this.userID = userID;
        this.userName = userName;
        this.userProfile = userProfile;
        this.userBoardCnt = userBoardCnt;
        this.userCosCnt = userCosCnt;
        this.userCosCnt = userCosCnt;
        this.userAutoLogin = userAutoLogin;
        this.userInterestBrand = userInterestBrand;
    }

    public static String getUserID() {
        return userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserProfile() {
        return userProfile;
    }

    public static int getUserBoardCnt() {
        return userBoardCnt;
    }

    public static int getUserCosCnt() {
        return userCosCnt;
    }

    public static int getUserAutoLogin() {
        return userAutoLogin;
    }

    public static String getUserInterestBrand() {
        return userInterestBrand;
    }
}
