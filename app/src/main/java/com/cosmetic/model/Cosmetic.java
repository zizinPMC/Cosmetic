package com.cosmetic.model;

import android.content.Context;

import com.dhha22.bindadapter.Item;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class Cosmetic implements Item {
    public String name;
    public  String cosExpDate;
    public  String cosImgUrl;
    private Context context = null;

    public Cosmetic(String name) {
        this.name = name;
    }
    public Cosmetic(String name,String cosExpDate) {
        this.name = name;
        this.cosExpDate = cosExpDate;

    }
    public Cosmetic() {

    }
    public Cosmetic(String name, String cosExpDate, String cosImgUrl) {
        this.name = name;
        this.cosExpDate = cosExpDate;
        this.cosImgUrl = cosImgUrl;
    }



}
