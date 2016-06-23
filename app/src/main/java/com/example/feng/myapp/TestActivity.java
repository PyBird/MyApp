package com.example.feng.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.feng.myapp.view.MarqueeTextView;

public class TestActivity extends Activity {

    private TextView tv_roll;
    private MarqueeTextView mt_roll;
    private MarqueeTextView mt_roll1;
    private MarqueeTextView mt_roll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tv_roll = (TextView)findViewById(R.id.tv_roll);
        mt_roll = (MarqueeTextView)findViewById(R.id.mt_roll);
        mt_roll1 = (MarqueeTextView)findViewById(R.id.mt_roll1);
        mt_roll2 = (MarqueeTextView)findViewById(R.id.mt_roll2);

//        ViewGroup.MarginLayoutParams margin1 = new ViewGroup.MarginLayoutParams(mt_roll2.getLayoutParams());
//        margin1.setMargins(100, 200, 0, 0);//设置滚动区域位置：在左边距400像素，顶边距10像素的位置
//        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(margin1);
//        layoutParams1.height = 240;//设滚动区域高度
//        layoutParams1.width = 1000; //设置滚动区域宽度
//        mt_roll2.setLayoutParams(layoutParams1);
//        mt_roll2.setScrollWidth(1000);
//        mt_roll2.setCurrentPosition(100 + 1000);//设置滚动信息从滚动区域的右边出来
//        mt_roll2.setSpeed(2);

        mt_roll.setText("15647897978979756456JKSAJGKSHGHSHGJ4587874545KLLKSJHG,,,,,,jkjkjk,,,,,");
        mt_roll1.setSpeed(10);
        mt_roll1.setText("15647897978979756456JKSAJGKSHGHSHGJ4587874545KLLKSJHG,,,,,,jkjkjk,,,,,");
        mt_roll2.setSpeed(5);
        mt_roll2.setText("15647897978979756456JKSAJGKSHGHSHGJ4587874545KLLKSJHG,,,,,,jkjkjk,,,,,");

        mt_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
