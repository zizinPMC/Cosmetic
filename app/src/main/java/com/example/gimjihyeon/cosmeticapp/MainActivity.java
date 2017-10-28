package com.example.gimjihyeon.cosmeticapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.dhha22.bindadapter.BindAdapter;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private RecyclerView recyclerView;
    private BindAdapter adapter;
    private TextView mTextMessage;
    private Button button;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BindAdapter(this);
        adapter.addLayout(ListItemView.class);
        recyclerView.setAdapter(adapter);
        adapter.addItem(new Cosmetic("이니스프리"));
        adapter.addItem(new Cosmetic("미샤"));
        adapter.addHeaderView(HomeHeaderView.class);
        adapter.addHeaderItem(new Cosmetic("헤더입니다."));
        adapter.addHeaderView(HomeHeaderView.class);
        adapter.addHeaderItem(new Cosmetic("헤더입니다.2"));
        adapter.notifyData();
    }

}
