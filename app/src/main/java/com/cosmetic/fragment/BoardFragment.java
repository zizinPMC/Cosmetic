package com.cosmetic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cosmetic.R;
import com.cosmetic.activity.MainActivity;
import com.cosmetic.adapter.MainAdapter;
import com.cosmetic.board.BoardWriteActivity;
import com.cosmetic.db.BoardTipDB;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class BoardFragment extends Fragment {

    @BindView(R.id.btn_boardWrite) Button btn_write;
    @BindView(R.id.cbt_listview) ListView listView;
    ArrayList<HashMap<String,String>> tipList;
    public static BoardFragment newInstance() {
        BoardFragment fragment = new BoardFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_board, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeFragment.fab.setVisibility(View.INVISIBLE);
        tipList = new ArrayList<HashMap<String, String>>();
        BoardTipDB.getData("http://zizin1318.cafe24.com/board/board_tip_read.php", tipList, getContext(), listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), tipList.get(position).toString(), Toast.LENGTH_LONG).show();
                MainActivity.viewPager.setCurrentItem(MainAdapter.READ,false);
            }
        });

        btn_write.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_boardWrite){
                Intent intent = new Intent(
                        getContext(), // 현재 화면의 제어권자
                        BoardWriteActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent);

            }
        }
    };


}
