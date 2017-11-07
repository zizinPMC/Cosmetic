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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cosmetic.R;
import com.cosmetic.activity.ChatActivity;
import com.cosmetic.activity.FindShopActivity;
import com.cosmetic.db.DBHelper;
import com.cosmetic.model.Cosmetic;
import com.cosmetic.model.Favorite;
import com.cosmetic.view.CosmeticItemView;
import com.cosmetic.view.HomeFavoriteView;
import com.dhha22.bindadapter.BindAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cosmetic.view.HomeFavoriteView.hfavoriteImg;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.fabChat) FloatingActionButton fabChat;
    @BindView(R.id.homeRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.homeFavoriteImg) ImageView favoriteImg;
    @BindView(R.id.btn_findShop) Button btnShop;
    private BindAdapter adapter;

    ArrayList<HashMap<String, String>> tipList;//하단 리스트뷰 팁리스트
    HomeFavoriteView hfv;

    public static HomeFragment newInstance() {
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
        ButterKnife.bind(this, view);
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

        if(Favorite.favorite!=null){
            hfv = new HomeFavoriteView(Favorite.favorite);
            Glide.with(getContext()).load(hfavoriteImg).into(favoriteImg);
        }

        favoriteImg.setOnClickListener(listener);
        btnShop.setOnClickListener(listener);

        makeDumy();
        fabChat.setOnClickListener(v -> goChatting());
    }

    private void makeDumy() {
        final DBHelper dbHelper = new DBHelper(getContext(), "Cosmetics.db", null, 1);
        adapter.addItem(new Cosmetic(dbHelper.getCosName()));
        adapter.addItem(new Cosmetic(dbHelper.getCosName()));
        adapter.addItem(new Cosmetic(dbHelper.getCosName()));
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.addItem(new Cosmetic());
        adapter.notifyData();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_findShop: {
                    if (Favorite.favorite != null) {
                        /*Intent intent = new Intent(getContext(), FindShopActivity.class);
                        intent.putExtra("brand",Favorite.favorite);
                        startActivity(intent);*/
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
                        String urlStr = "https://m.store.naver.com/places/listMap?sortingOrder=distance&viewType=place&back=false&level=bottom&nlu=%5Bobject%20Object%5D&query="+Favorite.favorite+"&sid=36298052%2C21547444%2C36836471&x=" + longitude +"&y=" + latitude;
                        //%EC%97%90%EB%9B%B0%EB%93%9C
                        Intent intent = new Intent(getContext(), FindShopActivity.class);
                        intent.putExtra("url",urlStr);
                        startActivity(intent);
                        //"https://m.store.naver.com/places/listMap?sortingOrder=distance&viewType=place&back=false&level=bottom&nlu=%5Bobject%20Object%5D&query=%EC%97%90%EB%9B%B0%EB%93%9C&sid=36298052%2C21547444%2C36836471&x=" + longitude +"&y=" + latitude
                        //Toast.makeText(getContext(), "btn_findShop을 눌렀습니다", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "마이페이지에서 관심브랜드를 설정해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.homeFavoriteImg: {
                    if (Favorite.favorite != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(hfv.favoriteWeb));
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "마이페이지에서 관심브랜드를 설정해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    };

    private void goChatting() {
        // 채팅페이지 이동
        Intent intent = new Intent(getContext(), ChatActivity.class);
        startActivity(intent);
    }


}
