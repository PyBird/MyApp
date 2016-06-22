package com.example.feng.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.common.Common;
import com.example.feng.myapp.utils.MyToastUtils;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

public class TestListActivity extends BaseActivity {

    private SmartAdapter<Map<String,String>> smartAdapter;
    private ArrayList<Map<String,String>> listDatas=new ArrayList<Map<String,String>>();

    private Context context;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("数据加载中……");
        progressDialog.setMessage("数据加载中……");

        findViewById(R.id.bt_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDatas.clear();
                smartAdapter.notifyDataSetChanged();
                getData();
            }
        });
        context = this;
        initView();
        initData();
    }

    private void initView(){

//        Map<String,String> map = new HashMap<String, String>();
//        map.put("img","uiuiuouiuo");
//        map.put("name","你好你好你好");
//        listDatas.add(map);
//
//        map = new HashMap<String, String>();
//        map.put("img","uiuiuouiuo--1");
//        map.put("name","你好你好你好--1");
//        listDatas.add(map);
//
//        map = new HashMap<String, String>();
//        map.put("img","uiuiuouiuo--2");
//        map.put("name","你好你好你好--2");
//        listDatas.add(map);

        final ImageOptions options = new ImageOptions.Builder()
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

        ListView lv_test = (ListView)findViewById(R.id.lv_test);
        smartAdapter = new SmartAdapter<Map<String, String>>(this,listDatas,R.layout.item_test_list) {
            @Override
            protected void setView(ViewHolder viewHolder, Map<String, String> item) {

                View hover = LayoutInflater.from(context).inflate(R.layout.item_view, null);

                ImageView iv_img = viewHolder.GetView(R.id.iv_img);
//                x.image().bind(iv_img,item.get("img"),options);
                iv_img.setImageResource(R.drawable.ic_launcher);

                BlurLayout blur_layout = viewHolder.GetView(R.id.blur_layout);
                blur_layout.removeAllViews();
                blur_layout.addView(iv_img);
                blur_layout.setHoverView(hover);
                blur_layout.setBlurDuration(1000);

                TextView tv_txt = (TextView)hover.findViewById(R.id.tv_txt);
                tv_txt.setText(item.get("name"));

                blur_layout.addChildAppearAnimator(hover, R.id.tv_txt, Techniques.DropOut,1200);

                blur_layout.addChildDisappearAnimator(hover, R.id.tv_txt, Techniques.FadeOutUp);
            }
        };

        lv_test.setAdapter(smartAdapter);
    }

    private void initData(){

        getData();
    }

    private void getData(){

        progressDialog.show();
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
                        listDatas.addAll(map2);

                        Log.e("listDatas","---------------------"+listDatas);

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
                smartAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                Log.e("onFinished","--------------------------onFinished-----");
            }
        });
    }
}
