package com.wjp.mypc.fragment;

import android.view.View;

import com.wjp.mypc.R;

/*
* 侧边栏fragment
* */
public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.fragment_left_menu,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
