package com.example.feng.myapp.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by onepeak on 2016/7/1.
 */
public class GalleryFlowAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImageIds;

    public GalleryFlowAdapter(Context c, int[] ImageIds) {
        mContext = c;
        mImageIds = ImageIds;
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 加载图片
     *
     * @param position
     * @return
     */
    private ImageView getImageView(int position) {
        final int reflectionGap = 4;
        Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), mImageIds[position % mImageIds.length]);
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint deafaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

        paint.setShader(shader);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmapWithReflection);
        imageView.setLayoutParams(new GalleryFlow.LayoutParams(450, 261));
//        imageView.setLayoutParams(new GalleryFlow.LayoutParams(GalleryFlow.LayoutParams.WRAP_CONTENT, GalleryFlow.LayoutParams.WRAP_CONTENT));
//        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageResource(mImageIds[position % mImageIds.length]);
        return imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageView(position);
    }

    public float getScale(boolean focused, int offset) {
        return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
    }
}
