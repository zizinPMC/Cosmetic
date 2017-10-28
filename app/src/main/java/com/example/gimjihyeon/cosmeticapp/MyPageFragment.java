package com.example.gimjihyeon.cosmeticapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class MyPageFragment extends Fragment{
    public static MyPageFragment newInstance(){
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mypage,container,false);
        return view;
    }
}
