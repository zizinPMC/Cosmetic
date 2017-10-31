package com.cosmetic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cosmetic.db.BoardTipDB;
import com.cosmetic.view.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.fab_store) FloatingActionButton fab;
    @BindView(R.id.home_viewPager) ViewPager viewPager;
    //@BindView(R.id.pager_image) ImageView pagerImage;
    @BindView(R.id.home_listview) ListView listView;
    @BindView(R.id.go_board) Button btnGoBoard;

    ArrayList<HashMap<String,String>> tipList;//하단 리스트뷰 팁리스
    Bitmap bmImg;

    LayoutInflater layoutInflater;
    Thread thread = null;
    Handler handler = null;
    int p = 1;//페이지번호

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        layoutInflater = inflater;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(layoutInflater);
        viewPager.setAdapter(adapter);

        startHandler();

        //하단 게시판 글 뷰
        tipList = new ArrayList<HashMap<String, String>>();
        BoardTipDB.getData("http://zizin1318.cafe24.com/board/board_tip_read.php",tipList,this.getContext(),listView);

        //하단 게시판 더보기버튼 누를경우 게시판뷰로 이동
        btnGoBoard.setOnClickListener(listener);

    }
    public void startHandler(){
        handler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(p==0 ){
                    //viewPager.setCurrentItem(0,false);
                    viewPager.setCurrentItem(0);
                    p++;
                }else if(p==1){
                    //viewPager.setCurrentItem(0,false);
                    viewPager.setCurrentItem(1);
                    p++;
                }else if(p==2){
                    //viewPager.setCurrentItem(0,false);
                    viewPager.setCurrentItem(2);
                    p++;
                }else if(p==3){
                    //viewPager.setCurrentItem(0,false);
                    viewPager.setCurrentItem(3);
                    p++;
                }else if(p==4){
                    //viewPager.setCurrentItem(0,false);
                    viewPager.setCurrentItem(4);
                    p++;
                }else if(p==5){
                    viewPager.setCurrentItem(0,false);
                    //viewPager.setCurrentItem(0);
                    p=0;
                }

            }
        };

        thread = new Thread(){
            //run은 jvm이 쓰레드를 채택하면 해당 쓰레드의 run메서드를 수행한다.
            public void run(){
                super.run();
                while(true){
                    try{
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(0);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

        };
        thread.start();

    }
    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.go_board :
                    Toast.makeText(getContext(), "button go board click", Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = BoardFragment.newInstance();
                    ft.add(R.id.layout_fragment_home,fragment);
                    ft.commit();

                    break;
                case R.id.fab_store:

                    break;
                default:
                    break;
            }
        }
    };
}
