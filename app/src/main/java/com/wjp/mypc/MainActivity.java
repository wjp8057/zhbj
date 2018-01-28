package com.wjp.mypc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wjp.mypc.librarydemo.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.testLib();
    }
}
