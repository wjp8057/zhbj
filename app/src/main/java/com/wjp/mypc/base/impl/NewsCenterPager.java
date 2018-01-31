package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjp.mypc.base.BasePager;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;


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

        //更改标题
        tvTitle.setText("新闻中心");
        //显示menu图标
        imgBtnMenu.setVisibility(View.VISIBLE);
        getDataFromService();
    }

    /*
    * 从服务器获取数据，获取之前需要权限
    * */
    private void   getDataFromService(){
        RequestParams params = new RequestParams("http://10.0.2.2:8080/zhbj/categories.json ");
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("结果:"+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("结果:错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
