package com.example.feng.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.utils.WebViewSetting;
import com.example.feng.myapp.view.HeadView;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class WebViewHtmlActivity extends Activity {

    private HeadView headView;
    private WebView wv_web;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
        initEvent();
        getData();
    }

    private void getData(){

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            String html = bundle.getString("html");
            progressDialog.show();
//            String tt = "<p><a href=\"http://img.hdwan.net/2016/06/e07536a7fff28111779cd071bb58ab47.jpg\" data-title=\"[大地与阴影/大地飞灰][2015][欧美][剧情][HD-MP4/1.5GB][中英双字][720P]\" data-lightbox=\"35355\"><img src=\"http://img.hdwan.net/2016/06/e07536a7fff28111779cd071bb58ab47.jpg\" alt=\"[大地与阴影/大地飞灰][2015][欧美][剧情][HD-MP4/1.5GB][中英双字][720P]\" alt=\"e07536a7fff28111779cd071bb58ab47\" width=\"600\" height=\"337\" class=\"aligncenter size-full wp-image-35357\" srcset=\"http://img.hdwan.net/2016/06/e07536a7fff28111779cd071bb58ab47.jpg 600w, http://img.hdwan.net/2016/06/e07536a7fff28111779cd071bb58ab47-189x106.jpg 189w\" sizes=\"(max-width: 600px) 100vw, 600px\"/></a></p>\n" +
//                    "<p><a href=\"http://img.hdwan.net/2016/06/6f760b0b39d1d02897f123de4f828bbc.jpg\" data-title=\"[大地与阴影/大地飞灰][2015][欧美][剧情][HD-MP4/1.5GB][中英双字][720P]\" data-lightbox=\"35355\"><img src=\"http://img.hdwan.net/2016/06/6f760b0b39d1d02897f123de4f828bbc.jpg\" alt=\"[大地与阴影/大地飞灰][2015][欧美][剧情][HD-MP4/1.5GB][中英双字][720P]\" alt=\"6f760b0b39d1d02897f123de4f828bbc\" width=\"600\" height=\"337\" class=\"aligncenter size-full wp-image-35358\" srcset=\"http://img.hdwan.net/2016/06/6f760b0b39d1d02897f123de4f828bbc.jpg 600w, http://img.hdwan.net/2016/06/6f760b0b39d1d02897f123de4f828bbc-189x106.jpg 189w\" sizes=\"(max-width: 600px) 100vw, 600px\"/></a></p>\n";
//            tt = Jsoup.clean(tt, Whitelist.basicWithImages());
//            wv_web.loadDataWithBaseURL("about:blank", tt, "text/html", "utf-8", null);
            WebViewSetting.webViewShowHtml(wv_web,html);
//            wv_web.loadData(tt, "text/html", "UTF-8");
//            wv_web.reload();
        }else{
            MyToastUtils.showToastShort(this,"链接为空");
        }
    }

    private void initView(){

        headView = (HeadView)findViewById(R.id.hv_head);
        wv_web = (WebView)findViewById(R.id.wv_web);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中……");

//        WebSettings settings = wv_web.getSettings();
//        settings.setJavaScriptEnabled(false);//启用支持javascript
////        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //图片自适应 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
//        settings.setSupportZoom(false);// 设置支持缩放
//        settings.setBuiltInZoomControls(true);// 设置缩放工具的显示
////        settings.setAllowFileAccess(true); // 允许访问文件
//        settings.setUseWideViewPort(true);//关键点
//        settings.setLoadWithOverviewMode(true);

//        progressDialog.setProgress(100);
//        progressDialog.getProgress();

    }

    private void initEvent(){
        headView.setLeftLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //TODO 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        wv_web.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view,String url)
            {
                progressDialog.dismiss();
            }
        });
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(wv_web.canGoBack())
            {
                wv_web.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();//关闭
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wv_web.removeAllViews();
        wv_web.destroy();
    }
}
