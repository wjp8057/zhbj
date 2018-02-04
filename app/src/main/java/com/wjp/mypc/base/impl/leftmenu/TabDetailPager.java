package com.wjp.mypc.base.impl.leftmenu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.domain.NewsMenu;

import org.w3c.dom.Text;

/**
 * @author wjp
 * @date 2018/02/04 21:33
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private NewsMenu.NewsTabData newsMenuData;
    private TextView view;
    public TabDetailPager(Activity activity,NewsMenu.NewsTabData m) {

        super(activity);
        this.newsMenuData=m;
    }

    @Override
    public View initView() {
        view=new TextView(mActivity);
        /*
        *此处空指针异常
        * view.setText(newsMenuData.title);
        * */

        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        view.setText(newsMenuData.title);
//        TextView view=new TextView(mActivity);
//        view.setText("页签");
//        view.setTextSize(25);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);
    }
}
