package com.example.feng.myapp.jsoup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.feng.myapp.R;
import com.example.feng.myapp.WebViewActivity;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.utils.LoadImgUtils;
import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;
import com.example.feng.myapp.utils.jsoup.DocumentUtils;
import com.example.feng.myapp.utils.jsoup.GetHtmlData;
import com.example.feng.myapp.view.HeadView;
import com.example.feng.myapp.view.MarqueeTextView;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Document;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

public class HDWMovieActivity extends BaseActivity {

    private HeadView headView;

    private GridView gv_data;

    private Document document;
    private SmartAdapter<Map<String, String>> smartAdapter;
    private ArrayList<Map<String, String>> htmlData;

    private ProgressDialog progressDialog;
    private Thread thread;

    private String URL="http://www.hdwan.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdwmovie);

        initView();
        initEvent();
        getData();
    }

    private void initView() {
        headView = (HeadView) findViewById(R.id.hv_head);
        gv_data = (GridView) findViewById(R.id.gv_data);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载数据中");
        progressDialog.setCancelable(false);

        WindowManager wm = this.getWindowManager();
        //获取屏幕宽高
        int width = wm.getDefaultDisplay().getWidth();
//        int imgW=189;
//        int imgH=267;
        final int newWidth = width/2;
//        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newWidth,imgH*newWidth/imgW);

        htmlData = new ArrayList<Map<String, String>>();
        smartAdapter = new SmartAdapter<Map<String, String>>(this, htmlData, R.layout.item_hdwmovie) {
            @Override
            protected void setView(ViewHolder viewHolder, Map<String, String> item) {

                ImageView iv_img = viewHolder.GetView(R.id.iv_img);
//                x.image().bind(iv_img,item.get("img"), LoadImgUtils.getImageOptions());
//                iv_img.setLayoutParams(layoutParams);
                Picasso.with(HDWMovieActivity.this).load(item.get("img")).error(R.drawable.ic_launcher).placeholder(R.drawable.logo).into(iv_img);

//                viewHolder.setTextToTextView(R.id.tv_title,item.get("title"));
                MarqueeTextView tv_title = (MarqueeTextView)viewHolder.GetView(R.id.tv_title);
                tv_title.setScrollWidth(newWidth-100);
//                tv_title.setCurrentPosition(newWidth+100);
                tv_title.setSpeed(2);
                tv_title.setText(item.get("title"));
            }
        };

        gv_data.setAdapter(smartAdapter);

//        gv_data.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.e("onScrollStateChanged","--------------AbsListView:"+view+"--------scrollState:"+scrollState);
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                Log.e("onScroll","--------------firstVisibleItem:"+firstVisibleItem+"--------visibleItemCount:"+visibleItemCount+"----totalItemCount:"+totalItemCount);
//            }
//        });

    }

    private void initEvent() {
        headView.setLeftLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String href = htmlData.get(position).get("href");
                Intent intent = new Intent(HDWMovieActivity.this, WebViewActivity.class);
                intent.putExtra("href",href);
                startActivity(intent);
            }
        });
    }

    private void getData() {

        progressDialog.show();
        if(thread==null){
            thread = new Thread(runnable);
        }
        thread.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            document = DocumentUtils.getDocumentFromUrl(URL);

            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message message) {

            progressDialog.dismiss();
            if (message.what == 1) {

                if(document != null){

                    try{
                        htmlData.addAll(GetHtmlData.getHDWIndexData(document));
                    }catch (Exception e){
                        MyToastUtils.showToastShort(HDWMovieActivity.this,"解析出错");
                    }
                }else{
                    MyToastUtils.showToastShort(HDWMovieActivity.this,"获取数据失败");
                }

                smartAdapter.notifyDataSetChanged();
            }else{
                MyToastUtils.showToastShort(HDWMovieActivity.this,"请求网络失败");
            }
        }
    };
}
