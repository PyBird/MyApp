package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by tocet on 2016/8/25.
 */
public class MyLoadingLayout extends LoadingLayout {

    private AnimationDrawable mAnimationDrawable;

    public MyLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
//        mHeaderImage.setImageResource(R.drawable.animation_loading);
//        mAnimationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
        my_head_img.setImageResource(R.drawable.animation_loading);
        mAnimationDrawable = (AnimationDrawable) my_head_img.getDrawable();

    }

    @Override
    protected int getDefaultDrawableResId() {
        Log.e("MyLoadingLayout","----------getDefaultDrawableResId");
        return R.drawable.loading1;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        Log.e("MyLoadingLayout","----------onLoadingDrawableSet");
    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        Log.e("MyLoadingLayout","----------onPullImpl");
    }

    @Override
    protected void pullToRefreshImpl() {
        Log.e("MyLoadingLayout","----------pullToRefreshImpl");
    }

    @Override
    protected void refreshingImpl() {

        if(mMode== PullToRefreshBase.Mode.PULL_FROM_START){
            my_head_img.setVisibility(VISIBLE);
            ll_my_inner.setVisibility(INVISIBLE);
            mAnimationDrawable.start();
        }else{
            my_head_img.setVisibility(INVISIBLE);
            ll_my_inner.setVisibility(VISIBLE);
        }
        Log.e("MyLoadingLayout","----------refreshingImpl---mMode "+mMode+" -----Mode.PULL_FROM_START "+PullToRefreshBase.Mode.PULL_FROM_START);
    }

    @Override
    protected void releaseToRefreshImpl() {
        Log.e("MyLoadingLayout","----------releaseToRefreshImpl");
    }

    @Override
    protected void resetImpl() {
        Log.e("MyLoadingLayout","----------resetImpl---mMode "+mMode+" -----Mode.PULL_FROM_START "+PullToRefreshBase.Mode.PULL_FROM_START);
        if(mMode== PullToRefreshBase.Mode.PULL_FROM_START){
            mAnimationDrawable.stop();
        }
    }
}
