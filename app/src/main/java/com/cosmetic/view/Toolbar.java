package com.cosmetic.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cosmetic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gimjihyeon on 2017. 11. 5..
 */

public class Toolbar extends FrameLayout {
    @BindView(R.id.backBtn)
    ImageView backBtn;

    public Toolbar(@NonNull Context context) {
        this(context, null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);
        ButterKnife.bind(this);
        backBtn.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("이 창을 나가시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("네", (dialog, id) -> ((Activity) context).onBackPressed())
                    .setNegativeButton("머무르겠습니다", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });

    }
}
