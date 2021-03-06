/*
package com.cosmetic.board;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cosmetic.R;
import com.cosmetic.activity.MainActivity;
import com.cosmetic.adapter.MainAdapter;
import com.cosmetic.db.BoardWriteDB;
import com.cosmetic.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.sql.Types.NULL;

*
 * Created by yujeen on 2017. 9. 21..



public class BoardWriteActivity extends AppCompatActivity {

    @BindView(R.id.cbw_sp_category) Spinner spinCategory;
    //@BindView(R.id.cbw_et_writer) EditText editWriter;
    @BindView(R.id.cbw_et_title) EditText editTitle;
    @BindView(R.id.cbw_et_contents) EditText editContents;
    @BindView(R.id.cbw_bt_submit) Button btnSubmit;

    private BoardWriteDB dbManager;
    private ArrayList<String> results;

    String[] category = {"사용팁","보관팁"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(
                getBaseContext()
                ,android.R.layout.simple_spinner_item
                ,category
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapter);



        dbManager = new BoardWriteDB();

        results = new ArrayList<String>();
        btnSubmit.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.cbw_bt_submit){
                String sCategory = spinCategory.getSelectedItem().toString();
                String sWriter = UserInfo.getUserName();
                String sTitle = editTitle.getText().toString();
                String sContents = editContents.getText().toString();
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String sDate =sdfNow.format(date).toString();
                dbManager.comBoardDBMㅁr.setCurrentItem(MainAdapter.HOME,false);
                MainActivity.viewPager.setCurrentItem(MainAdapter.BOARD,false);
                finish();
            }
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage("글 작성을 종료하시겠습니까?");
        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.show();

    }
}
*/
