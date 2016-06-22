package com.example.feng.myapp.utils;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by onepeak on 2016/6/22.
 */
public class GetLoadImgSize extends Activity {

    private WindowManager wm = this.getWindowManager();

    //获取屏幕宽高
    int width = wm.getDefaultDisplay().getWidth();
    int height = wm.getDefaultDisplay().getHeight();

    private static LinearLayout.LayoutParams layoutParams=null;

    /**
     * 获取电影图片宽高
     * @return
     */
//    public static LinearLayout.LayoutParams getGrideImgPatams(int imgW,int imgH){
//
////        int imgW=140;
////        int imgH=160;
//        if(layoutParams==null){
//            layoutParams = new LinearLayout.LayoutParams(width/2,imgH/imgW*width);
//        }
//
//        return layoutParams;
//    }

}
