package com.wjp.mypc.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

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
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.fragment_content,null);
        mViewpaer=view.findViewById(R.id.vp_content);//找到ViewPager
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
    }

    class myViewpager extends PagerAdapter{

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager=basePagerList.get(position);
            basePager.initData();
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
}
