package com.wjp.mypc.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjp.mypc.MainActivity;
import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.base.BasePager;
import com.wjp.mypc.base.impl.leftmenu.InteractMenuDetailPager;
import com.wjp.mypc.base.impl.leftmenu.NewsMenuDetailPager;
import com.wjp.mypc.base.impl.leftmenu.PhotosMenuDetailPager;
import com.wjp.mypc.base.impl.leftmenu.TopictMenuDetailPager;
import com.wjp.mypc.domain.NewsMenu;
import com.wjp.mypc.fragment.LeftMenuFragment;
import com.wjp.mypc.utils.CacheUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import static com.wjp.mypc.global.GlobalConstants.CATEGORY_URL;


public class NewsCenterPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> baseMenuDetailPagers;
    private  NewsMenu newsMenu;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
//        System.out.println("新闻中心");
//        TextView view=new TextView(mActivity);
//        view.setText("新闻中心");
//        view.setTextSize(25);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);
//        flContent.addView(view);

        //更改标题
        tvTitle.setText("新闻中心");
        //显示menu图标
        imgBtnMenu.setVisibility(View.VISIBLE);

        /*
        * 判断缓存是否有数据
        * */
        String isCache=CacheUtils.getCache(CATEGORY_URL,mActivity);
        if(!TextUtils.isEmpty(isCache)){
                System.out.println("缓存出现了");
                processData(isCache);
        }
        //为了增强用户体验，不管有没有缓存，我们都要去获取最新的数据
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
            newsMenu=gson.fromJson(result,NewsMenu.class);
            System.out.println("结果:"+newsMenu);

            //写缓存
            CacheUtils.setCache(CATEGORY_URL,result,mActivity);

            //获取侧边栏对象及给侧边栏设置数据
            MainActivity  mainUI=(MainActivity)mActivity;
            FragmentManager fm=mainUI.getSupportFragmentManager();
            LeftMenuFragment leftMenuFragment=(LeftMenuFragment) fm.findFragmentByTag("TAG_LEFT_MENU");
            //给左侧菜单设置数据
            leftMenuFragment.setLeftMenuData(newsMenu.data);

            /*
            * 初始化侧边栏的页面
            * */
            baseMenuDetailPagers=new ArrayList<BaseMenuDetailPager>();
            baseMenuDetailPagers.add(new NewsMenuDetailPager(mActivity));
            baseMenuDetailPagers.add(new TopictMenuDetailPager(mActivity));
            baseMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity));
            baseMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));

            //将侧边栏菜单中的新闻设置为默认页面
            setCurrentDetailPager(0);
    }

    //设置侧边栏菜单详情页
    public void setCurrentDetailPager(int position){
        BaseMenuDetailPager pager=baseMenuDetailPagers.get(position);
        View view=pager.mRootView;

        //清除之前的旧布局
        flContent.removeAllViews();

        flContent.addView(view);

        //初始化页面数据
        pager.initData();

        tvTitle.setText(newsMenu.data.get(position).title);
    }
}
