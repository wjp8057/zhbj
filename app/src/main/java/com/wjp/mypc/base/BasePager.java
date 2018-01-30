package com.wjp.mypc.base;


import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjp.mypc.R;

/*
* 五个标签页的基类
* */
public class BasePager {

    public Activity mActivity;
    public TextView tvTitle;
    public ImageButton imgBtnMenu;
    public FrameLayout flContent;
    public View rootView;

    public BasePager(Activity activity){
        this.mActivity=activity;
        rootView=initView();
    }
    //初始化布局
    public View initView(){
        View view=View.inflate(mActivity,R.layout.base_pager,null);
        tvTitle=view.findViewById(R.id.tv_title);
        imgBtnMenu=view.findViewById(R.id.imgbtn_menu);
        flContent=view.findViewById(R.id.fl_content);
        return view;
    }
    //初始化数据
    public  void initData(){

    }
}
