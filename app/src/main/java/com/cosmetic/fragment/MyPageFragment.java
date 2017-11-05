package com.cosmetic.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cosmetic.R;
import com.cosmetic.activity.AlarmActivity;
import com.cosmetic.activity.MyCosmeticActivity;
import com.cosmetic.board.UserInfo;
import com.cosmetic.model.Setting;
import com.cosmetic.view.MyPageHeaderView;
import com.cosmetic.view.MyPageItemView;
import com.dhha22.bindadapter.BindAdapter;
import com.dhha22.bindadapter.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by yujeen on 2017. 10. 28..
 */


public class MyPageFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BindAdapter adapter;

    public static MyPageFragment newInstance() {
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new BindAdapter(getContext())
                .addHeaderView(MyPageHeaderView.class)
                .addLayout(MyPageItemView.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        ButterKnife.bind(this, view);
        adapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.addItem(new Setting(R.drawable.ic_notification, "알림"));
        adapter.addItem(new Setting(R.drawable.ic_logout, "로그아웃"));
        adapter.notifyData();

    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            switch (position) {
                case 1:
                    Toast.makeText(getContext(), "알림입니다", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "로그아웃 입니다", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}
