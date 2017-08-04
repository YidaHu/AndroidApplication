package com.huyd.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Author: huyd
 * Date: 2017-07-26
 * Time: 19:10
 * Describe:
 */
public class MyService2 extends Service {
	private final String LOG = "MyService2";
	private int count;
	private boolean quit;

	private Callback callback;//采用回调的方式

	//定义onBinder方法所返回的对象
//	private MyBinder binder = new MyBinder();
//
//	public class MyBinder extends Binder {
//		public int getCount() {
//			return count;
//		}
//	}

	public class IBinder extends Binder {
		public MyService2 getService() {
			return MyService2.this;
		}
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	//必须实现的方法,绑定改Service时回调该方法
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(LOG, "onBind方法被调用!");
//		return binder;
		return new IBinder();
	}

	//Service被创建时回调
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(LOG, "onCreate方法被调用!");
		//创建一个线程动态地修改count的值
		new Thread() {
			@Override
			public void run() {
				while (!quit) {
					count++;
					if (callback != null) {
						callback.onDataChange(count + "");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}.start();
	}

	//Service断开连接时回调
	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(LOG, "onUnbind方法被调用!");
		return true;
	}

	//Service被关闭前回调
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.quit = true;
		Log.i(LOG, "onDestroyed方法被调用!");
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i(LOG, "onRebind方法被调用!");
		super.onRebind(intent);
	}

	public interface Callback {
		void onDataChange(String data);
	}
}
