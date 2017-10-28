package com.cosmetic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yujeen on 2017. 10. 28..
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.fab_store) FloatingActionButton fab;
    @BindView(R.id.home_viewFlipper) ViewFlipper viewFlipper;
    @BindView(R.id.flipper_image) ImageView flipImage;
    @BindView(R.id.home_listview) ListView listView;
    @BindView(R.id.go_board) Button btnGoBoard;

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoBoard.setOnClickListener(listener);

    }


    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.go_board :
                    Toast.makeText(getContext(), "button go board click", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
