package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.wjp.mypc.base.BasePager;



public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("首页");
        TextView view=new TextView(mActivity);
        view.setText("首页");
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);
    }
}
