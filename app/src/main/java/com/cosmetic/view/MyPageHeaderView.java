package com.cosmetic.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cosmetic.R;
import com.cosmetic.model.User;
import com.dhha22.bindadapter.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DavidHa on 2017. 11. 5..
 */

public class MyPageHeaderView extends ItemView {
    @BindView(R.id.userProfileImg) CircleImageView imgProfile;
    @BindView(R.id.userNameTxt) TextView userNameTxt;
    @BindView(R.id.userEmailTxt) TextView userEmailTxt;

    public MyPageHeaderView(@NonNull Context context) {
        super(context);
        setContentView(R.layout.my_page_header);
        setFullSpan();
        ButterKnife.bind(this);

        //set imgProfile
        Glide.with(getContext()).load(User.getUserPhotoUrl()).into(imgProfile);
        //set text
        userNameTxt.setText(User.getUserName());
        userEmailTxt.setText(User.getUserEmail());
    }
}
