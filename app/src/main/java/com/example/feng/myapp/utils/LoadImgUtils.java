package com.example.feng.myapp.utils;

import com.example.feng.myapp.R;

import org.xutils.image.ImageOptions;

/**
 * Created by onepeak on 2016/6/22.
 */
public class LoadImgUtils {

    /**
    * private的构造函数用于避免外界直接使用new来实例化对象
    */
    private LoadImgUtils() {
    }

    private static ImageOptions options=null;

    public synchronized static ImageOptions getImageOptions(){

        if(options == null) {

            options = new ImageOptions.Builder()
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.drawable.loading)
                    //设置加载失败后的图片
                    .setFailureDrawableId(R.drawable.logo_h)
                    //设置使用缓存
                    .setUseMemCache(true)
                    //设置显示圆形图片
                    .setCircular(false)
                    //设置支持gif
                    .setIgnoreGif(true)
                    .build();
        }

        return options;
    }
}
