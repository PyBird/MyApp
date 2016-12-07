package com.example.feng.myapp.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import org.xutils.x;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
