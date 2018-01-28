package com.wjp.mypc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.wjp.mypc.librarydemo.Utils;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Utils.testLib();
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.slidingmenu_left_test);

        SlidingMenu mneu=getSlidingMenu();
        getSlidingMenu().setMode(SlidingMenu.LEFT);
        mneu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mneu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

        mneu.setBehindOffset(300);
    }
}
