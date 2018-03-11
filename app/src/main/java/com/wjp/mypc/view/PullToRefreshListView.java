package com.wjp.mypc.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
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
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener{
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
    private View mFootView;
    private int mHeaderViewHeight;
    private int mFootViewHeight;
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
        initFootview();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFootview();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFootview();
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
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);

        initAnim();
        setCurrentTime();
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
                if (mCurrentState==STATE_REFRESHING){
                    break;
                }

                //必须下拉，并且当前显示的是第一个item
                if (dy>0&&firstVisiblePosition==0){
                    int paddingHeight=dy-mHeaderViewHeight;//计算当前下拉控件的padding值
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

        //下拉加载更多
        public void onLoadMore();
    }

    /*
    * 刷新结束，收起控件
    * */
    public void OnRefreshComplete(boolean flag){
        if(!isLoadMore) {
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

            mCurrentState = STATE_PULL_TO_REFRESH;
            tvZt.setText("下拉刷新");
            pbJdt.setVisibility(View.INVISIBLE);
            imgJt.setVisibility(View.VISIBLE);

            if (flag) {// 只有刷新成功之后才更新时间
                setCurrentTime();
            }
        }else {
            //加载更多
            mFootView.setPadding(0, -mFootViewHeight, 0, 0);//隐藏布局
            isLoadMore = false;
        }
    }

    // 设置刷新时间
    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());

        tvTime.setText(time);
    }

    /*
    * 加载更多的foot布局
    * */
    private void initFootview(){
            mFootView=View.inflate(getContext(),R.layout.pull_to_refresh_foot,null);
            this.addFooterView(mFootView);
            mFootView.measure(0,0);
            mFootViewHeight=mFootView.getMeasuredHeight();
            mFootView.setPadding(0,-mFootViewHeight,0,0);
            this.setOnScrollListener(this);
    }



    // 滑动过程回调
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

    }

    /*
    * 标记是否正在加载更多
    * */
    private boolean isLoadMore;
    // 滑动状态发生变化
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //空闲状态
        if (scrollState==SCROLL_STATE_IDLE){
            int lastVIsiblePosition=getLastVisiblePosition();
            if (lastVIsiblePosition==getCount()-1&&!isLoadMore){// 当前显示的是最后一个item并且没有正在加载更多
                Log.d("aa","加载更多");
                isLoadMore=true;
                mFootView.setPadding(0,0,0,0);
                setSelection(getCount() - 1);// 将listview显示在最后一个item上,
                // 从而加载更多会直接展示出来, 无需手动滑动

                //通知主界面加载下一页数据
                if (mListener!=null){
                    mListener.onLoadMore();
                }
            }
        }
    }

}
