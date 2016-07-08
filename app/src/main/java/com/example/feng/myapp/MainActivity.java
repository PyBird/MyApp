package com.example.feng.myapp;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.naviMapDemo.MultipleRoutePlanningActivity;
import com.example.feng.myapp.test.TestAnimationsActivity;
import com.example.feng.myapp.test.TestBluetoothActivity;
import com.example.feng.myapp.test.TestGalleryActivity;
import com.example.feng.myapp.test.TestLoadingActivity;
import com.example.feng.myapp.test.TestPropertyAnimationActivity;
import com.example.feng.myapp.test.TwoSidedViewActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_top_left;
    private LinearLayout ll_top_left2;
    private LinearLayout ll_top_right;
    private LinearLayout ll_top_right2;

    private LinearLayout ll_center_left;
    private LinearLayout ll_center_right;

    private int LinearHeight=-1;

    private int timeDo=800;

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

        ll_center_left = (LinearLayout)findViewById(R.id.ll_center_left);
        ll_center_left.setOnClickListener(this);
        ll_center_right = (LinearLayout)findViewById(R.id.ll_center_right);
        ll_center_right.setOnClickListener(this);

        ll_top_left.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                LinearHeight = ll_top_left.getMeasuredHeight();
                initAnimation(LinearHeight);
                ll_top_left.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        if(LinearHeight>0){
            initAnimation(LinearHeight);
        }
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
//                startActivity(new Intent(this,TestJsoupActivity.class));
                startActivity(new Intent(this,TwoSidedViewActivity.class));
                break;

            case R.id.ll_top_right2:
//                startActivity(new Intent(this,MultipleRoutePlanningActivity.class));
//                startActivity(new Intent(this,TestActivity.class));
//                startActivity(new Intent(this,TestAnimationsActivity.class));
                startActivity(new Intent(this,TestGalleryActivity.class));
                break;

            case R.id.ll_center_left:
                startActivity(new Intent(this,TestPropertyAnimationActivity.class));
                break;

            case R.id.ll_center_right:
//                startActivity(new Intent(this,TestLoadingActivity.class));
                startActivity(new Intent(this,TestBluetoothActivity.class));
                break;
        }
    }

    public void initAnimation(int uuuu){

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();

        int height = uuuu;
        int width2 = width/2;

        TranslateAnimation translate1 = new TranslateAnimation(-width2,0,-height,0);
        translate1.setDuration(timeDo);
        translate1.setFillAfter(true);

        TranslateAnimation translate2 = new TranslateAnimation(width2,0,-height,0);
        translate2.setDuration(timeDo);
        translate2.setFillAfter(true);

        TranslateAnimation translate3 = new TranslateAnimation(-width2,0,height,0);
        translate3.setDuration(timeDo);
        translate3.setFillAfter(true);

        TranslateAnimation translate4 = new TranslateAnimation(width2,0,height,0);
        translate4.setDuration(timeDo);
        translate4.setFillAfter(true);

//        ll_top_left.setAnimation(translate1);
//        ll_top_right.setAnimation(translate2);
//        ll_top_left2.setAnimation(translate3);
//        ll_top_right2.setAnimation(translate4);

//        translate1.setStartTime(0);
//        translate2.setStartTime(0);
        ll_top_left.startAnimation(translate1);
        ll_top_right.startAnimation(translate2);
        ll_top_left2.startAnimation(translate3);
        ll_top_right2.startAnimation(translate4);

        propertyValuesHolder(ll_center_left);
        propertyValuesHolder(ll_center_right);


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

    public void propertyValuesHolder(View view)
    {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);

        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(timeDo).start();
    }
}
