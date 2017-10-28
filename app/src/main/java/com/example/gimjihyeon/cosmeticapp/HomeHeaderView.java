package com.example.gimjihyeon.cosmeticapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.dhha22.bindadapter.Item;
import com.dhha22.bindadapter.ItemView;

/**
 * Created by gimjihyeon on 2017. 10. 28..
 */

public class HomeHeaderView extends ItemView {
    private TextView nameTxt;
    public HomeHeaderView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.home_header,this,true);
        setFullSpan();
        nameTxt = findViewById(R.id.nameTxt);
    }

    @Override
    public void setData(Item data) {
        super.setData(data);
        Cosmetic cosmetic = (Cosmetic)data;
        nameTxt.setText(cosmetic.name);

    }
}
