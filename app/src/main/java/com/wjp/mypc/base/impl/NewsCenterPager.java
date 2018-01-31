package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjp.mypc.base.BasePager;
import com.wjp.mypc.domain.NewsMenu;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.wjp.mypc.global.GlobalConstants.CATEGORY_URL;


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
        RequestParams params = new RequestParams(CATEGORY_URL);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String> (){
            @Override
            public void onSuccess(String result) {
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //请求失败
                ex.printStackTrace();
                Toast.makeText(mActivity,ex.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*
    * 解析json数据
    * */
    protected void processData(String result){
            Gson gson=new Gson();
            System.out.println("结果:"+gson.fromJson(result,NewsMenu.class));
    }
}
