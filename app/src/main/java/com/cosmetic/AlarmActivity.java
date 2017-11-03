package com.cosmetic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gimjihyeon on 2017. 11. 1..
 */

public class AlarmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
