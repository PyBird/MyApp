package com.example.feng.myapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by onepeak on 2016/4/25.
 * 自定义Dialog
 */
public class CommonDialog extends Dialog {

    private View mConvertView=null;
    int layoutRes;//布局文件
    Context context;

    private SparseArray<View> mViewList = new SparseArray<View>();

    public CommonDialog(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 自定义布局的构造方法
     *
     * @param context
     * @param resLayout
     */
    public CommonDialog(Context context, int resLayout) {
        super(context);
        this.context = context;
        this.layoutRes = resLayout;
        mConvertView = LayoutInflater.from(context).inflate(layoutRes, null, false);
    }

    /**
     * 自定义主题及布局的构造方法
     *
     * @param context
     * @param theme
     * @param resLayout
     */
    public CommonDialog(Context context, int theme, int resLayout) {
        super(context, theme);
        this.context = context;
        this.layoutRes = resLayout;
        mConvertView = LayoutInflater.from(context).inflate(layoutRes, null, false);
    }

    /**
     * 获取主页面
     * @return
     */
    public View getDialogView(){
        return mConvertView;
    }

    /**
     * 通过ID获取控件
     *
     * @param resID 控件ID
     * @param <T>   控件类型
     * @return 返回控件
     */
    public <T extends View> T getThisView(int resID) {
        View view = mViewList.get(resID);
        if (null == view) {
            view = mConvertView.findViewById(resID);
            mViewList.put(resID, view);
        }
        return (T) view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);
    }
}
