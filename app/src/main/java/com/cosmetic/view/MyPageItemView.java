package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cosmetic.R;
import com.cosmetic.model.Setting;
import com.dhha22.bindadapter.Item;
import com.dhha22.bindadapter.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class MyPageItemView extends ItemView {
    @BindView(R.id.icon)
    ImageView iconImg;
    @BindView(R.id.text)
    TextView text;
    private Context context;

    public MyPageItemView(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.my_page_item);
        setFullSpan();
        ButterKnife.bind(this);
    }

    @Override
    public void setData(Item data) {
        super.setData(data);
        if (data instanceof Setting) {
            Glide.with(context).load(((Setting) data).icon).into(iconImg);
            text.setText(((Setting) data).name);
        }
    }
}
