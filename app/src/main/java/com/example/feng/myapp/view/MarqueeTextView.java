package com.example.feng.myapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 水平滚动TextView
 * Created by onepeak on 2016/6/23.
 */
public class MarqueeTextView extends TextView {

    /** 是否停止滚动 */
    private boolean mStopMarquee;
    private String mText;
    private float mCoordinateX;//当前滚动位置
    private float mTextWidth;//文本宽度
    private int mScrollWidth = 0;//滚动区域宽度(下次滚动的开始位置)
    private int speed = 2;//滚动速度

    private int mTextLineHeight=42;

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getScrollWidth() {
        return mScrollWidth;
    }

    /**
     * 设置滚动区域宽度
     * @param mScrollWidth (-1为当前屏幕宽度（在setText之后执行）)
     */
    public void setScrollWidth(int mScrollWidth) {
        this.mScrollWidth = mScrollWidth;
    }

    public void setText(String text) {
        this.mText = text;
        mTextWidth = getPaint().measureText(mText);
        mTextLineHeight = this.getLineHeight();

//        Log.e("setText","-------------"+mText+"-------mTextLineHeight:"+mTextLineHeight+"------mTextWidth-"+mTextWidth);

        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }


    @SuppressLint("NewApi")
    @Override
    protected void onAttachedToWindow() {
        mStopMarquee = false;
        if (!(mText == null || mText.isEmpty()))
            mHandler.sendEmptyMessageDelayed(0, 2000);
        super.onAttachedToWindow();
    }


    @Override
    protected void onDetachedFromWindow() {
        mStopMarquee = true;
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }


    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(mText == null || mText.isEmpty()))
            canvas.drawText(mText, mCoordinateX, mTextLineHeight-8, getPaint());
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    if (Math.abs(mCoordinateX) > (mTextWidth + 5)) {
//                        mCoordinateX = 0;
                    if (mCoordinateX < (-mTextWidth)) {//文字滚动完了，从滚动区域的右边出来
                        mCoordinateX = mScrollWidth;
                        invalidate();
                        if (!mStopMarquee) {
                            sendEmptyMessageDelayed(0,500);
                        }
                    } else {
                        mCoordinateX -= speed;
                        invalidate();
                        if (!mStopMarquee) {
                            sendEmptyMessageDelayed(0, 30);
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
