package com.cosmetic;

import android.content.Context;
import android.content.Intent;

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
}
