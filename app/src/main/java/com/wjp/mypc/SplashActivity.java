package com.wjp.mypc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.wjp.mypc.utils.PrefUtils;


/*
* AppCompatActivity和Activity的区别

首先是AppCompatActivity默认带标题
* */
public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rlRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rlRoot=findViewById(R.id.rl_root);
        /*RotateAnimation
        * fromDegrees：旋转的开始角度。
            toDegrees：旋转的结束角度。
            pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
            pivotXValue：X坐标的伸缩值。
            pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
            pivotYValue：Y坐标的伸缩值。
        * */
        RotateAnimation animRotate= new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        //动画持续时间
        animRotate.setDuration(1000);
        //保持动画结束状态
        animRotate.setFillAfter(true);

        //缩放动画
        ScaleAnimation animScale=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animScale.setDuration(1000);

        //渐变
        AlphaAnimation animAlpha=new AlphaAnimation(0,1);
        animAlpha.setDuration(2000);
        animAlpha.setFillAfter(true);

        //动画结合
        AnimationSet set=new AnimationSet(true);
        set.addAnimation(animRotate);
        set.addAnimation(animScale);
        set.addAnimation(animAlpha);

        //启动动画
        rlRoot.startAnimation(set);

        //设置一个动画监听事件
        animRotate.setAnimationListener(new Animation.AnimationListener() {
            /**
             * 动画开始时回调。
             */
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * 动画结束时回调。
                * 但是当动画重复次数被设置为INFINITE的时候，该方法不会回调。
                */
            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，跳转页面，如果是第一次，则进入新手引导页，否则跳转主页面
                boolean isFirstEnter= PrefUtils.getBoolean(SplashActivity.this,"is_first_enter",true);
                Intent intent;
                if (isFirstEnter){
                    //跳转新手引导页
                    intent=new Intent(getApplicationContext(),GuideActivity.class);
                }else {
                    //主页面
                    intent=new Intent(getApplicationContext(),MainActivity.class);
                }
                startActivity(intent);//跳转页面
                finish();//结束当前页面
            }

            /**
             * 动画重复时回调。
             */
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
