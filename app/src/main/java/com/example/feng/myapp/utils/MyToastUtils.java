package com.example.feng.myapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by onepeak on 2016/5/13.
 * 解决Toast重复弹出
 */
public class MyToastUtils {

    private static String oldMsg;
    protected static Toast toast   = null;
    private static long oneTime=0;
    private static long twoTime=0;

    public static void showToastShort(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }

    public static void showToastShort(Context context, int resId){
        showToastShort(context, context.getString(resId));
    }

    public static void showToastLong(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, Toast.LENGTH_LONG);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_LONG){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }

    public static void showToastLong(Context context, int resId){
        showToastLong(context, context.getString(resId));
    }
}
