package com.wjp.mypc.base;

import android.app.Activity;
import android.view.View;

/**
 *
 *左侧菜单详情页基类
 * @author wjp
 * @date 2018/02/04 15:44
 */
public abstract class BaseMenuDetailPager {
    public Activity mActivity;
    public View mRootView;//菜单详情页的根布局
    public BaseMenuDetailPager(Activity activity){
        this.mActivity=activity;
        mRootView=initView();
    }

    //初始化布局，必须子类实现
    public abstract View initView();

    //初始化数据
    public void initData(){

    }

}
