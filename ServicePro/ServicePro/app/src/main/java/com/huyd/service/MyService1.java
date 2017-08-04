package com.huyd.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Author: huyd
 * Date: 2017-07-26
 * Time: 17:39
 * Describe:
 */
public class MyService1 extends Service {
	private final String LOG = "MyService1";

	//必须实现
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(LOG, "onBind方法被调用");
		return null;
	}

	//Service被创建时调用
	@Override
	public void onCreate() {
		Log.i(LOG, "onCreate方法被调用!");
		super.onCreate();
	}

	//Service被启动时调用
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(LOG, "onStartCommand方法被调用!");
		return super.onStartCommand(intent, flags, startId);
	}

	//Service被关闭之前回调
	@Override
	public void onDestroy() {
		Log.i(LOG, "onDestory方法被调用!");
		super.onDestroy();
	}
}
