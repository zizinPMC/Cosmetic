package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
            }

            System.out.println("-------->imgFile.....first..."+((Cosmetic) data).cosImgUrl);
            File imgFile = new  File(((Cosmetic) data).cosImgUrl);

            System.out.println("-------->imgFile.....second..."+imgFile.getAbsolutePath());
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                cosImg.setImageBitmap(myBitmap);
            }
            else{
                System.out.println("----->imgFile is nonononononono");
            }*/
            /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("------------->error");
                return;
            }

            //System.out.println("-------->imgFile.....first..."+((Cosmetic) data).cosImgUrl);

            //Bitmap myBitmap = BitmapFactory.decodeFile(((Cosmetic) data).cosImgUrl);

            //cosImg.setImageBitmap(myBitmap);
            System.out.println("-------->img cosImgUrl..."+((Cosmetic) data).cosImgUrl);
            Glide.with(getContext()).load(((Cosmetic) data).cosImgUrl.substring(7)).into(cosImg);
*/

            Glide.with(getContext()).load(((Cosmetic) data).cosImgUrl).into(cosImg);
            cosTitle.setText(((Cosmetic) data).name);
            cosDday.setText(((Cosmetic) data).cosExpDate);
        }
    }
}
