package com.cosmetic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cosmetic.fragment.HomeFragment;
import com.cosmetic.fragment.MyPageFragment;
import com.cosmetic.fragment.RegisterFragment;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class MainAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 3;
    public static final int HOME = 0;
    public static final int REGISTER = 1;
    public static final int MY_PAGE = 2;
    public MainAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case HOME:
                return HomeFragment.newInstance();
            case REGISTER:
                return RegisterFragment.newInstance();
            case MY_PAGE:
                return MyPageFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
