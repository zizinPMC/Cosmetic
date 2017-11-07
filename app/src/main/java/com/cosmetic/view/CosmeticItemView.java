package com.cosmetic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.cosmetic.R;
import com.cosmetic.model.Cosmetic;
import com.dhha22.bindadapter.Item;
import com.dhha22.bindadapter.ItemView;

import java.io.File;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class CosmeticItemView extends ItemView {
    private TextView cosTitle;
    private TextView cosDday;
    //private ImageView cosCateImg;
    private ImageView cosImg;
    String str = "";

    public CosmeticItemView(@NonNull Context context) {
        super(context);
        setContentView(R.layout.cosmetic_item);
        setFullSpan();
        cosImg = (ImageView) findViewById(R.id.cosmeticImg);
        cosTitle = (TextView) findViewById(R.id.cosmeticTitle);
        //cosCateImg = (ImageView) findViewById(R.id.cosCateImg);
        cosDday = (TextView) findViewById(R.id.cosDday);
    }

    @Override
    public void setData(Item data) {
        super.setData(data);
        if(data instanceof Cosmetic){

            /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("------------->error");
                return;
            }*/

            //str=((Cosmetic) data).cosImgUrl;
            //str = str.substring(7);
            File imgFile = new  File(((Cosmetic) data).cosImgUrl);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                cosImg.setImageBitmap(myBitmap);
            }
            else{
                System.out.println("----->imgFile is nonononononono");
            }
            cosTitle.setText(((Cosmetic) data).name);
            cosDday.setText(((Cosmetic) data).cosExpDate);
        }
    }
}
