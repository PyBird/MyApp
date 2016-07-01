package com.example.feng.myapp.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryFlow extends Gallery
{
    private Camera mCamera = new Camera();
    private int mMaxRotationAngle = 150;
    private int mMaxZoom = -100;
    private int mCoveflowCenter;

    public GalleryFlow(Context context)
    {
        super(context);
        this.setStaticTransformationsEnabled(true);
    }

    public GalleryFlow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
    }

    public GalleryFlow(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.setStaticTransformationsEnabled(true);
    }

    public int getMaxRotationAngle()
    {
        return mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle)
    {
        mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom()
    {
        return mMaxZoom;
    }

    public void setMaxZoom(int maxZoom)
    {
        mMaxZoom = maxZoom;
    }

    private int getCenterOfCoverflow()
    {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    private static int getCenterOfView(View view)
    {
        return view.getLeft() + view.getWidth() / 2;
    }

    protected boolean getChildStaticTransformation(View child, Transformation t)
    {
//        final int childCenter = getCenterOfView(child);
//        final int childWidth = child.getWidth();
//        int rotationAngle = 0;
//
//        t.clear();
//        t.setTransformationType(Transformation.TYPE_MATRIX);
//
//        if (childCenter == mCoveflowCenter)
//        {
//            transformImageBitmap((ImageView) child, t, 0);
//        } else
//        {
//            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
//            if (Math.abs(rotationAngle) > mMaxRotationAngle)
//            {
//                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
//            }
//            transformImageBitmap((ImageView) child, t, rotationAngle);
//        }

        final int childCenter = getCenterOfView(child) + offsetChildrenLeftAndRight();
        final int childWidth = child.getWidth();
        int rotationAngle = 0;

        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        if (childCenter == mCoveflowCenter)
        {
            transformImageBitmap((ImageView) child, t, 0);
        } else
        {
            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
            if (Math.abs(rotationAngle) > mMaxRotationAngle)
            {
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
            }
            transformImageBitmap((ImageView) child, t, rotationAngle);
        }

        return true;
    }

    private int offsetChildrenLeftAndRight()
    {
        int offset = 0;
        for (int i = getChildCount() - 1; i >= 0; i--)
        {

            getChildAt(i).offsetLeftAndRight(offset);

            if (android.os.Build.VERSION.SDK_INT >= 16)
                getChildAt(i).invalidate();
        }
        return offset;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle)
    {
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final int rotation = Math.abs(rotationAngle);

        // 在Z轴上正向移动camera的视角，实际效果为放大图片。
        // 如果在Y轴上移动，则图片上下移动；X轴上对应图片左右移动。
        mCamera.translate(0.0f, 0.0f, 100.0f);
//        mCamera.translate(0.0f, 0.0f, 50.0f);

        if (rotation < mMaxRotationAngle)
        {
            float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
            mCamera.translate(0.0f, 0.0f, zoomAmount);
            Log.e("zoomAmount","---------"+zoomAmount+"-----rotationAngle-"+rotationAngle);
        }

        if(rotationAngle>0){
            mCamera.rotateY(-10);// rotationAngle 为正，沿y轴向内旋转； 为负，沿y轴向外旋转
        }
        else if(rotationAngle<0){
            mCamera.rotateY(10);
        }

        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        mCamera.restore();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
//         return super.onFling(e1, e2, velocityX/1.5f, velocityY);
        return false;
    }
}