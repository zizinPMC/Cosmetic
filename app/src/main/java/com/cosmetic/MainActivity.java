package com.cosmetic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cosmetic.adapter.MainAdapter;
import com.cosmetic.board.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation) BottomNavigationView navigation;
    //@BindView(R.id.viewPager) ViewPager viewPager;
    public static ViewPager viewPager;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        adapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
       
        Intent intent = getIntent();
        String userNickname = intent.getExtras().getString("userName");
        String userprofileURL = intent.getExtras().getString("ProfileUrl");
      new UserInfo(userNickname, userprofileURL);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(MainAdapter.HOME, false);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(MainAdapter.REGISTER, false);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(MainAdapter.MY_PAGE, false);

                    return true;
            }
            return false;
        }

    };
}
