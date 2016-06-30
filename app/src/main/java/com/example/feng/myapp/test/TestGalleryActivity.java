package com.example.feng.myapp.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feng.myapp.R;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;
import com.example.feng.myapp.view.MarqueeTextView;

import java.util.ArrayList;


public class TestGalleryActivity extends Activity {

    Gallery gallery;
    SmartAdapter<String> smartAdapter;
    ArrayList<String> data = new ArrayList<String>();

    public RecyclerView recyclerView;

    private int[] myImageIds = {R.drawable.logo, R.drawable.ic_launcher,R.drawable.loading};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gallery);

        testGallery();
//        testRecyclerView();
    }

    /************************testRecyclerView---********************************/
    private void testRecyclerView(){

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        MyAdapter myAdapter = new MyAdapter();

        recyclerView.setAdapter(myAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter.notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        MarqueeTextView tv_title;

        public MyViewHolder(View itemView) {

//            View view = View.inflate(TestGalleryActivity.this,R.layout.item_dialog_marquee_textview,null);
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (MarqueeTextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//            View view = View.inflate(TestGalleryActivity.this,R.layout.item_dialog_marquee_textview,null);
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hdwmovie,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.iv_img.setImageResource(myImageIds[position]);
        }

        @Override
        public int getItemCount() {
            return myImageIds.length;
        }
    }

    /************************testRecyclerView--++********************************/

    /************************testGallery---********************************/
    private void testGallery(){
        gallery = (Gallery) findViewById(R.id.gallery);

//        gallery.setLayoutMode(L);

        data.add("4546465456WW");
        data.add("4546465456QQ");
        data.add("4546465456AAA");

        smartAdapter = new SmartAdapter<String>(this, data, R.layout.item_hdwmovie) {
            @Override
            protected void setView(ViewHolder viewHolder, String item) {

                ImageView iv_img = viewHolder.GetView(R.id.iv_img);
                iv_img.setImageResource(R.drawable.logo);

                MarqueeTextView tv_title = viewHolder.GetView(R.id.tv_title);
                tv_title.setText(item);
            }
        };

//        gallery.setAdapter(smartAdapter);

//        ImageAdapter imageAdapter = new ImageAdapter(this);
//        gallery.setAdapter(imageAdapter);

        ImageAdapter2 imageAdapter2 = new ImageAdapter2(this);
        gallery.setAdapter(imageAdapter2);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
            //　获得Gallery组件的属性
            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
            mGalleryItemBackground = typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
        }

        //　返回图像总数
        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //　返回具体位置的ImageView对象
        public View getView(int position, View convertView, ViewGroup arent) {
            ImageView imageView = new ImageView(mContext);
            //　设置当前图像的图像（position为当前图像列表的位置）
//            imageView.setImageResource(data[position]);
            imageView.setImageResource(R.drawable.logo);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.WRAP_CONTENT));
            //　设置Gallery组件的背景风格
//            imageView.setBackgroundResource(mGalleryItemBackground);
            return imageView;
        }
    }

    /**
     * 实现循环浏览图片
     */
    public class ImageAdapter2 extends BaseAdapter         /* 改写BaseAdapter自定义一ImageAdapter class */
    {
        int     mGalleryItemBackground;
        private Context mContext;         /* ImageAdapter的建构子 */
//        private int[] myImageIds = {R.drawable.logo,
//                R.drawable.ic_launcher,R.drawable.loading};

        public ImageAdapter2(Context c)
        {
            mContext = c;
            TypedArray a = obtainStyledAttributes(R.styleable.Gallery); /* 使用在res/values/attrs.xml中的定义 的Gallery属性. */
            mGalleryItemBackground = a.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0); ///*取得Gallery属性的Index
            a.recycle();/* 让对象的styleable属性能够反复使用 */
        }

        public int getCount()               /* 一定要重写的方法getCount,传回图片数目总数 */
        {
            //return myImageIds.length;
            return Integer.MAX_VALUE;
        }

        public Object getItem(int position) /* 一定要重写的方法getItem,传回position */
        {
            return position;
        }

        public long getItemId(int position) /* 一定要重写的方法getItemId,传回position */
        {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)/* 一定要重写的方法getView,传回一View对象 */
        {
//      if (position == getCount())
//      {
//        position = 0;
//      }
            ImageView i = new ImageView(mContext);
            i.setImageResource(myImageIds[position%myImageIds.length]);           /* 设定图片给
            i.setImageResource(myImageIds[position%myImageIds.length]);              /* 设定图片给imageView对象 */
            i.setScaleType(ImageView.ScaleType.CENTER_CROP);            /* 重新设定图片的宽高 */
//            i.setLayoutParams(new Gallery.LayoutParams(136, 88));  /* 重新设定Layout的宽高 */
            i.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
//            i.setBackgroundResource(mGalleryItemBackground);       /* 设定Gallery背景图 */
            return i;                                              /* 传回imageView物件 */
        }
    }
    /************************testGallery---++********************************/
}
