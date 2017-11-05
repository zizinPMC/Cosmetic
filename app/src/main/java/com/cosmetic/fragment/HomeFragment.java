package com.cosmetic.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cosmetic.R;
import com.cosmetic.adapter.AutoScrollAdapter;
import com.cosmetic.adapter.MainAdapter;
import com.cosmetic.db.BoardTipDB;
import com.cosmetic.model.Cosmetic;
import com.cosmetic.view.CosmeticItemView;
import com.dhha22.bindadapter.BindAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.fabChat) FloatingActionButton fabChat;
    @BindView(R.id.homeRecyclerView) RecyclerView recyclerView;
    private BindAdapter adapter;

    ArrayList<HashMap<String,String>> tipList;//하단 리스트뷰 팁리스트

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new BindAdapter(getContext()).addLayout(CosmeticItemView.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        for(int i=1;i<=5;i++){
            data.add("http://zizin1318.cafe24.com/homeFlipImage/etude"+i+".jpg");
        }
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this.getContext(), data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작*/

        makeDumy();
        fabChat.setOnClickListener(v -> goChatting());
    }

    private void makeDumy(){
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.notifyData();

    }

    private void goChatting(){
        // 채팅페이지 이동
    }


}
