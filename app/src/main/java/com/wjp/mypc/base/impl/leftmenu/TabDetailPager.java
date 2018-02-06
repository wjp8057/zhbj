package com.wjp.mypc.base.impl.leftmenu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wjp.mypc.R;
import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.domain.NewTabBean;
import com.wjp.mypc.domain.NewsMenu;
import com.wjp.mypc.global.GlobalConstants;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author wjp
 * @date 2018/02/04 21:33
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private NewsMenu.NewsTabData newsMenuData;
    private TextView view;

    private ViewPager vp_tabdetail;
    private String mUrl;

    public TabDetailPager(Activity activity,NewsMenu.NewsTabData m) {

        super(activity);
        this.newsMenuData=m;
        mUrl=m.url;
    }

    @Override
    public View initView() {
        View view=View.inflate(mActivity,R.layout.pager_tab_detail,null);
        vp_tabdetail=view.findViewById(R.id.vp_tabdetail);
        /*
        *此处空指针异常
        * view.setText(newsMenuData.title);
        * */

//        view.setTextSize(25);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        //设置适配器
          vp_tabdetail.setAdapter(new tabdetail());
          //从服务器获取数据
          getDataServer();
//        view.setText(newsMenuData.title);
//        TextView view=new TextView(mActivity);
//        view.setText("页签");
//        view.setTextSize(25);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);
    }

    //请求网络数据
    protected void getDataServer(){
        RequestParams params=new RequestParams(GlobalConstants.SERVER_URL+mUrl);
        System.out.println("参数："+params);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                NewTabBean newTabBean=gson.fromJson(result, NewTabBean.class);
                System.out.println("结果:"+newTabBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    class tabdetail extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
