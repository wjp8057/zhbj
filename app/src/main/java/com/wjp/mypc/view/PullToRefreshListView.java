package com.wjp.mypc.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wjp.mypc.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wjp
 * on 2018-03-08 21:58
 * Describe
 */
public class PullToRefreshListView extends ListView{
    /*
    * 下拉刷新
    * */
    private static final int STATE_PULL_TO_REFRESH = 1;
    /*
     * 松开刷新
     * */
    private static final int STATE_RELEASE_TO_REFRESH = 2;
    /*
     * 正在刷新
     * */
    private static final int STATE_REFRESHING = 3;
    /*
    * 当前刷新状态
    * */
    private int mCurrentState = STATE_PULL_TO_REFRESH;

    private View mHeaderView;
    private int mHeaderViewHeight;
    private int startY=-1;
    private ImageView imgJt;
    private ProgressBar pbJdt;
    private TextView tvZt;
    private TextView tvTime;
    private RotateAnimation rotateUp;
    private RotateAnimation rotateDown;

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
        imgJt=(ImageView)mHeaderView.findViewById(R.id.img_jiantou);
        pbJdt=(ProgressBar)mHeaderView.findViewById(R.id.pb_jdt);
        tvZt=(TextView)mHeaderView.findViewById(R.id.tv_zt);
        tvTime=(TextView)mHeaderView.findViewById(R.id.tv_time);
        this.addHeaderView(mHeaderView);

        //隐藏头布局
        mHeaderView.measure(0,0);
        mHeaderViewHeight=mHeaderView.getMeasuredHeight();
        Log.d("mHeaderViewHeight",String.valueOf(mHeaderViewHeight));
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);

        initAnim();
        setCurrentTime();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY=(int)ev.getY();
                Log.d("startY1",String.valueOf(startY));
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 当用户按住头条新闻的viewpager进行下拉时,ACTION_DOWN会被viewpager消费掉,
                    // 导致startY没有赋值,此处需要重新获取一下
                    startY = (int) ev.getY();
                    Log.d("ACTION_MOVE :startY",String.valueOf(startY));
                }
                int endY=(int)ev.getY();
                Log.d("endY",String.valueOf(endY));
                int dy=endY-startY;
                Log.d("dy",String.valueOf(dy));
                int firstVisiblePosition=getFirstVisiblePosition();

                if (mCurrentState==STATE_REFRESHING){
                    break;
                }

                //必须下拉，并且当前显示的是第一个item
                if (dy>0&&firstVisiblePosition==0){
                    int paddingHeight=dy-mHeaderViewHeight;//计算当前下拉控件的padding值
                    Log.d("paddingHeight",String.valueOf(paddingHeight));
                    mHeaderView.setPadding(0,paddingHeight,0,0);
                    if (paddingHeight>0&&mCurrentState!=STATE_RELEASE_TO_REFRESH){
                        /*
                        * 改为松开刷新*/
                        mCurrentState=STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    }else if (paddingHeight<0&&mCurrentState!=STATE_PULL_TO_REFRESH){
                        /*
                        * 下拉刷新
                        * */
                        mCurrentState=STATE_PULL_TO_REFRESH;
                        refreshState();
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY=-1;
                if (mCurrentState==STATE_RELEASE_TO_REFRESH){
                    mCurrentState=STATE_REFRESHING;
                    refreshState();
                    // 完整展示头布局
                    mHeaderView.setPadding(0, 0, 0, 0);

                    //进行回调刷新
                    if (mListener!=null){
                        mListener.onRefresh();
                    }
                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    // 隐藏头布局
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                }
                break;
                default:break;
        }
        return super.onTouchEvent(ev);
    }


    /*
    * 初始化箭头方向
    * */
    protected void initAnim(){
        rotateUp=new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateUp.setDuration(200);
        rotateUp.setFillAfter(true);

        rotateDown=new RotateAnimation(-180,0,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateDown.setDuration(200);
        rotateDown.setFillAfter(true);
    }

    /*
    * 根据当前刷新状态改变刷新界面
    * */
    protected  void refreshState(){
        switch (mCurrentState){
            case STATE_PULL_TO_REFRESH:
                tvZt.setText("下拉刷新");
                pbJdt.setVisibility(View.INVISIBLE);
                imgJt.startAnimation(rotateDown);
                break;
            case STATE_RELEASE_TO_REFRESH:
                tvZt.setText("松开刷新");
                pbJdt.setVisibility(View.INVISIBLE);
                imgJt.startAnimation(rotateUp);
                break;
            case STATE_REFRESHING:
                tvZt.setText("正在刷新...");
                imgJt.clearAnimation();
                imgJt.setVisibility(View.INVISIBLE);
                pbJdt.setVisibility(View.VISIBLE);
                break;
            default:break;
        }
    }

    /*
    * 3、定义成员变量，接收监听对象
    * */
    private OnRefreshListener mListener;

    /**
     * 2. 暴露接口,设置监听
     */
    public void setOnRefershListener(OnRefreshListener listener){
        mListener=listener;
    }

    /*
     * 1、下拉刷新的回调接口
     * */
    public interface OnRefreshListener{
        public void onRefresh();
    }

    /*
    * 刷新结束，收起控件
    * */
    public void OnRefreshComplete(boolean flag){
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
        mCurrentState=STATE_PULL_TO_REFRESH;
        tvZt.setText("下拉刷新");
        pbJdt.setVisibility(View.INVISIBLE);
        imgJt.setVisibility(View.VISIBLE);

        if (flag){
            setCurrentTime();
        }
    }

    // 设置刷新时间
    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());

        tvTime.setText(time);
    }

}