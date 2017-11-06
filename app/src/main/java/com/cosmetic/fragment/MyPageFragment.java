package com.cosmetic.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cosmetic.Navigator;
import com.cosmetic.R;
import com.cosmetic.activity.LoginActivity;
import com.cosmetic.model.Favorite;
import com.cosmetic.model.Setting;
import com.cosmetic.view.MyPageHeaderView;
import com.cosmetic.view.MyPageItemView;
import com.dhha22.bindadapter.BindAdapter;
import com.dhha22.bindadapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 10. 28..
 */


public class MyPageFragment extends Fragment {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;


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
        adapter.addItem(new Setting(R.drawable.ic_favorite,"관심브랜드 설정"));
        adapter.addItem(new Setting(R.drawable.ic_logout, "로그아웃"));

        adapter.notifyData();

    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            switch (position) {
                case 1:
                    Navigator.goAlarm(getContext());
                    break;
                case 2:
                    show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                    LoginActivity.logout=1;
                    Navigator.goLogin(getContext());
                    getActivity().finish();
                    break;
            }
        }
    };
    void show()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("에뛰드하우스");
        ListItems.add("이니스프리");
        ListItems.add("토니모리");
        ListItems.add("미샤");
        ListItems.add("더샘");
        ListItems.add("아리따움");
        ListItems.add("올리브영");
        ListItems.add("네이처리퍼블릭");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("관심브랜드 설정");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                new Favorite(selectedText);
                Toast.makeText(getContext(), selectedText, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }



}
