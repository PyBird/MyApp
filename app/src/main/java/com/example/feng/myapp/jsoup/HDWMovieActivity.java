package com.example.feng.myapp.jsoup;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.feng.myapp.R;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;
import com.example.feng.myapp.view.HeadView;

import java.util.ArrayList;
import java.util.Map;

public class HDWMovieActivity extends BaseActivity {

    private HeadView headView;

    private GridView gv_data;

    private SmartAdapter<Map<String,String>> smartAdapter;
    private ArrayList<Map<String,String>> htmlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdwmovie);

        initView();
        initEvent();
        getData();
    }

    private void initView(){
        headView = (HeadView)findViewById(R.id.hv_head);
        gv_data = (GridView)findViewById(R.id.gv_data);

        htmlData = new ArrayList<Map<String, String>>();
        smartAdapter = new SmartAdapter<Map<String, String>>(this,htmlData,0) {
            @Override
            protected void setView(ViewHolder viewHolder, Map<String, String> item) {

            }
        };

        gv_data.setAdapter(smartAdapter);

    }

    private void initEvent(){
        headView.setLeftLayoutOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData(){

    }
}
