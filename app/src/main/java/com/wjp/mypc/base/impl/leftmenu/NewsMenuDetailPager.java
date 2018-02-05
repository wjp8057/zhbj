package com.wjp.mypc.base.impl.leftmenu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;
import com.wjp.mypc.R;
import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.domain.NewsMenu;

import org.xutils.ViewInjector;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 菜单详情页-新闻
 * @author wjp
 * @date 2018/02/04 15:49
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {
    private TabPageIndicator tabPageIndicator;
    private ArrayList<NewsMenu.NewsTabData> mNewsTabData;
    private ViewPager viewPager;

    //页签对象
    private ArrayList<TabDetailPager> mtabDetailPagers;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        mNewsTabData=children;
    }

    @Override
    public View initView() {
        View view=View.inflate(mActivity,R.layout.pager_news_menu_detail,null);
        /*
        * 找到viewpager
        * 不能在这个方法为viewpager设置适配器，因为构造方法继承了父类构造方法，父类在构造方法也执行initVIew方法，...
        * */
        viewPager=(ViewPager) view.findViewById(R.id.menu_detail_content);
        tabPageIndicator=(TabPageIndicator)view.findViewById(R.id.indicator);
        return view;
    }

    @Override
    public void initData() {
        /*
        * 为viewpager设置适配器
        * */
        mtabDetailPagers=new ArrayList<TabDetailPager>();
        for (int i=0;i<mNewsTabData.size();i++){
            mtabDetailPagers.add(new TabDetailPager(mActivity,mNewsTabData.get(i)));
        }
        viewPager.setAdapter(new NewsMenuDetailAdapter());
        tabPageIndicator.setViewPager(viewPager);//必须在viewpager设置完数据之后
    }

    class NewsMenuDetailAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mtabDetailPagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager=mtabDetailPagers.get(position);
            container.addView(tabDetailPager.mRootView);
            tabDetailPager.initData();
            return tabDetailPager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  mNewsTabData.get(position).title;
        }
    }
}
