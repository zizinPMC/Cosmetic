package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cosmetic.R;
import com.cosmetic.model.UserSave;
import com.dhha22.bindadapter.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class MyPageHeaderView extends ItemView {
    @BindView(R.id.userProfileImg) CircleImageView userProfileImg;
    @BindView(R.id.userNameTxt) TextView userNameTxt;
    @BindView(R.id.userEmailTxt) TextView userEmailTxt;

    public MyPageHeaderView(@NonNull Context context) {
        super(context);
        setContentView(R.layout.my_page_header);
        setFullSpan();
        ButterKnife.bind(this);

        //userNameTxt.setText(UserSave.getUserName());
        //set imgProfile
        Glide.with(getContext()).load(UserSave.getUserPhotoUrl()).into(userProfileImg);
        //set text
        userNameTxt.setText(UserSave.getUserName());
        userEmailTxt.setText(UserSave.getUserEmail());
    }
}
