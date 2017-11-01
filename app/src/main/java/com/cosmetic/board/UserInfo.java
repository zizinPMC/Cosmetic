package com.cosmetic.board;


public class UserInfo{
    public static String userName;
    //public static String userID;
    public static String profileUrl;

    public UserInfo(String Name,String Url) {
        userName = Name;
        profileUrl = Url;
    }
    public UserInfo(String userName) {
        this.userName = userName;
    }
    public UserInfo() {

    }


    public static String getUserName() {
        return userName;
    }

    public static String getProfileUrl() {
        return profileUrl;
    }
}
