package com.example.feng.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.common.Common;
import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.view.LoadingGifDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

public class TestLinearActivity extends BaseActivity {

    LinearLayout ll_linear;

    private Context context;
//    private ProgressDialog progressDialog;

    private LoadingGifDialog loadingGifDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_linear);

        context=this;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("数据加载中……");

        loadingGifDialog = new LoadingGifDialog(context);
        loadingGifDialog.setDialogMessage("加载中……");

        ll_linear = (LinearLayout)findViewById(R.id.ll_linear);

        findViewById(R.id.bt_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        getData();
    }

    private void getData(){

//        progressDialog.show();
        loadingGifDialog.show();
        RequestParams params = new RequestParams(Common.SERVER_URL+"getInfoData.php");

        x.http().get(params, new Callback.CacheCallback<JSONObject>() {
            @Override
            public boolean onCache(JSONObject o) {

                return false;
            }

            @Override
            public void onSuccess(JSONObject o) {

                Log.e("onSuccess","---------------------"+o);

                try{

                    String status = o.optString("status");
                    if("1".equals(status)){

                        JSONArray data = o.optJSONArray("data");

                        ObjectMapper mapper = new ObjectMapper();
//                        Map<String,Object> map = mapper.readValue(o.toString(),Map.class);
//                        ArrayList<Map<String,String>> mapList = (ArrayList<Map<String,String>>)map.get("data");

                        ArrayList<Map<String,String>> map2 = mapper.readValue(data+"",ArrayList.class);

                        addLinearView(map2);

                        Log.e("listDatas","---------------------"+map2);

                    }else{
                        MyToastUtils.showToastShort(context,o.optString("info"));
                    }

                }catch (Exception e){
                    Log.e("Exception","------Exception---------------"+o);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("onError","--------------------------onError-----"+b+"--"+throwable);
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.e("onCancelled","--------------------------onCancelled-----");
            }

            @Override
            public void onFinished() {
//                progressDialog.dismiss();
                loadingGifDialog.dismiss();
                Log.e("onFinished","--------------------------onFinished-----");
            }
        });
    }

    private void addLinearView(ArrayList<Map<String,String>> map){

        ImageOptions options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.ic_launcher)
                //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.ic_launcher)
                //设置使用缓存
                .setUseMemCache(true)
                //设置显示圆形图片
                .setCircular(false)
                //设置支持gif
                .setIgnoreGif(true)
                .build();

        ll_linear.removeAllViews();
        for(Map<String,String> item:map){

            View linearView = LayoutInflater.from(context).inflate(R.layout.item_test_list, null);
            View hover = LayoutInflater.from(context).inflate(R.layout.item_view, null);

            ImageView imageView = (ImageView)linearView.findViewById(R.id.iv_img);
            x.image().bind(imageView,item.get("img"),options);

            TextView tv_txt = (TextView)hover.findViewById(R.id.tv_txt);
            tv_txt.setText(item.get("name"));

            BlurLayout blur_layout = (BlurLayout)linearView.findViewById(R.id.blur_layout);
//        blur_layout.removeAllViews();
//        blur_layout.addView(iv_img);
            blur_layout.setHoverView(hover);
            blur_layout.setBlurDuration(1000);

            blur_layout.addChildAppearAnimator(hover, R.id.tv_txt, Techniques.DropOut,1200);

            blur_layout.addChildDisappearAnimator(hover, R.id.tv_txt, Techniques.FadeOutUp);

            ll_linear.addView(linearView);
        }

    }
}
