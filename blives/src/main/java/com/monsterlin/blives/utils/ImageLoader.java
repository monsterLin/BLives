package com.monsterlin.blives.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用于处理图片的加载
 * @author monster
 *
 */
public class ImageLoader {
	private ImageView mImageView;
	private String mUrl;
	private LruCache<String, Bitmap> mCaches ;  //用户图片的缓存
	
	public ImageLoader(){
		int maxMemory=(int) Runtime.getRuntime().maxMemory();  //获取最大可用内存
		int cacheSize=maxMemory/4;  //缓存的大小
		mCaches=new LruCache<String,Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//在每次存入缓存的时候调用
				return value.getByteCount(); //告诉系统，存入的图片的大小
			}
		};
	}
	/**
	 * 把bitmap加入到缓存中
	 * @param url
	 * @param bitmap
	 */
	public void addBitmapToCache(String url,Bitmap bitmap){
		if(getBitmapFromCache(url)==null){
			mCaches.put(url, bitmap);
		}
	}
	
	/**
	 * 把图片从缓存中取出来
	 * @param url
	 * @return bitmap
	 */
	public Bitmap getBitmapFromCache(String url){
		return mCaches.get(url);
	}
	/**
	 * UI主线程
	 */
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg); 
			//通过设置tag属性避免缓存图片对正确图片的影响
			if(mImageView.getTag().equals(mUrl)){
				mImageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};
	/**
	 * 通过多线程的方式加载图片
	 * @param imageView
	 * @param url
	 */
	public void showImageByThread(ImageView imageView,final String url){
		mImageView=imageView; //将ImageView保存进成员变量中
		mUrl=url;
		new Thread(){
			@Override
			public void run() {
				super.run();
				Bitmap bitmap=getBitmapFromURL(url);
				Message message=Message.obtain();
				message.obj=bitmap;
				mHandler.sendMessage(message); //将内容发送到Handle线程中
			}
		}.start();
	}
	/**
	 * 通过url得到bitmap
	 * @param urlString
	 * @return bitmap
	 */
	public Bitmap getBitmapFromURL(String urlString){
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url=new URL(urlString);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection(); //打开链接   //注意是：HttpURLConnection而不是HttpsURLConnection
			is=new BufferedInputStream(connection.getInputStream());
			bitmap=BitmapFactory.decodeStream(is); //将这个流转换为bitmap
			connection.disconnect(); //资源释放
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 通过AsyncTask的方式异步加载图片
	 * @param imageView
	 * @param url
	 */
	public void showImageByAsyncTask(ImageView imageView,String url){
		Bitmap bitmap=getBitmapFromCache(url);  //从缓存中取出图片
		if(bitmap==null){
			new NewsAsyncTask(imageView,url).execute(url);	
		}else{
			imageView.setImageBitmap(bitmap);
		}
		
	}
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{
		private ImageView mImageView;
		private String mUrl;
		public NewsAsyncTask(ImageView imageView,String url){
			mImageView=imageView;
			mUrl=url;
		}
		/**
		 * 从网络中获取图片，如果图片已经下载，则加入到缓存
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			String url=params[0];
			Bitmap bitmap=getBitmapFromURL(url);
			if(bitmap!=null){
				addBitmapToCache(url, bitmap);
			}
			return bitmap ;
		}
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if(mImageView.getTag().equals(mUrl)){
				 mImageView.setImageBitmap(bitmap);	
			}
		}
	}
}
//修改于:2015年10月7日
