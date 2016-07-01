package com.example.feng.myapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.feng.myapp.R;

public class TestAnimationsActivity extends Activity {

    private ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animations);

        iv_img = (ImageView)findViewById(R.id.iv_img);

//        AnimationSet animationSet = new AnimationSet(true);
//        //渐变
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
//        alphaAnimation.setDuration(2000);//持续时间
//        animationSet.addAnimation(alphaAnimation);
//
//        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,Animation.RELATIVE_TO_SELF);
//        rotateAnimation.setDuration(2000);//持续时间
//
//        animationSet.addAnimation(rotateAnimation);
//
//        animationSet.setFillAfter(true);
//
//        iv_img.startAnimation(animationSet);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
//        animation.setStartTime(1000);
//        iv_img.startAnimation(animation);
        animation.setFillBefore(true);
        animation.setInterpolator(new AnticipateInterpolator());
        iv_img.setAnimation(animation);

        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setDelay(500);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
