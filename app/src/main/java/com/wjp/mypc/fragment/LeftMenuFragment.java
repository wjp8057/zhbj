package com.wjp.mypc.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wjp.mypc.MainActivity;
import com.wjp.mypc.R;
import com.wjp.mypc.base.impl.NewsCenterPager;
import com.wjp.mypc.domain.NewsMenu;


import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/*
* 侧边栏fragment
* */
public class LeftMenuFragment extends BaseFragment {
    @ViewInject(R.id.lv_leftmenu)
    public ListView lv_leftmenu;
    public ArrayList<String> lvdata;
    public ArrayList<NewsMenu.NewsMenuData> newsMenuData;
    public int mPosition;
    public lvAdapter mlvAdapter;
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.fragment_left_menu,null);
        /*
         * 找到左侧菜单的listview，给它设置数据
         * */
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {

    }

    //为左侧菜单设置数据
    public void setLeftMenuData(ArrayList<NewsMenu.NewsMenuData> data){
        mPosition=0;
        newsMenuData=data;//侧边栏的数据对象
        /*String[] args={newsMenuData.get(0).title,newsMenuData.get(1).title};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mActivity,android.R.layout.simple_list_item_1,args);*/
//        System.out.println(arrayAdapter);
        mlvAdapter=new lvAdapter();
        lv_leftmenu.setAdapter(mlvAdapter);
        /*
         * 给listview设置一个点击事件
        */
        lv_leftmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition=position;
                mlvAdapter.notifyDataSetChanged();
                toggle();

                //点击侧边栏的菜单后要修改contentFragment中的内容
                setCurrentDetailPager(position);
            }
        });
    }

    /**
     * 设置当前菜单详情页
     *
     * @param position
     */
    protected void setCurrentDetailPager(int position) {
        // 获取新闻中心的对象
        MainActivity mainUI = (MainActivity) mActivity;
        // 获取ContentFragment
        FragmentManager fm=mainUI.getSupportFragmentManager();
        ContentFragment contentFragment=(ContentFragment) fm.findFragmentByTag("TAG_CONTENT");
        // 获取NewsCenterPager
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        // 修改新闻中心的FrameLayout的布局
        newsCenterPager.setCurrentDetailPager(position);
    }

    /*
    * 点击之后关闭侧边栏
    * */
    public void toggle(){
        Activity activity=(MainActivity)mActivity;
        SlidingMenu slidingMenu=((MainActivity) mActivity).getSlidingMenu();
        slidingMenu.toggle();

    }




    protected class lvAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return newsMenuData.size();
        }

        @Override
        public NewsMenu.NewsMenuData getItem(int position) {
            return newsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(mActivity,R.layout.lv_leftmenu,null);
            TextView tvMneu=view.findViewById(R.id.lf_menuitem);
            tvMneu.setText(newsMenuData.get(position).title);
            /*
            * 设置颜色
            * */
            if (position==mPosition){
                tvMneu.setEnabled(true);
            }else {
                tvMneu.setEnabled(false);
            }
            return view;
        }
    }


}
