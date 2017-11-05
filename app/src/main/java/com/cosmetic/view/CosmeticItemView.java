package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cosmetic.R;
import com.dhha22.bindadapter.ItemView;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class CosmeticItemView extends ItemView {
    public CosmeticItemView(@NonNull Context context) {
        super(context);
        setContentView(R.layout.cosmetic_item);
        setFullSpan();
    }
}
