package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.cosmetic.R;
import com.dhha22.bindadapter.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class MyPageHeaderView extends ItemView {
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.userNameTxt)
    TextView userNameTxt;

    public MyPageHeaderView(@NonNull Context context) {
        super(context);
        setContentView(R.layout.my_page_header);
        setFullSpan();
        ButterKnife.bind(this);
    }
}
