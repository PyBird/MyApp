package com.example.feng.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.feng.myapp.common.Common;
import com.example.feng.myapp.utils.CommonDialog;
import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;
import com.example.feng.myapp.utils.WebViewSetting;
import com.example.feng.myapp.view.HeadView;
import com.example.feng.myapp.view.MarqueeTextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class WebViewHtmlActivity extends Activity {

    private HeadView headView;
    private WebView wv_web;
    private LinearLayout ll_down;

    private ProgressDialog progressDialog;
//    private String downUrl="";//下载地址
//    private String downName="";//下载文件名
//    private String downType="";//下载文件类型
    private ArrayList<String> downUrl = new ArrayList<String>();//下载地址
    private ArrayList<String> downName = new ArrayList<String>();//下载文件名
    private ArrayList<String> downType = new ArrayList<String>();//下载文件类型

    CommonDialog commonDialog;
    private SmartAdapter<String> dialogAdapter;

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
//            downUrl = bundle.getString("downUrl");
//            downName = bundle.getString("downName");
//            downType = bundle.getString("downType");
            downUrl = bundle.getStringArrayList("downUrl");
            downName = bundle.getStringArrayList("downName");
            downType = bundle.getStringArrayList("downType");

//            if(TextUtils.isEmpty(downUrl)){
            if(downUrl.size()==0){
                headView.getRightTextView().setText("暂停下载");
            }
            progressDialog.show();

            WebViewSetting.webViewShowTextHtml(wv_web,html);
//            wv_web.reload();

        }else{
            MyToastUtils.showToastShort(this,"链接为空");
        }
    }

    private void initView(){

        headView = (HeadView)findViewById(R.id.hv_head);
        wv_web = (WebView)findViewById(R.id.wv_web);
        ll_down = (LinearLayout) findViewById(R.id.ll_down);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中……");
    }

    private void initEvent(){
        headView.setLeftLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headView.setRightLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(TextUtils.isEmpty(downUrl)){
                if(downUrl.size()==0){

                    MyToastUtils.showToastShort(WebViewHtmlActivity.this,"暂无下载");
                }else{
//                    if(downUrl.size()==1){
//                        downFile(0);
//                    }else{
//                        initDownDialog();
//                    }
                    showDownLayout();
                }
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

    private void showDownLayout(){

        ll_down.removeAllViews();

        for (final String name:downName){
//            String name = downName.get(position);
            View view = View.inflate(this,R.layout.item_dialog_marquee_textview,null);
            MarqueeTextView mv_item = (MarqueeTextView)view.findViewById(R.id.mv_item);
//            mv_item.setSpeed(3);
            mv_item.setText(name);
//
            mv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InOutDownView(0);
                    int position = downName.indexOf(name);
                    downFile(position);
                }
            });

            ll_down.addView(view);
        }

//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_down_movie_in);
//        ll_down.startAnimation(animation);
        InOutDownView(1);
    }

    private void InOutDownView(final int isIn){
        Animation animation;
        if(isIn==1){
            animation = AnimationUtils.loadAnimation(this,R.anim.anim_down_movie_in);
//            ll_down.startAnimation(animation);
//            ll_down.setVisibility(View.VISIBLE);
        }else{
            animation = AnimationUtils.loadAnimation(this,R.anim.anim_down_movie_out);
//            ll_down.startAnimation(animation);
//            ll_down.setVisibility(View.GONE);
        }

        animation.setFillBefore(false);
        animation.setFillAfter(true);
        animation.setStartOffset(0);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(isIn==1){
                    ll_down.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isIn!=1){
                    ll_down.setVisibility(View.GONE);
                }
                ll_down.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        ll_down.setAnimation(animation);
//        ll_down.clearAnimation();
        ll_down.startAnimation(animation);

    }

    private void initDownDialog(){
        if(commonDialog!=null){
//            addDialogView();
            commonDialog.show();
            dialogAdapter.notifyDataSetChanged();
            return;
        }else {

            commonDialog = new CommonDialog(this, R.style.commonDialog, R.layout.dialog_list);
//            LinearLayout ll_dialog = commonDialog.getThisView(R.id.ll_dialog);
//            addDialogView();
            commonDialog.setCancelable(true);
            commonDialog.setCanceledOnTouchOutside(true);
            commonDialog.show();
            ListView lv_dialog = commonDialog.getThisView(R.id.lv_dialog);
            dialogAdapter = new SmartAdapter<String>(this, downName, R.layout.item_dialog_marquee_textview) {
                @Override
                protected void setView(ViewHolder viewHolder, String item) {

                    MarqueeTextView mv_item = viewHolder.GetView(R.id.mv_item);
                    mv_item.setSpeed(3);
                    mv_item.setText(item);
                }
            };

            lv_dialog.setAdapter(dialogAdapter);

            lv_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    downFile(position);
                    commonDialog.dismiss();
                }
            });
            dialogAdapter.notifyDataSetChanged();
            commonDialog.show();
        }
    }

    private void addDialogView(){
        commonDialog.setCancelable(true);
        commonDialog.setCanceledOnTouchOutside(true);
        commonDialog.show();
//        LinearLayout ll_dialog = commonDialog.getThisView(R.id.ll_dialog);
        LinearLayout ll_dialog = (LinearLayout)commonDialog.findViewById(R.id.ll_dialog);
//        int leng = downName.size();
        for (final String name:downName){
//            String name = downName.get(position);
            View view = View.inflate(this,R.layout.item_dialog_marquee_textview,null);
//            TextView tv_item = (TextView)view.findViewById(R.id.tv_item);
//            tv_item.setText("0000111112223333");
            MarqueeTextView mv_item = (MarqueeTextView)view.findViewById(R.id.mv_item);
            mv_item.setSpeed(3);
            mv_item.setText(name);
//            mv_item.setText("456465464546");
//
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    int position = downName.indexOf(name);
//                    downFile(position);
                    commonDialog.dismiss();
                }
            });

            ll_dialog.addView(view);
        }
    }

    //TODO 下载Dialog
    private ProgressDialog progressDown;

    /**
     * 下载
     * @param position 资源位置
     */
    private void downFile(int position){

        String url = downUrl.get(position);
        if(TextUtils.isEmpty(url)){
            MyToastUtils.showToastLong(this,"下载地址为空");
            return;
        }

//        if(TextUtils.isEmpty(downName)){
//            downName = new Date().getTime()+"";
//        }
//        if(TextUtils.isEmpty(downType)){
//            downType = "torrent";
//        }

        String Dname = new Date().getTime()+"";
        String Tname = "torrent";

        String ddName=downName.get(position);
        if(!TextUtils.isEmpty(ddName)){
            Dname = ddName;
        }
        String ttName = downType.get(position);
        if(TextUtils.isEmpty(ttName)){
            Tname = ttName;
        }

//        url="http://img4.duitang.com/uploads/item/201410/23/20141023220736_srfHB.jpeg";
        progressDown = new ProgressDialog(this);
        RequestParams params = new RequestParams(url);
        params.setAutoResume(true);//设置断点续传
        params.setSaveFilePath(Common.FILE_TORRENT+Dname+"."+Tname);


        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                Log.e("onWaiting","--------onWaiting-----");
            }

            @Override
            public void onStarted() {
                Log.e("onStarted","--------onStarted-----");
            }

            @Override
            public void onLoading(long all, long now, boolean b) {

                Log.e("onLoading","--------onLoading----all-"+all+"--now-"+now+"--"+b);
                progressDown.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDown.setMessage("文件下载中……");
                progressDown.show();
                progressDown.setMax((int) all);
                progressDown.setProgress((int) now);
            }

            @Override
            public void onSuccess(File o) {
                Log.e("onSuccess","--------onSuccess-----"+o);
                MyToastUtils.showToastLong(WebViewHtmlActivity.this,"下载成功："+o.getName());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("onError","--------onError-----"+throwable+"--"+b);

                MyToastUtils.showToastLong(WebViewHtmlActivity.this,"下载失败："+throwable);
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.e("onCancelled","--------onCancelled-----"+e);
                MyToastUtils.showToastLong(WebViewHtmlActivity.this,"onCancelled："+e);
            }

            @Override
            public void onFinished() {
                Log.e("onFinished","--------onFinished-----");
                progressDown.dismiss();
//                MyToastUtils.showToastLong(WebViewHtmlActivity.this,"下载完成");
            }
        });

//        x.http().get(params, new Callback.CacheCallback<File>() {
//            @Override
//            public boolean onCache(File o) {
//                Log.e("onCache","--------onCache-----");
//                return false;
//            }
//
//            @Override
//            public void onSuccess(File o) {
//                Log.e("onSuccess","--------onSuccess-----"+o);
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//                Log.e("onError","--------onError-----"+throwable+"--"+b);
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//                Log.e("onCancelled","--------onCancelled-----"+e);
//            }
//
//            @Override
//            public void onFinished() {
//                Log.e("onFinished","--------onFinished-----");
//            }
//        });

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
