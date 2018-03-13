package com.wjp.mypc;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imgbtn_back,imgbtn_menu,imgbtn_textsz,imgbtn_share;
    private LinearLayout llcontrol;
    private WebView wv_nsdt;
    private ProgressBar pb_loading;
    private String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        imgbtn_back=(ImageButton) findViewById(R.id.imgbtn_back);
        imgbtn_menu=(ImageButton) findViewById(R.id.imgbtn_menu);
        imgbtn_textsz=(ImageButton) findViewById(R.id.imgbtn_textsz);
        imgbtn_share=(ImageButton) findViewById(R.id.imgbtn_share);
        llcontrol=(LinearLayout) findViewById(R.id.nsdt_ll_control);
        wv_nsdt=(WebView) findViewById(R.id.wv_nsdt);
        pb_loading=(ProgressBar)findViewById(R.id.pb_loading);
        imgbtn_back.setOnClickListener(this);
        imgbtn_textsz.setOnClickListener(this);
        imgbtn_share.setOnClickListener(this);
        /*
        * 隐藏菜单按钮,打开返回按钮
        * */
        imgbtn_menu.setVisibility(View.GONE);
        imgbtn_back.setVisibility(View.VISIBLE);
        /*
        * 打开字体按钮和分享按钮
        * */
        llcontrol.setVisibility(View.VISIBLE);
        mUrl=getIntent().getStringExtra("dturl");
        wv_nsdt.loadUrl(mUrl);
        WebSettings settings=wv_nsdt.getSettings();
        settings.setBuiltInZoomControls(true);//设置是否有缩放按钮
        settings.setJavaScriptEnabled(true);//支持js
        settings.setUseWideViewPort(true);//自适应屏幕 任意比例缩放
        wv_nsdt.setWebViewClient(new WebViewClient(){
            //开始加在网页
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("start","开始加载网页了");
                pb_loading.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("end","结束加载网页了");
                pb_loading.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }

            // 所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return  true;
            }
        });
    }

    /*
    * 图片按钮点击事件
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.imgbtn_textsz:
                TextSizeSelect();
                break;
            case R.id.imgbtn_share:
                share_def();
                break;
            default:break;
        }
    }
    private int mCurrenWhich=2;
    private int mTempWhich=2;
    /*
    * 网页字体大小选择
    * */
    private void TextSizeSelect(){
        String[] ts=new String[]{"超大号字体", "大号字体", "正常字体", "小号字体",
                "超小号字体" };
        AlertDialog.Builder builder=new AlertDialog.Builder(NewsDetailActivity.this);
        builder.setTitle("字体设置");
        builder.setSingleChoiceItems(ts, mCurrenWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTempWhich=which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("which",String.valueOf(which));
                WebSettings webSettings=wv_nsdt.getSettings();
                switch (mTempWhich){
                    case 0:
                        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        webSettings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                    default:
                        break;
                }
                mCurrenWhich=mTempWhich;
            }
        });
        builder.setNegativeButton("取消",null);
        builder.create();
        builder.show();
    }

    /*
    * 分享到社交平台
    * */
    private void share_def(){
        Log.d("aaa","你好");
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(mUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//                oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(mUrl);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }
}
