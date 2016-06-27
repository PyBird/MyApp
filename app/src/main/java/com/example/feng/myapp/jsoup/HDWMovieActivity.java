package com.example.feng.myapp.jsoup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.feng.myapp.R;
import com.example.feng.myapp.WebViewActivity;
import com.example.feng.myapp.WebViewHtmlActivity;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;
import com.example.feng.myapp.utils.jsoup.DocumentUtils;
import com.example.feng.myapp.utils.jsoup.GetHtmlData;
import com.example.feng.myapp.view.HeadView;
import com.example.feng.myapp.view.MarqueeTextView;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HDWMovieActivity extends BaseActivity {

    private HeadView headView;

    private GridView gv_data;

    private Document document;
    private SmartAdapter<Map<String, String>> smartAdapter;
    private ArrayList<Map<String, String>> htmlData;

    private ProgressDialog progressDialog;

    private String URL="http://www.hdwan.net/";
    private String URL_DETAIL="";
    private int HTML_TYPE=1;//请求类型，1.列表，2.详情

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdwmovie);

        initView();
        initEvent();
        getData();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        handler.removeCallbacks(runnable);
    }

    private void initView() {
        headView = (HeadView) findViewById(R.id.hv_head);
        gv_data = (GridView) findViewById(R.id.gv_data);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载数据中");
//        progressDialog.setCancelable(false);

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
                MarqueeTextView tv_title = viewHolder.GetView(R.id.tv_title);
                tv_title.setScrollWidth(newWidth);
                tv_title.setText(item.get("title"));
            }
        };

        gv_data.setAdapter(smartAdapter);
    }

    private void initEvent() {
        headView.setLeftLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headView.setRightLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyToastUtils.showToastLong(HDWMovieActivity.this,"next--"+URL);
                getData();
            }
        });

        headView.getRightView().setVisibility(View.INVISIBLE);

        gv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String href = htmlData.get(position).get("href");
//                Intent intent = new Intent(HDWMovieActivity.this, WebViewHtmlActivity.class);
//                intent.putExtra("href",href);
//                startActivity(intent);

                HTML_TYPE=2;
                URL_DETAIL = htmlData.get(position).get("href");
                getData();
            }
        });
    }

    private void getData() {

        progressDialog.show();

        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if(HTML_TYPE==1){
                document = DocumentUtils.getDocumentFromUrl(URL);
            }else{
                document = DocumentUtils.getDocumentFromUrl(URL_DETAIL);
            }

            Message msg = new Message();
            msg.what = HTML_TYPE;
            HTML_TYPE=1;
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
                        Map<String,String> map = GetHtmlData.getHDWIndexDataAndPage(document);
                        String nextUrl = map.get("nextUrl");
                        String extendUrl = map.get("extendUrl");
                        String page = map.get("page");
                        if(!TextUtils.isEmpty(page) && !TextUtils.isEmpty(nextUrl)){
                            headView.getRightTextView().setText(page);
//                            headView.getRightTextView().setTag(nextUrl);
                            URL = nextUrl;
                            headView.getRightView().setVisibility(View.VISIBLE);
                        }

                    }catch (Exception e){
                        MyToastUtils.showToastShort(HDWMovieActivity.this,"解析出错");
                    }
                }else{
                    MyToastUtils.showToastShort(HDWMovieActivity.this,"获取数据失败");
                }
//                document.remove();
                smartAdapter.notifyDataSetChanged();
            }
            else if (message.what == 2) {

                try{

                    Map<String, Object> map = GetHtmlData.getHDWDetailData(document);
                    String html = map.get("html")+"";
//                    String downUrl = map.get("downUrl");
//                    String downName = map.get("downName");
//                    String downType = map.get("downType");
                    ArrayList<String> downUrl = (ArrayList<String>)map.get("downUrl");
                    ArrayList<String> downName = (ArrayList<String>)map.get("downName");
                    ArrayList<String> downType = (ArrayList<String>)map.get("downType");

                    if (TextUtils.isEmpty(html)) {

                        MyToastUtils.showToastShort(HDWMovieActivity.this, "数据为空");
                    } else {
//                    String href = htmlData.get(position).get("href");
                        Intent intent = new Intent(HDWMovieActivity.this, WebViewHtmlActivity.class);
                        intent.putExtra("html", html);
//                        intent.putExtra("downUrl", downUrl);
//                        intent.putExtra("downName", downName);
//                        intent.putExtra("downType", downType);
                        intent.putStringArrayListExtra("downUrl", downUrl);
                        intent.putStringArrayListExtra("downName", downName);
                        intent.putStringArrayListExtra("downType", downType);
                        startActivity(intent);
                    }

                }catch (Exception e){
                    MyToastUtils.showToastShort(HDWMovieActivity.this,"解析出错");
                }
            }
            else{
                MyToastUtils.showToastShort(HDWMovieActivity.this,"请求网络失败");
            }

        }
    };
}
