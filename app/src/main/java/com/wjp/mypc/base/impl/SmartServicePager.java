package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjp.mypc.base.BasePager;


public class SmartServicePager extends BasePager {

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("智慧服务");
        TextView view=new TextView(mActivity);
        view.setText("智慧服务");
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);

        //更改标题
        tvTitle.setText("智慧服务");
        //显示menu图标
        imgBtnMenu.setVisibility(View.VISIBLE);
    }
}
