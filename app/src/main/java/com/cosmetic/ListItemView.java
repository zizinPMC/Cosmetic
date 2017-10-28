package com.cosmetic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.dhha22.bindadapter.Item;
import com.dhha22.bindadapter.ItemView;

/**
 * Created by gimjihyeon on 2017. 10. 28..
 */

public class ListItemView extends ItemView {
    private TextView nameTxt;

    public ListItemView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.list_item, this, true);
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
