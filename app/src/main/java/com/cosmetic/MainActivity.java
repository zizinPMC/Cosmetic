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
import com.cosmetic.db.UserDB;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private UserDB dbManager;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    //@BindView(R.id.viewPager) ViewPager viewPager;
    public static ViewPager viewPager;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        adapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        dbManager = new UserDB();
        Intent intent = getIntent();

        String userID = intent.getExtras().getString("userID");
        String userNickname = intent.getExtras().getString("userName");
        int userBoardCnt = intent.getExtras().getInt("userBoardCnt");
        int userCosCnt = intent.getExtras().getInt("userCosCnt");
        String userprofileURL = intent.getExtras().getString("ProfileUrl");
        int autoLogin = intent.getExtras().getInt("autoLogin");
        String interestBrand = intent.getExtras().getString("interestBrand");

        dbManager.userDBManager(userID, userNickname, userBoardCnt, userCosCnt, userprofileURL,
                autoLogin, interestBrand);

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
