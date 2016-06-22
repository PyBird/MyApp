package com.example.feng.myapp.base;

import android.app.Activity;
import android.os.Bundle;

import org.xutils.x;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化xutils
        x.Ext.init(getApplication());
        x.Ext.setDebug(true);
    }
}
