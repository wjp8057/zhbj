package com.wjp.mypc;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.wjp.mypc.fragment.ContentFragment;
import com.wjp.mypc.fragment.LeftMenuFragment;


public class MainActivity extends SlidingFragmentActivity {

    private final String TAG_LEFT_MENU="TAG_LEFT_MENU";
    private final String TAG_CONTENT="TAG_CONTENT";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Utils.testLib();
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);


        SlidingMenu mneu=getSlidingMenu();
        getSlidingMenu().setMode(SlidingMenu.LEFT);
        mneu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        mneu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

        mneu.setBehindOffset(280);
        initFragment();
    }

    //初始化fragment
    public void initFragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();//开始事务
        /*
        * 用fragment替换镇帧布局；参数1：帧布局容器id，参数2：要替换的fragemnt；参数3：标记
        * */
        transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(),TAG_LEFT_MENU);
        transaction.replace(R.id.fl_main,new ContentFragment(),TAG_CONTENT);
        transaction.commit();
    }
}
