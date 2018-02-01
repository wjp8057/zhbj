package com.wjp.mypc.fragment;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wjp.mypc.R;
import com.wjp.mypc.domain.NewsMenu;


import java.util.ArrayList;

/*
* 侧边栏fragment
* */
public class LeftMenuFragment extends BaseFragment {
    public ListView lv_leftmenu;
    public ArrayList<String> lvdata;
    public ArrayList<NewsMenu.NewsMenuData> newsMenuData;
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.fragment_left_menu,null);
        /*
         * 找到左侧菜单的listview，给它设置数据
         * */
        lv_leftmenu=view.findViewById(R.id.lv_leftmenu);
        return view;
    }

    @Override
    public void initData() {

    }

    //为左侧菜单设置数据
    public void setLeftMenuData(ArrayList<NewsMenu.NewsMenuData> data){
        newsMenuData=data;//侧边栏的数据对象
        /*String[] args={newsMenuData.get(0).title,newsMenuData.get(1).title};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(mActivity,android.R.layout.simple_list_item_1,args);*/
//        System.out.println(arrayAdapter);
        lv_leftmenu.setAdapter(new lvAdapter());
    }


    protected class lvAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return newsMenuData.size();
        }

        @Override
        public Object getItem(int position) {
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
            return view;
        }
    }


}
