package com.wjp.mypc;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wjp.mypc.utils.PrefUtils;

import java.util.ArrayList;

/*
* 新手引导页
* */

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;//定义一个viewpager
    //引导页图片id数据
    private int[] mImageIds=new int[]{
            R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3
    };
    //定义一个list集合存放ImageView
    private ArrayList<ImageView> mImageViewList;

    private LinearLayout llContainer;

    private ImageView ivRedPoint;//小红点

    private int mPointDis;//小红点移动的距离

    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题，必须在setContentView之前调用

        setContentView(R.layout.activity_guide);
        //找到viewpager
        mViewPager=(ViewPager) findViewById(R.id.vp_guide);
        //找到小圆点线性布局
        llContainer=(LinearLayout) findViewById(R.id.ll_container);
        //找到小红点
        ivRedPoint=(ImageView) findViewById(R.id.iv_red_point);
        //初始化开始体验按钮
        btnStart=(Button) findViewById(R.id.btn_start);

        //设置数据源
        intData();//先初始化数据
        mViewPager.setAdapter(new GuideAdapter());//设置数据

        //设置监听事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*
            * position:判断当前页是向前滑还是向后滑 possition来判断viewPager是在向前滑还是在向后滑
            * 如果 possition==当前页id 表示向后滑，possition<当前页id 表示viewPager向前滑
            * positionOffset:位移偏移量
            * positionOffsetPixels：具体移动了多少个像素
            * */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当页面滑动过程中的回调
//                System.out.println("当前页面是："+position+"  移动的百分比是："+positionOffset+"  像素："+positionOffsetPixels);
                //更新 小红点的距离
                int  leftMargin=(int)(mPointDis*positionOffset)+position*mPointDis;
                RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)ivRedPoint.getLayoutParams();
                params.leftMargin=leftMargin;
                //重新设置布局参数
                ivRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //当页面选择时候的回调
                if (position==mImageViewList.size()-1){
                    btnStart.setVisibility(View.VISIBLE);
                }else{
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //当页面状态发生改变时的回调
            }
        });
        /*
        * 计算两个圆点的距离
        * 移动距离=第二个圆点left值-第一个圆点left值
        * measure->layout(确定位置)->draw(activity的oncreate方法执行完后)
        * mPointDis=llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
        System.out.println("圆点距离"+mPointDis);
        * */
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDis=llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setBoolean(GuideActivity.this,"is_first_enter",false);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private void intData(){
        mImageViewList=new ArrayList<ImageView>();
        for (int i=0;i<mImageIds.length;i++){
            ImageView view=new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(view);

            //初始化小圆点
            ImageView point=new ImageView(this);
            //设置图片形状（shape形状）
            point.setImageResource(R.drawable.shape_point_gray);

            //初始化布局参数
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i>0){
                params.leftMargin=10;
            }
            //设置布局参数
            point.setLayoutParams(params);
            llContainer.addView(point);//网布局里添加小圆点
        }
    }

    class GuideAdapter extends PagerAdapter{
        //item的个数
        @Override
        public int getCount(){
            return mImageViewList.size();
        }
        @Override
        public  boolean isViewFromObject(View view, Object object){
                return view==object;
        }


        //初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view=mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        // 销毁item
       @Override
        public void destroyItem(ViewGroup container,int postition,Object object){
            container.removeView((View)object);
       }
    }


}

