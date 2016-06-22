package com.example.feng.myapp.jsoup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.feng.myapp.R;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.utils.jsoup.DocumentUtils;
import com.example.feng.myapp.utils.jsoup.GetHtmlData;

import org.jsoup.nodes.Document;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

public class SinaNewsActivity extends BaseActivity {

    private LinearLayout ll_linear;

    private Context context;
    private ProgressDialog progressDialog;

    private ImageOptions options;

    private Document document;
    private ArrayList<Map<String,String>> htmlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_linear);

        context=this;
        progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("数据加载中……");
        progressDialog.setMessage("数据加载中……");
        progressDialog.setCancelable(false);


        ll_linear = (LinearLayout)findViewById(R.id.ll_linear);

        findViewById(R.id.bt_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        initView();
        getData();
    }

    private void initView(){

        options = new ImageOptions.Builder()
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
    }

    private void getData(){

//        progressDialog.show();
        new Thread(runnable).start();

    }

    private void addLinearView(ArrayList<Map<String,String>> map){

        ll_linear.removeAllViews();
        for(Map<String,String> item:map){

            View linearView = LayoutInflater.from(context).inflate(R.layout.item_test_list, null);
            View hover = LayoutInflater.from(context).inflate(R.layout.item_view, null);

            ImageView imageView = (ImageView)linearView.findViewById(R.id.iv_img);
            x.image().bind(imageView,item.get("img"),options);

            TextView tv_txt = (TextView)hover.findViewById(R.id.tv_txt);
            tv_txt.setText(item.get("title"));

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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            document = DocumentUtils.getDocumentFromUrl("http://news.sina.com.cn/");

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("status",1);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message message){

            htmlData = new ArrayList<Map<String, String>>();
            htmlData.addAll(GetHtmlData.getSinaNewsData(document));
            addLinearView(htmlData);
        }
    };
}
