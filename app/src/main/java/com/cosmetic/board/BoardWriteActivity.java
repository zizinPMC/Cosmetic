package com.cosmetic.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cosmetic.R;
import com.cosmetic.db.BoardWriteDB;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 9. 21..
 */

public class BoardWriteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /*@BindView(R.id.txt_nickname)
    TextView nickname;*/

    //@BindView(R.id.cbw_et_category) EditText editCategory;
    @BindView(R.id.cbw_sp_category)
    Spinner spinCategory;
    //@BindView(R.id.cbw_et_writer) EditText editWriter;
    @BindView(R.id.cbw_et_title)
    EditText editTitle;
    @BindView(R.id.cbw_et_contents)
    EditText editContents;
    @BindView(R.id.cbw_bt_submit)
    Button btnSubmit;

    private BoardWriteDB dbManager;
    private ArrayList<String> results;

    String[] category = {"사용팁","보관팁"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        //nickname.setText(UserInfo.userName+"  님");

        ArrayAdapter adapter = new ArrayAdapter(
                getBaseContext()
                ,android.R.layout.simple_spinner_item
                ,category
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapter);



        dbManager = new BoardWriteDB();

        results = new ArrayList<String>();
        //btnSubmit.setOnClickListener(listener);

    }
/*
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.cbw_bt_submit){
                //String sCategory = editCategory.getText().toString();
                String sCategory = spinCategory.getSelectedItem().toString();
                //String sWriter = editWriter.getText().toString();
                String sWriter = UserInfo.userName;
                String sTitle = editTitle.getText().toString();
                String sContents = editContents.getText().toString();
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String sDate =sdfNow.format(date).toString();
                dbManager.comBoardDBManager(sCategory,sWriter, sTitle,sContents,sDate,"",NULL,0);
                //(String boardCategory, String boardWriter, String boardTitle, String boardContent, String boardDate, String boardMdate, int boardHits, int boardGood) {
                //Intent intent = new Intent(ComBoardWriteActivity.this,ComBoardTipActivity.class);
                //startActivity(intent);
            }
        }
    };*/
}
