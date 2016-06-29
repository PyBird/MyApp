package com.example.feng.myapp;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.feng.myapp.naviMapDemo.MultipleRoutePlanningActivity;
import com.example.feng.myapp.test.TestAnimationsActivity;
import com.example.feng.myapp.test.TestGalleryActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    private LinearLayout ll_top_left;
    private LinearLayout ll_top_left2;
    private LinearLayout ll_top_right;
    private LinearLayout ll_top_right2;

    private int LinearHeight=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_top_left = (LinearLayout)findViewById(R.id.ll_top_left);
        ll_top_left.setOnClickListener(this);
        ll_top_left2 = (LinearLayout)findViewById(R.id.ll_top_left2);
        ll_top_left2.setOnClickListener(this);

        ll_top_right = (LinearLayout)findViewById(R.id.ll_top_right);
        ll_top_right.setOnClickListener(this);
        ll_top_right2 = (LinearLayout)findViewById(R.id.ll_top_right2);
        ll_top_right2.setOnClickListener(this);

        ll_top_left.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int uuuu = ll_top_left.getMeasuredHeight();
                initAnimation(uuuu);
                ll_top_left.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.ll_top_left:
                startActivity(new Intent(this,TestListActivity.class));
                break;
            case R.id.ll_top_right:
                startActivity(new Intent(this,TestLinearActivity.class));
                break;

            case R.id.ll_top_left2:
                startActivity(new Intent(this,TestJsoupActivity.class));
                break;
            case R.id.ll_top_right2:
//                startActivity(new Intent(this,MultipleRoutePlanningActivity.class));
//                startActivity(new Intent(this,TestActivity.class));
//                startActivity(new Intent(this,TestAnimationsActivity.class));
                startActivity(new Intent(this,TestGalleryActivity.class));
                break;
        }
    }

    private void initAnimation(int uuuu){

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();

        int height = uuuu;
        int width2 = width/2;

        TranslateAnimation translate1 = new TranslateAnimation(-width2,0,-height,0);
        translate1.setDuration(1000);
        translate1.setFillAfter(true);

        TranslateAnimation translate2 = new TranslateAnimation(width2,0,-height,0);
        translate2.setDuration(1000);
        translate2.setFillAfter(true);

        TranslateAnimation translate3 = new TranslateAnimation(-width2,0,height,0);
        translate3.setDuration(1000);
        translate3.setFillAfter(true);

        TranslateAnimation translate4 = new TranslateAnimation(width2,0,height,0);
        translate4.setDuration(1000);
        translate4.setFillAfter(true);

//        ll_top_left.setAnimation(translate1);
//        ll_top_right.setAnimation(translate2);
//        ll_top_left2.setAnimation(translate3);
//        ll_top_right2.setAnimation(translate4);

        ll_top_left.startAnimation(translate1);
        ll_top_right.startAnimation(translate2);
        ll_top_left2.startAnimation(translate3);
        ll_top_right2.startAnimation(translate4);

        translate4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ll_top_left.setEnabled(false);
                ll_top_right.setEnabled(false);
                ll_top_left2.setEnabled(false);
                ll_top_right2.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_top_left.setEnabled(true);
                ll_top_right.setEnabled(true);
                ll_top_left2.setEnabled(true);
                ll_top_right2.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
