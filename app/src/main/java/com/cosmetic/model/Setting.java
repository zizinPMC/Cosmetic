package com.cosmetic.model;

import com.dhha22.bindadapter.Item;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class Setting implements Item {
    public int icon;
    public String name;

    public Setting(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }
}
