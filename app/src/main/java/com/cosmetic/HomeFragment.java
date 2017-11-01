package com.cosmetic;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cosmetic.adapter.AutoScrollAdapter;
import com.cosmetic.db.BoardTipDB;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.fab_store) FloatingActionButton fab;
    @BindView(R.id.home_autoViewPager) AutoScrollViewPager autoViewPager;
    @BindView(R.id.home_listview) ListView listView;
    @BindView(R.id.go_board) Button btnGoBoard;

    ArrayList<HashMap<String,String>> tipList;//하단 리스트뷰 팁리스

    static View v;

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        for(int i=1;i<=5;i++){
            data.add("http://zizin1318.cafe24.com/homeFlipImage/etude"+i+".jpg");
        }
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this.getContext(), data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작
        //하단 게시판 글 뷰
        tipList = new ArrayList<HashMap<String, String>>();
        BoardTipDB.getData("http://zizin1318.cafe24.com/board/board_tip_read.php",tipList,this.getContext(),listView);

        //하단 게시판 더보기버튼 누를경우 게시판뷰로 이동
        btnGoBoard.setOnClickListener(listener);
        fab.setOnClickListener(listener);

    }


    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.go_board :
                    Fragment fragment = BoardFragment.newInstance();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.layout_fragment_home, fragment).addToBackStack(null).commit();
                    break;
                case R.id.fab_store: {
                    Double latitude = 127.027624;
                    Double longitude = 37.497941;
                    LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                    //네트워크를 통해 위치정보를 받아오기위해 기기에서 퍼미션체크를 하도록 아래 코드를 추가해줌
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        System.out.println("------------->error");
                        return;
                    }
                    Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (lastLocation != null) {
                        latitude = lastLocation.getLatitude();
                        longitude = lastLocation.getLongitude();
                    }
                    System.out.println("gps----lat = " + latitude +", lon = "+ longitude);
                    //왜 lat이랑 lon이랑 바뀌어서 저장이 되는거징...
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://m.store.naver.com/places/listMap?sortingOrder=distance" +
                                    "&viewType=place&back=false&level=bottom&nlu=%5Bobject%20Object%5D&query=%EC%97%90%EB%9B%B0%EB%93%9C" +
                                    "&sid=36298052%2C21547444%2C36836471" +
                                    "&x=" + longitude +
                                    "&y=" + latitude));
                    startActivity(intent);
                }break;
                default:
                    break;
            }
        }
    };
}
