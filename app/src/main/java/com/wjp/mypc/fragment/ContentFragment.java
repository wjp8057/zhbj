package com.wjp.mypc.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wjp.mypc.MainActivity;
import com.wjp.mypc.R;
import com.wjp.mypc.base.BasePager;
import com.wjp.mypc.base.impl.GovAffarisPager;
import com.wjp.mypc.base.impl.HomePager;
import com.wjp.mypc.base.impl.NewsCenterPager;
import com.wjp.mypc.base.impl.SettingsPager;
import com.wjp.mypc.base.impl.SmartServicePager;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {
    public ViewPager mViewpaer;//定义一个viewpager变量
    public ArrayList<BasePager> basePagerList;
    public RadioGroup radioGroup;
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.fragment_content,null);
        mViewpaer=view.findViewById(R.id.vp_content);//找到ViewPager
        radioGroup=view.findViewById(R.id.rg_btn);
        return view;
    }

    @Override
    public void initData() {
        basePagerList=new ArrayList<BasePager>();
        basePagerList.add(new HomePager(mActivity));
        basePagerList.add(new NewsCenterPager(mActivity));
        basePagerList.add(new SmartServicePager(mActivity));
        basePagerList.add(new GovAffarisPager(mActivity));
        basePagerList.add(new SettingsPager(mActivity));
        mViewpaer.setAdapter(new myViewpager());
        //设置底部点击监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_home:
                        mViewpaer.setCurrentItem(0,false);//首页
                        setSlidingMenuEnable(false);
                        break;
                    case R.id.tab_news:
                        mViewpaer.setCurrentItem(1,false);//新闻中心
                        setSlidingMenuEnable(true);
                        break;
                    case R.id.tab_service:

                        mViewpaer.setCurrentItem(2,false);//智慧服务
                        setSlidingMenuEnable(true);
                        break;
                    case R.id.tab_goveaff:
                        mViewpaer.setCurrentItem(3,false);//政务
                        setSlidingMenuEnable(true);
                        break;
                    case R.id.tab_settings:
                        mViewpaer.setCurrentItem(4,false);//设置
                        setSlidingMenuEnable(false);
                        break;
                        default:
                            break;
                }
            }
        });

        basePagerList.get(0).initData();//手动加载第一页的数据
        setSlidingMenuEnable(false);
        //设置viewpager页面切换时的监听
        mViewpaer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager basePager=basePagerList.get(position);
                basePager.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //viewpager的适配器
    class myViewpager extends PagerAdapter{

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager=basePagerList.get(position);
//            basePager.initData();如果在这里初始化数据，viewpager默认自动会加载下一页,所以为它设置一个监听，当页面改变时初始化数据
            container.addView(basePager.rootView);
            return basePager.rootView;
        }

        @Override
        public int getCount() {
            return basePagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

    /**
     * 开启或禁用侧边栏
     *
     * @param enable
     */
    protected void setSlidingMenuEnable(boolean enable) {
        // 获取侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    // 获取新闻中心页面
    public NewsCenterPager getNewsCenterPager() {
        NewsCenterPager pager =(NewsCenterPager) basePagerList.get(1);
        return pager;
    }
}
