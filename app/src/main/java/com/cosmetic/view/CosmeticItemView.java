package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.cosmetic.R;
import com.cosmetic.model.Cosmetic;
import com.dhha22.bindadapter.Item;
import com.dhha22.bindadapter.ItemView;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class CosmeticItemView extends ItemView {
    private TextView cosTitle;
    private TextView cosDday;
    //private ImageView cosCateImg;
    private ImageView cosImg;

    public CosmeticItemView(@NonNull Context context) {
        super(context);
        setContentView(R.layout.cosmetic_item);
        setFullSpan();
        cosImg = (ImageView) findViewById(R.id.cosmeticImg);
        cosTitle = (TextView) findViewById(R.id.cosmeticTitle);
      //  cosCateImg = (ImageView) findViewById(R.id.cosCateImg);
        cosDday = (TextView) findViewById(R.id.cosmeticDday);
    }

    @Override
    public void setData(Item data) {
        super.setData(data);
        if(data instanceof Cosmetic){
            //cosImg.setImageURI(Uri.parse(((Cosmetic) data).cosImgUrl));
            //Toast.makeText(getContext(),((Cosmetic) data).name,Toast.LENGTH_LONG).show();
            cosTitle.setText(((Cosmetic) data).name);
//            cosDday.setText(((Cosmetic) data).cosExpDate);
        }
    }
}
