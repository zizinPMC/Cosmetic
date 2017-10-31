package com.cosmetic.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cosmetic.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yujeen on 2017. 9. 14..
 */
public class MyViewPagerAdapter extends PagerAdapter {

    Bitmap bmImg;
    private final int imgCount = 5;

    LayoutInflater inflater;

    int num = 0;
    public MyViewPagerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return imgCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;

        view = inflater.inflate(R.layout.viewpager_image, null);

        ImageView img = (ImageView)view.findViewById(R.id.pager_image);
        ImageLoad imageLoad = new ImageLoad();

        num = position + 1;
        //System.out.println("position = "+position +", num = "+num);
        System.out.println("viewpageradapter---http://zizin1318.cafe24.com/homeFlipImage/etude"+num+".jpg");
        imageLoad.execute("http://zizin1318.cafe24.com/homeFlipImage/etude"+num+".jpg");
        img.setImageBitmap(bmImg);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }
    private class ImageLoad extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... url) {
            try{
                URL myFileUrl = new URL(url[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);
                System.out.println("image url "+url[0]+"  ok!");

            }catch(IOException e){
                e.printStackTrace();
            }

            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //super.onPostExecute(bitmap);
            //img.setImageBitmap(bmImg);
            //viewFlipper.addView(img);
            System.out.println("ImageLoad onPostExecute ok!");

        }
    }

}
