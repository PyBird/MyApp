package com.example.feng.myapp.view;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.example.feng.myapp.R;

/**
 * Created by onepeak on 2016/6/30.
 */
public class LoadingGifDialog extends ProgressDialog {

    private View mConvertView=null;
    int layoutRes=R.layout.dialog_loading_gif;//布局文件
    Context context;

    public LoadingGifDialog(Context context) {
        super(context);

        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutRes, null, false);

        setGifImg();
    }

    public LoadingGifDialog(Context context, int theme) {
        super(context,theme);

        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutRes, null, false);

        setGifImg();
    }

    public LoadingGifDialog(Context context,String title,String message) {
        super(context);

        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutRes, null, false);

        setGifImg();

        setDialogTitle(title);
        setDialogMessage(message);

    }

    public void setGifImg(){

        GifView gv_img = (GifView)mConvertView.findViewById(R.id.gv_img);
        gv_img.setLayoutParams(new LinearLayout.LayoutParams(77,111));
        gv_img.setShowDimension(77,111);
        gv_img.setGifImage(R.drawable.loading_man);
    }

    public void setDialogTitle(String title){

        TextView tv_title = (TextView)mConvertView.findViewById(R.id.tv_title);
        if(TextUtils.isEmpty(title)){
            tv_title.setVisibility(View.GONE);
        }else{
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }

    }

    public void setDialogMessage(String message){

        TextView tv_message = (TextView)mConvertView.findViewById(R.id.tv_message);
        if(TextUtils.isEmpty(message)){
            tv_message.setVisibility(View.GONE);
        }else{
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText(message);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(mConvertView);
//        this.setContentView(layoutRes);
//        this.setView(mConvertView);
    }

//    public LoadingGifDialog(Context context, int theme) {
//        super(context, theme);
//    }
//
//    public static LoadingGifDialog show(Context context, CharSequence title,
//                                        CharSequence message) {
//        return show(context, title, message, false);
//    }
//
//    public static LoadingGifDialog show(Context context, CharSequence title,
//                                        CharSequence message, boolean indeterminate) {
//        return show(context, title, message, indeterminate, false, null);
//    }
//
//    public static LoadingGifDialog show(Context context, CharSequence title,
//                                        CharSequence message, boolean indeterminate, boolean cancelable) {
//        return show(context, title, message, indeterminate, cancelable, null);
//    }
//
//    public static LoadingGifDialog show(Context context, CharSequence title,
//                                        CharSequence message, boolean indeterminate,
//                                        boolean cancelable, OnCancelListener cancelListener) {
//        LoadingGifDialog dialog = new LoadingGifDialog(context);
//        dialog.setTitle(title);
//        dialog.setMessage(message);
//        dialog.setIndeterminate(indeterminate);
//        dialog.setCancelable(cancelable);
//        dialog.setOnCancelListener(cancelListener);
//        dialog.show();
//        return dialog;
//    }
}
