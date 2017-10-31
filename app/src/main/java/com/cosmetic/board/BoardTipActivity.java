package com.cosmetic.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cosmetic.R;
import com.cosmetic.db.BoardTipDB;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 9. 21..
 */

public class BoardTipActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.txt_nickname) TextView nickname;
    @BindView(R.id.cbt_listview) ListView listView;
    @BindView(R.id.cbt_btn_write) Button cbtBtnWrite;

    ArrayList<HashMap<String,String>> tipList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_tip);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        //nickname.setText(UserInfo.userName + "  님");

        //cbtBtnWrite.setOnClickListener(listener);
        tipList = new ArrayList<HashMap<String, String>>();
        BoardTipDB.getData("http://zizin1318.cafe24.com/board/board_tip_read.php", tipList, getApplicationContext(), listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), tipList.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
/*
    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cbt_btn_write){
                *//*Intent intent = new Intent(
                        ComBoardTipActivity.this, // 현재 화면의 제어권자
                        ComBoardWriteActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다*//*
            }
        }
    };*/



}
