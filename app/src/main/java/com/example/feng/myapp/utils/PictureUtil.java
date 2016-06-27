package com.example.feng.myapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

/**
 * 图片压缩在100K以内还不失真
 */
public class PictureUtil {

	private static String folderName="/freecar/";

	/**
	 * 把bitmap转换成String
	 * @param filePath
	 * @return
     */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 计算图片的缩放值
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
     * @return
     */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}


    /**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * @param filePath
	 * @return
     */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 600, 600);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据路径删除图片
	 * @param path
     */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 添加到图库
	 * @param context
	 * @param path
     */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取保存图片的目录
	 * @return
     */
	public static File getAlbumDir() {
		File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getAlbumName());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 * @return
     */
	public static String getAlbumName() {
		return folderName;
	}//"folderName"

	/**
	 * 保存Bitmap到File
	 * @param mBitmap
	 * @param bitName
     */
	public static File saveBitmapToFile(Bitmap mBitmap,String bitName)  {

		File file = new File(Environment.getExternalStorageDirectory().getPath() +folderName);
		if (!file.exists()) {
			file.mkdirs();
		}

		int quality = 50;
		File photoFile = new File( file+"/"+bitName + ".jpg");
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(photoFile);
			mBitmap.compress(CompressFormat.JPEG, quality, fOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return photoFile;
	}

	/**
	 * 保存Bitmap到File
	 * @param mBitmap
	 * @param bitName
	 * @param quality 质量压缩比 1~100 100为不压缩
     * @return
     */
	public static File saveBitmapToFile(Bitmap mBitmap,String bitName,int quality)  {

		File file = new File(Environment.getExternalStorageDirectory().getPath() +folderName);
		if (!file.exists()) {
			file.mkdirs();
		}

//		int quality = 50;
		File photoFile = new File( file+"/"+bitName + ".jpg");
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(photoFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
		mBitmap.compress(CompressFormat.PNG, quality, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return photoFile;
	}

	/**
	 * 通过Uri获取图片地址
	 * @param uri
	 * @return
     */
	public static String getPathByUri(Context context,Uri uri){

		if(uri ==null){
			return "";
		}
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri,filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();

		return picturePath;
	}

	/**
	 * Bitmap 转 Uri
	 * @param context
	 * @param bitmap
     * @return
     */
	public static Uri bitmapToUri(Context context,Bitmap bitmap){

		// Bitmap 转 Uri
		Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));

		return uri;
	}

	/**
	 * 拍照返回获取图片File
	 * @return
     */
	public static File getFileByTakePicture(Context context,Intent data){

		if(null == data){
			return null;
		}
		Bundle bundle = data.getExtras();
		if(null == bundle){
			return null;
		}
		Bitmap bitmap = (Bitmap)bundle.get("data");
		Uri uri=null;
		if(bitmap != null){
			uri = PictureUtil.bitmapToUri(context,bitmap);
		}

		if(uri==null){
			return null;
		}

		String picturePath = PictureUtil.getPathByUri(context,uri);
		String fileName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
		Bitmap b2= PictureUtil.getSmallBitmap(picturePath);

		File file = PictureUtil.saveBitmapToFile(b2,fileName,80);
		//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
		bitmap.recycle();
		b2.recycle();
		return file;
	}

	/**
	 * 本地图片选择返回获取图片File
	 * @return
	 */
	public static File getFileBySelectPhoto(Context context,Intent data){

		if(data==null){
			return null;
		}

		Uri uri = data.getData();
		if(uri==null){
			return null;
		}

//		ContentResolver cr = context.getContentResolver();
//		Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//		Log.e("getByteCount","----1-----"+bitmap.getRowBytes() * bitmap.getHeight());

		String picturePath = PictureUtil.getPathByUri(context,uri);
		String fileName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
		Bitmap b2= PictureUtil.getSmallBitmap(picturePath);
//		photoNegative = PictureUtil.saveBitmapToFile(b2,fileName);

		File file = PictureUtil.saveBitmapToFile(b2,fileName,80);
		//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
		b2.recycle();
		return file;
	}

	/**
	 * 指定文件拍照
	 * 返回用Uri处理
	 */
	public void takePhotoByFile(){

//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//
//			photoPositive = new File(Environment.getExternalStorageDirectory(), new Date()+".jpg");
//			Uri uri = Uri.fromFile(photoPositive);
//			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//			startActivityForResult(intent,TAKE_PICTURE_P+requestCode);
//		} else {
//			MyToastUtils.showToastShort(PerfectInformationActivity.this,"未找到存储卡，无法存储照片！");
//		}
	}
}
