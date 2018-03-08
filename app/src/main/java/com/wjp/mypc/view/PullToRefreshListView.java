package com.wjp.mypc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.wjp.mypc.R;

/**
 * Created by wjp
 * on 2018-03-08 21:58
 * Describe
 */
public class PullToRefreshListView extends ListView{
    private View mHeaderView;
    private int mHeaderViewHeight;
    private int startY=-1;

    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    /*
    * 初始化头布局
    * */
    private void initHeaderView(){
        mHeaderView=View.inflate(getContext(), R.layout.pull_to_refresh,null);
        this.addHeaderView(mHeaderView);

        //隐藏头布局
        mHeaderView.measure(0,0);
        mHeaderViewHeight=mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY=(int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 当用户按住头条新闻的viewpager进行下拉时,ACTION_DOWN会被viewpager消费掉,
                    // 导致startY没有赋值,此处需要重新获取一下
                    startY = (int) ev.getY();
                }
                int endY=(int)ev.getY();
                int dy=endY-startY;
                int firstVisiblePosition=getFirstVisiblePosition();

                //必须下拉，并且当前显示的是第一个item
                if (dy>0&&firstVisiblePosition==0){
                    int paddingHeight=dy-mHeaderViewHeight;//计算当前下拉控件的padding值
                    mHeaderView.setPadding(0,paddingHeight,0,0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:break;
        }
        return super.onTouchEvent(ev);
    }
}
