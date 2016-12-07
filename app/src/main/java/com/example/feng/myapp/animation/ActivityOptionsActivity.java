package com.example.feng.myapp.animation;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.feng.myapp.R;
import com.example.feng.myapp.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_activity_options)
public class ActivityOptionsActivity extends BaseActivity {

    @ViewInject(R.id.iv_img) private ImageView ivImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_activity_options);\

    }
}
