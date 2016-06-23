package com.example.feng.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.view.HeadView;

public class WebViewActivity extends Activity {

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
            String url = bundle.getString("href");
            progressDialog.show();
            wv_web.loadUrl(url);//"https://www.baidu.com/"
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

        WebSettings settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);//启用支持javascript
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //图片自适应 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
        settings.setSupportZoom(true);// 设置支持缩放
        settings.setBuiltInZoomControls(true);// 设置缩放工具的显示
//        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setUseWideViewPort(true);//关键点
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        progressDialog.setProgress(100);
        progressDialog.getProgress();

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
