package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wjp.mypc.base.BasePager;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
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
//        BaiduParams params=new BaiduParams();
        RequestParams params = new RequestParams("http://localhost:8080/zhbj/categories.json");
//        params.addQueryStringParameter("username","abc");
//        params.addQueryStringParameter("password","123");
        Callback.Cancelable cancelable= x.http().get(params,
                new Callback.CacheCallback<String>() {
                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                            System.out.println("其他错误");
                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onSuccess(String result) {
                        System.out.println("结果:"+result);
                    }
                });
    }
}
