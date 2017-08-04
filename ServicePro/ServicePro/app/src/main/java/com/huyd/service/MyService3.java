package com.huyd.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Author: huyd
 * Date: 2017-07-26
 * Time: 20:24
 * Describe:
 */
public class MyService3 extends IntentService {
	private final String LOG = "MyService3";

	//必须实现父类的构造方法
	public MyService3() {
		super("MyService3");
	}

	//必须重写的核心方法
	@Override
	protected void onHandleIntent(Intent intent) {
		//Intent是从Activity发过来的，携带识别参数，根据参数不同执行不同的任务
		String action = intent.getExtras().getString("param");
		if (action.equals("s1")) Log.i(LOG, "启动service1");
		else if (action.equals("s2")) Log.i(LOG, "启动service2");
		else if (action.equals("s3")) Log.i(LOG, "启动service3");

		//让服务休眠2秒
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//重写其他方法,用于查看方法的调用顺序

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(LOG, "onBind");
		return super.onBind(intent);
	}

	@Override
	public void onCreate() {
		Log.i(LOG, "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(LOG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void setIntentRedelivery(boolean enabled) {
		super.setIntentRedelivery(enabled);
		Log.i(LOG, "setIntentRedelivery");
	}

	@Override
	public void onDestroy() {
		Log.i(LOG, "onDestroy");
		super.onDestroy();
	}
}
