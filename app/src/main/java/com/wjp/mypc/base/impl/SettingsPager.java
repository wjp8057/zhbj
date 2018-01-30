package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.wjp.mypc.base.BasePager;


public class SettingsPager extends BasePager {

    public SettingsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("设置");
        TextView view=new TextView(mActivity);
        view.setText("设置");
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);

        //更改标题
        tvTitle.setText("设置");
    }
}
