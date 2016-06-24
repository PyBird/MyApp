package com.example.feng.myapp.utils;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by onepeak on 2016/6/24.
 */
public class WebViewSetting {


//    WebSettings settings = wv_web.getSettings();
//    settings.setJavaScriptEnabled(false);//启用支持javascript
//    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
//    settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
//    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //图片自适应 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
//    settings.setSupportZoom(false);// 设置支持缩放
//    settings.setBuiltInZoomControls(true);// 设置缩放工具的显示
//    settings.setAllowFileAccess(true); // 允许访问文件
//    settings.setUseWideViewPort(true);
//    settings.setLoadWithOverviewMode(true);

    /**
     * 用WebView显示HTML代码
     * @param view WebView组件
     * @param html 要显示html代码
     */
    public static void webViewShowHtml(WebView view,String html){
        //让webView自适应屏幕
        WebSettings webSettings= view.getSettings(); // webView: 类WebView的实例
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View paramView) {
                return true;
            }
        });

        String baseUrl="about:blank"; //初始化URL
        String historyUrl = null;//历史URL
        view.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", historyUrl);
    }

    /**
     * 用WebView显示HTML代码（针对图文内容）
     * @param view WebView组件
     * @param html 要显示html代码
     */
    public static void webViewShowTextHtml(WebView view,String html){
        //让webView自适应屏幕
        WebSettings webSettings= view.getSettings(); // webView: 类WebView的实例
        webSettings.setJavaScriptEnabled(false);//不支持js
        webSettings.setSupportZoom(false);//不支持缩放
        webSettings.setBuiltInZoomControls(false);//不显示缩放工具
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//使所有列的宽度不超过屏幕宽度
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(false); // 允许访问文件

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View paramView) {
                return true;
            }
        });

        String baseUrl="about:blank"; //初始化URL
        String historyUrl = null;//历史URL
        view.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", historyUrl);
    }

}
