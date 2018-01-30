package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.wjp.mypc.base.BasePager;


public class GovAffarisPager extends BasePager {

    public GovAffarisPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("政务");
        TextView view=new TextView(mActivity);
        view.setText("政务");
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);

        //更改标题
        tvTitle.setText("政务中心");
    }
}
