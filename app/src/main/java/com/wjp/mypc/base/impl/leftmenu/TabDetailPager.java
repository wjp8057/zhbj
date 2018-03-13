package com.wjp.mypc.base.impl.leftmenu;

import android.app.Activity;
import java.util.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.wjp.mypc.NewsDetailActivity;
import com.wjp.mypc.R;
import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.domain.NewTabBean;
import com.wjp.mypc.domain.NewsMenu;
import com.wjp.mypc.global.GlobalConstants;
import com.wjp.mypc.utils.CacheUtils;
import com.wjp.mypc.utils.PrefUtils;
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
    private ArrayList<NewTabBean.TopNews> mTopnews;
    //标题
    private TextView tv_title;
    //轮播图片小圆圈
    private CirclePageIndicator mCircleIndicator;
    //新闻列表listview
    private PullToRefreshListView mListView;
    //新闻内容
    private ArrayList<NewTabBean.NewsData> mNews;

    private myListViewAdapter myListViewAdapter;

    private Handler mHandler;

    //下一页数据链接
    private String mMoreUrl;
    public TabDetailPager(Activity activity,NewsMenu.NewsTabData m) {
        super(activity);
        this.newsMenuData=m;
        //新闻的链接
        mUrl=newsMenuData.url.toString();
    }

    @Override
    public View initView() {
        View view=View.inflate(mActivity,R.layout.pager_tab_detail,null);
        mListView=(PullToRefreshListView) view.findViewById(R.id.lv_tabdetail);
        /*给listview添加一个头布局，让图片能向上滑*/
        View headview=View.inflate(mActivity,R.layout.list_item_header,null);
        vp_tabdetail=headview.findViewById(R.id.vp_tabdetail);
        tv_title=headview.findViewById(R.id.tb_title);
        mCircleIndicator=headview.findViewById(R.id.indicator);
        mListView.addHeaderView(headview);

        //回调
       mListView.setOnRefershListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataServer();
            }

            @Override
            public void onLoadMore() {
                //判断是否有下一页数据
                if (mMoreUrl!=null){
                    getMoreDataServer();
                }else{
                    Toast.makeText(mActivity,"没有更多数据了",Toast.LENGTH_LONG).show();
                    mListView.OnRefreshComplete(false);
                }
            }
        });

       /*
       * 点击标记为已读
       * */
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int posi=position-mListView.getHeaderViewsCount();
                String read_ids= PrefUtils.getString(mActivity,"read_ids","");
                if (!read_ids.contains(String.valueOf(mNews.get(posi).id))){
                    read_ids=read_ids+mNews.get(posi).id+",";
                    PrefUtils.setString(mActivity,"read_ids",read_ids);
                }
                TextView tvTitle=(TextView) view.findViewById(R.id.tv_title);
                tvTitle.setTextColor(Color.GRAY);

                /*
                * 跳转新闻详情页
                * */
               Intent intent=new Intent(mActivity, NewsDetailActivity.class);
               intent.putExtra("dturl",mNews.get(posi).url);
               mActivity.startActivity(intent);
           }
       });

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
              analysis(cache,false);
          }
          //从服务器获取数据
          getDataServer();
        /*view.setText(newsMenuData.title);
        TextView view=new TextView(mActivity);
        view.setText("页签");
        view.setTextSize(25);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);*/
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
            x.image().bind(view, mTopnews.get(position).topimage,imageOptions);
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

    class myListViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mNews.size();
        }

        @Override
        public NewTabBean.NewsData getItem(int position) {
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

            }
            NewTabBean.NewsData news=getItem(position);
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
            x.image().bind(holder.ivIcon, news.listimage,imageOptions);
            /*
             * 设置标题
             * */
            holder.tvTtile.setText(news.title);
            String readids=PrefUtils.getString(mActivity,"read_ids","");
            if(readids.contains(String.valueOf(mNews.get(position).id))){
                holder.tvTtile.setTextColor(Color.GRAY);
            }else {
                holder.tvTtile.setTextColor(Color.BLACK);
            }
            /*
             * 设置时间
             * */
            holder.tvData.setText(news.pubdate);
            return convertView;
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
                analysis(result,false);

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
    protected void analysis(String res,boolean isMore){
        Gson gson=new Gson();
        mNewstabdatas=gson.fromJson(res,NewTabBean.class);
        mNews=mNewstabdatas.data.news;
        String moreUrl2=mNewstabdatas.data.more;
        if (!TextUtils.isEmpty(moreUrl2)){
            mMoreUrl=GlobalConstants.SERVER_URL+moreUrl2;
        }else {
            mMoreUrl=null;
        }

        if (!isMore){
            /*
             * 新闻内容
             * */
            mTopnews=mNewstabdatas.data.topnews;
            if(mTopnews!=null){
            /*
            图片的adapter
            * */
                vp_tabdetail.setAdapter(new tabdetail());
                tv_title.setText(mTopnews.get(0).title);
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
                        tv_title.setText(mTopnews.get(position).title);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                /*
                 * listview的adapter
                 * */
            }
            myListViewAdapter=new myListViewAdapter();
            mListView.setAdapter(myListViewAdapter);

            /*
            * 图片轮播
            * */
            if (mHandler == null) {
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(android.os.Message msg) {
                        int currentItem = vp_tabdetail.getCurrentItem();
                        currentItem++;

                        if (currentItem > mTopnews.size() - 1) {
                            currentItem = 0;// 如果已经到了最后一个页面,跳到第一页
                        }

                        vp_tabdetail.setCurrentItem(currentItem);

                        mHandler.sendEmptyMessageDelayed(0, 3000);// 继续发送延时3秒的消息,形成内循环
                    };
                };

                // 保证启动自动轮播逻辑只执行一次
                mHandler.sendEmptyMessageDelayed(0, 3000);// 发送延时3秒的消息

                vp_tabdetail.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                System.out.println("ACTION_DOWN");
                                // 停止广告自动轮播
                                // 删除handler的所有消息
                                mHandler.removeCallbacksAndMessages(null);
                                // mHandler.post(new Runnable() {
                                //
                                // @Override
                                // public void run() {
                                // //在主线程运行
                                // }
                                // });
                                break;
                            case MotionEvent.ACTION_CANCEL:// 取消事件,
                                // 当按下viewpager后,直接滑动listview,导致抬起事件无法响应,但会走此事件
                                System.out.println("ACTION_CANCEL");
                                // 启动广告
                                mHandler.sendEmptyMessageDelayed(0, 3000);
                                break;
                            case MotionEvent.ACTION_UP:
                                System.out.println("ACTION_UP");
                                // 启动广告
                                mHandler.sendEmptyMessageDelayed(0, 3000);
                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        }else {
            //加载更多
            List<NewTabBean.NewsData> moreNews=mNewstabdatas.data.news;
            // 将数据追加在原来的集合中
            mNews.addAll(moreNews);
            // 刷新listview
            myListViewAdapter.notifyDataSetChanged();
        }

    }


    static class ViewHolder{
        public ImageView ivIcon;
        public TextView tvTtile,tvData;
    }

    //加载更多数据
    protected void getMoreDataServer(){
        RequestParams params=new RequestParams(mMoreUrl);
        params.setConnectTimeout(6000);
        params.setReadTimeout(6000);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                analysis(result,true);

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
}
