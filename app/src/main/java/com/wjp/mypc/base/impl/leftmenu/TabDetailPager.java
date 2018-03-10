package com.wjp.mypc.base.impl.leftmenu;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.wjp.mypc.R;
import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.domain.NewTabBean;
import com.wjp.mypc.domain.NewsMenu;
import com.wjp.mypc.global.GlobalConstants;
import com.wjp.mypc.utils.CacheUtils;
import com.wjp.mypc.view.PullToRefreshListView;
import com.wjp.mypc.view.TabNewsViewPager;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author wjp
 * @date 2018/02/04 21:33
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private NewsMenu.NewsTabData newsMenuData;
    private TextView view;

    private TabNewsViewPager vp_tabdetail;
    private String mUrl;
    private NewTabBean mNewstabdatas;
    //头条新闻
    private ArrayList<NewTabBean.NewsTabtopnews> mTopnews;
    //标题
    private TextView tv_title;
    //轮播图片小圆圈
    private CirclePageIndicator mCircleIndicator;
    //新闻列表listview
    private PullToRefreshListView mListView;
    //新闻内容
    private ArrayList<NewTabBean.NewsTabnews> mNews;
    public TabDetailPager(Activity activity,NewsMenu.NewsTabData m) {
        super(activity);
        this.newsMenuData=m;
        //新闻的链接
        mUrl=m.url;
    }

    @Override
    public View initView() {
        View view=View.inflate(mActivity,R.layout.pager_tab_detail,null);
        mListView=(PullToRefreshListView) view.findViewById(R.id.lv_tabdetail);
        /*给listview添加一个头布局，让图片能向上滑*/
        View headview=View.inflate(mActivity,R.layout.list_item_header,null);
        mListView.addHeaderView(headview);

        //回调
        mListView.setOnRefershListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataServer();
            }
        });

        vp_tabdetail=headview.findViewById(R.id.vp_tabdetail);
        tv_title=headview.findViewById(R.id.tb_title);
        mCircleIndicator=headview.findViewById(R.id.indicator);

        /*
        *此处空指针异常
        view.setText(newsMenuData.title);
         *
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);*/
        return view;
    }

    @Override
    public void initData() {
        //设置适配器
//          vp_tabdetail.setAdapter(new tabdetail());
          String cache=CacheUtils.getCache(mUrl,mActivity);
          if (!TextUtils.isEmpty(cache)){
              analysis(cache);
          }
          //从服务器获取数据
          getDataServer();
//        view.setText(newsMenuData.title);
//        TextView view=new TextView(mActivity);
//        view.setText("页签");
//        view.setTextSize(25);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);
    }

    class tabdetail extends PagerAdapter{
        @Override
        public int getCount() {
            return mTopnews.size();
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view=new ImageView(mActivity);
//            System.out.println("图片链接:"+mTopnews.get(position).getTopimage());
//            view.setImageResource(R.drawable.pic_item_list_default);
            //设置图片显示的参数
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    //加载过程中显示的图片
                    .setLoadingDrawableId(R.drawable.pic_item_list_default)
                    //加载失败后显示的图片
                    .setFailureDrawableId(R.drawable.pic_item_list_default)
                    .build();
            /*参数：
            * 图片要显示在哪个ImageView中；图片的链接；图片的参数
            * */
            x.image().bind(view, mTopnews.get(position).getTopimage(),imageOptions);
            container.addView(view);
            return view;
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

    //请求网络数据
    protected void getDataServer(){
        RequestParams params=new RequestParams(GlobalConstants.SERVER_URL+mUrl);
        params.setConnectTimeout(6000);
        params.setReadTimeout(6000);
//        System.out.println("参数："+params);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                /*Gson gson=new Gson();
                NewTabBean newTabBean=gson.fromJson(result, NewTabBean.class);
                System.out.println("结果:"+newTabBean);*/
                /*
                * 保存结果
                * */
                CacheUtils.setCache(mUrl,result,mActivity);
                analysis(result);

                //刷新结束，收起控件
                mListView.OnRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mListView.OnRefreshComplete(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //解析网络请求的数据
    protected void analysis(String res){
        Gson gson=new Gson();
        mNewstabdatas=gson.fromJson(res,NewTabBean.class);
        mTopnews=mNewstabdatas.data.getTopnews();
        /*
        * 新闻内容
        * */
        mNews=mNewstabdatas.data.getNews();
        if(mTopnews!=null){
            /*
            图片的adapter
            * */
            vp_tabdetail.setAdapter(new tabdetail());
            tv_title.setText(mTopnews.get(0).getTitle());
            /*
            * 设置图片轮播时的小圆圈
            * */
            mCircleIndicator.setViewPager(vp_tabdetail);
            mCircleIndicator.setSnap(true);
            /*
            *默认让第一个选择，解决页面销毁后重新初始化，小圆点跟图片对不上号的问题
            * */
            mCircleIndicator.onPageSelected(0);
            vp_tabdetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_title.setText(mTopnews.get(position).getTitle());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            /*
            * listview的adapter
            * */
            mListView.setAdapter(new myListViewAdapter());
        }

    }

    class myListViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mNews.size();
        }

        @Override
        public NewTabBean.NewsTabnews getItem(int position) {
            return mNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=View.inflate(mActivity,R.layout.list_item_news,null);
                holder=new ViewHolder();
                holder.ivIcon=(ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvTtile=(TextView)convertView.findViewById(R.id.tv_title);
                holder.tvData=(TextView)convertView.findViewById(R.id.tv_date);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();
                /*
                * 设置图片
                * */
                ImageOptions imageOptions = new ImageOptions.Builder()
                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        //加载过程中显示的图片
                        .setLoadingDrawableId(R.drawable.pic_item_list_default)
                        //加载失败后显示的图片
                        .setFailureDrawableId(R.drawable.pic_item_list_default)
                        .build();
                /*参数：
                 * 图片要显示在哪个ImageView中；图片的链接；图片的参数
                 * */
                x.image().bind(holder.ivIcon, mNews.get(position).getListimage(),imageOptions);
                /*
                * 设置标题
                * */
                holder.tvTtile.setText(mNews.get(position).getTitle());
                /*
                * 设置时间
                * */
               holder.tvData.setText(mNews.get(position).getPubdate());
            }
            return convertView;
        }
    }

    static class ViewHolder{
        public ImageView ivIcon;
        public TextView tvTtile,tvData;
    }
}
