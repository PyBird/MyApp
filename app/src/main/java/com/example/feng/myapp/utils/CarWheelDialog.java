package com.example.feng.myapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feng.myapp.R;

/**
 * Created by one on 2016/10/10.
 */

public class CarWheelDialog extends Dialog {

    private ImageView imageView;
    private TextView textView;
    private RotateAnimation animation;

    public CarWheelDialog(Context context) {
        super(context);
        initView();
    }

    public CarWheelDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    private void initView(){

        setContentView(R.layout.dialog_loading_car);
        imageView = (ImageView)findViewById(R.id.imageView);
        textView = (TextView)findViewById(R.id.textView);

        initAnimation();
    }

    private void initAnimation(){

        animation = new RotateAnimation(0, 360, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        animation.setDuration(3000);
        animation.setRepeatCount(Animation.INFINITE);
//        animation.setRepeatMode(Animation.RESTART);
//        animation.setStartTime(Animation.START_ON_FIRST_FRAME);
    }

    @Override
    public void show(){
        super.show();
        if(imageView != null && animation != null){
            imageView.startAnimation(animation);
        }
    }

    @Override
    public void dismiss(){
        super.dismiss();
        if(animation != null){
            animation.cancel();
        }
        if(imageView != null){
            imageView.clearAnimation();
        }
    }

    public void setMessage(String message){

        if(textView != null && !TextUtils.isEmpty(message)){
            textView.setText(message);
        }
    }
}
