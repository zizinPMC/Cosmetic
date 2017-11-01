package com.cosmetic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cosmetic.R;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by yujeen on 2017. 11. 1..
 */

public class AutoScrollAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> data;
    Intent intent;

    private Activity activity;

    public AutoScrollAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager_image,null);
        ImageView image_container = (ImageView) v.findViewById(R.id.pager_image);
        Glide.with(context).load(data.get(position)).into(image_container);
        container.addView(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.etudehouse.com/kr/ko/mobile/planEvt.do?eventcd=1710004"));
                        activity.startActivity(intent);
                        break;
                    case 1 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.etudehouse.com/kr/ko/mobile/planEvt.do?eventcd=1710007"));
                        activity.startActivity(intent);
                        break;
                    case 2 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.etudehouse.com/kr/ko/mobile/planEvt.do?eventcd=1710008"));
                        activity.startActivity(intent);
                        break;
                    case 3 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.etudehouse.com/kr/ko/mobile/beautizen.do?method=recruit"));
                        activity.startActivity(intent);
                        break;
                    case 4 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.etudehouse.com/kr/ko/mobile/planEvt.do?eventcd=1710009"));
                        activity.startActivity(intent);
                        break;
                    default:
                        Toast.makeText(activity.getApplicationContext(), "관련 정보 없음", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
