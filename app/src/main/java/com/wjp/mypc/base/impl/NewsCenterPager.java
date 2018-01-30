package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.wjp.mypc.base.BasePager;


public class NewsCenterPager extends BasePager {

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("新闻中心");
        TextView view=new TextView(mActivity);
        view.setText("新闻中心");
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);
    }
}
