package com.cosmetic;

import android.content.Context;
import android.content.Intent;

import com.cosmetic.activity.LoginActivity;
import com.cosmetic.activity.MainActivity;
import com.cosmetic.activity.NavigationActivity;
import com.cosmetic.fragment.RegisterFragment;

/**
 * Created by DavidHa on 2017. 11. 3..
 */

public class Navigator {
    public static void goMain(Context context, String userName, String profileUrl){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("userName",userName);
        intent.putExtra("profileUrl", profileUrl);
        context.startActivity(intent);
    }

    public static void goMain(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void goRegister(Context context){
        Intent intent = new Intent(context, NavigationActivity.class);
        NavigationActivity.setFragment(RegisterFragment.newInstance());
        context.startActivity(intent);
    }
    public static void goLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);


    }
}
