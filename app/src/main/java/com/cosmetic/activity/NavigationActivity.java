package com.cosmetic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.cosmetic.R;
import com.cosmetic.log.Logger;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.v("on create navigation");
        if(fragment != null){
            setContentView(R.layout.activity_navigation);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
            fragment = null;
        }
    }

}
