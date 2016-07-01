package com.example.feng.myapp.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ant.liao.GifView;
import com.example.feng.myapp.R;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.view.LoadingGifDialog;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class TestLoadingActivity extends BaseActivity {

    @ViewInject(R.id.ll_start) private LinearLayout ll_start;
    @ViewInject(R.id.ll_end) private LinearLayout ll_end;

    @ViewInject(R.id.iv_img) private ImageView iv_img;
    @ViewInject(R.id.gv_img) private GifView gv_img;

    int p=0;

    int[] imgIds={R.drawable.loading_bear,R.drawable.loading_cat,R.drawable.loading_man};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loading);

        x.view().inject(this);

        initEvent();
    }

    private void initEvent(){

        ll_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p++;
                int img = p%imgIds.length;
                setLoadingImg(img);
            }
        });

        ll_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadingGifDialog loadingGifDialog = new LoadingGifDialog(TestLoadingActivity.this);
                loadingGifDialog.setDialogMessage("加载中……");
                loadingGifDialog.show();
            }
        });
    }

    private void setLoadingImg(int img){

//        ImageOptions.Builder imageOptions = new ImageOptions.Builder();
//        imageOptions.setIgnoreGif(true);
//
////        x.image().loadDrawable(imageOptions);
//        iv_img.setImageResource(img);

//        Picasso.with(this).load(imgIds[img]).into(iv_img);

//        // 设置Gif图片源
//        gf1.setGifImage(R.drawable.gif1);
//        // 添加监听器
//        gf1.setOnClickListener(this);
//        // 设置显示的大小，拉伸或者压缩
//        gf1.setShowDimension(300, 300);
//        // 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
//        gf1.setGifImageType(GifImageType.COVER);

        gv_img.setGifImage(imgIds[img]);
//        gv_img.setGifImageType(GifView.GifImageType.COVER);

    }
}
