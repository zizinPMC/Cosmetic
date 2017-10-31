package com.cosmetic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by yujeen on 2017. 10. 28..
 */


public class MyPageFragment extends Fragment{
    @BindView(R.id.userProfileImage)
    ImageView userProfileImg;

    public static MyPageFragment newInstance(){
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mypage,container,false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Intent intent = getActivity().getIntent();
         String profileUrl = intent.getExtras().getString("ProfileUrl");
        */Glide.with(getActivity())
                .load("http://k.kakaocdn.net/dn/cuFXl0/btqicSjTW1H/klbg3yc8KoL9eK8MbIY7AK/img_110x110.jpg")
                .error(R.drawable.ic_person)
                .centerCrop()
                .crossFade()
                .override(150,150)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(userProfileImg);
    }
}
