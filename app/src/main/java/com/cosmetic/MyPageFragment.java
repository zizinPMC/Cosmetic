package com.cosmetic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cosmetic.board.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by yujeen on 2017. 10. 28..
 */


public class MyPageFragment extends Fragment {
    @BindView(R.id.userProfileImage)
    ImageView userProfileImg;
    @BindView(R.id.linear_alarm)
    LinearLayout linear_alarm;
    @BindView(R.id.linear_notice)
    LinearLayout linear_notice;
    @BindView(R.id.linear_mycosmetic)
    LinearLayout linear_mycosmetic;
    @BindView(R.id.userName)
    TextView userName;

    private Activity activity;

    public static MyPageFragment newInstance() {
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String userprofileurl = UserInfo.profileUrl;
        String usernickname = UserInfo.userName;

        Glide.with(getActivity())
                .load(userprofileurl)
                .error(R.drawable.ic_person)
                .centerCrop()
                .crossFade()
                .override(150, 150)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(userProfileImg);

        userName.setText(usernickname);
        //마이페이지 -알람
        linear_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AlarmActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        //마이페이지 -공지사항
        linear_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //마이페이지 -내 등록화장품 목록
        linear_mycosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MyCosmeticActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }
}