package com.wjp.mypc.base.impl.leftmenu;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjp.mypc.R;
import com.wjp.mypc.base.BaseMenuDetailPager;
import com.wjp.mypc.domain.Photos;
import com.wjp.mypc.global.GlobalConstants;
import com.wjp.mypc.utils.CacheUtils;
import com.wjp.mypc.utils.PrefUtils;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 菜单详情页-组图
 * @author wjp
 * @date 2018/02/04 15:49
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener{
    private ImageButton imggv_btn;
    public PhotosMenuDetailPager(Activity activity,ImageButton imggv_btn) {
        super(activity);
        imggv_btn.setOnClickListener(this);
        this.imggv_btn=imggv_btn;
    }

    private ListView lv_photos;
    private GridView gv_photos;
    private ArrayList<Photos.dataNews> mNews;
    private ImageButton img_gvbtn;
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.photos,null);
        /*
        * 找到listview和groupview
        * */
        lv_photos=(ListView)view.findViewById(R.id.lv_photos);
        gv_photos=(GridView)view.findViewById(R.id.gv_photos);
        return view;
    }

    @Override
    public void initData() {
        String result=CacheUtils.getCache(GlobalConstants.PHOTO_URL,mActivity);
        if (!TextUtils.isEmpty(result)){
            processData(result);
        }
        getphotosData();
    }

    /*
    * 获取图片
    * */
    protected void getphotosData(){
        RequestParams params=new RequestParams();
        params.setUri(GlobalConstants.PHOTO_URL);
        params.setConnectTimeout(6000);
        params.setReadTimeout(6000);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
                /*
                * 设置缓存
                * */
                CacheUtils.setCache(GlobalConstants.PHOTO_URL,result,mActivity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity,ex.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(mActivity,cex.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*
    * 解析获取的数据
    * */
    protected void processData(String result){
            Gson gson=new Gson();
            Photos photos=gson.fromJson(result,Photos.class);
            mNews=photos.getData().getNews();
            /*
            *数据取出来后，为listview设置adapter
            *
            * */
            lv_photos.setAdapter(new mListViewAdapter());
            gv_photos.setAdapter(new mListViewAdapter());
    }

    private boolean lv_flag=true;
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.imgbtn_gvphoto){
            if (lv_flag){
                lv_flag=false;
                imggv_btn.setImageResource(R.drawable.icon_pic_list_type);
                lv_photos.setVisibility(View.GONE);
                gv_photos.setVisibility(View.VISIBLE);
            }else {
                lv_flag=true;
                imggv_btn.setImageResource(R.drawable.icon_pic_grid_type);
                lv_photos.setVisibility(View.VISIBLE);
                gv_photos.setVisibility(View.INVISIBLE);
            }
        }
    }

    /*
    *ListView的adapter
    * */
    class mListViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mNews.size();
        }
        @Override
        public Photos.dataNews getItem(int position) {
            return mNews.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            holderView holderVIew;
            if (convertView==null){
                   convertView=View.inflate(mActivity,R.layout.photos_list_item,null);
                   holderVIew=new holderView();
                   holderVIew.item_img=convertView.findViewById(R.id.lsitem_img);
                   holderVIew.item_tv=convertView.findViewById(R.id.lsitem_tv);
                   convertView.setTag(holderVIew);
            }else {
                holderVIew=(holderView) convertView.getTag();
            }
            holderVIew.item_tv.setText(mNews.get(position).getTitle());
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
            x.image().bind(holderVIew.item_img, mNews.get(position).getListimage(),imageOptions);
            return convertView;
        }
    }

    static class holderView{
        private ImageView item_img;
        private TextView item_tv;
    }
}
