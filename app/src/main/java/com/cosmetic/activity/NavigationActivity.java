package com.cosmetic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.cosmetic.R;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class NavigationActivity extends BaseActivity {

    private static Fragment fragment;

    public static Fragment getFragment(){
        return fragment;
    }

    public static void setFragment(Fragment fragment){
        NavigationActivity.fragment = fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(fragment != null){
            setContentView(R.layout.activity_navigation);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
            fragment = null;
        }

    }
}
