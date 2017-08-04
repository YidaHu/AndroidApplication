package com.huyd.servicepro;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huyd.service.MyService1;
import com.huyd.service.MyService2;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

	@Bind(R.id.btnStart)
	Button btnStart;
	@Bind(R.id.btnStop)
	Button btnStop;
	Intent intent;
	@Bind(R.id.btnBind)
	Button btnBind;
	@Bind(R.id.btnCancel)
	Button btnCancel;
	@Bind(R.id.btnStatus)
	Button btnStatus;
	String string = "";

	//保持所启动的Service的IBinder对象,同时定义一个ServiceConnection对象
	MyService2.IBinder binder;

	private ServiceConnection conn = new ServiceConnection() {
		//Activity与Service连接成功时回调该方法
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			System.out.println("------Service Connected-------");
			binder = (MyService2.IBinder) iBinder;
			MyService2 myService2 = binder.getService();
			//这里采用了自定义回调的方式
			myService2.setCallback(new MyService2.Callback() {
				@Override
				public void onDataChange(String data) {
					Message message = new Message();
					message.obj = data;
					handler.sendMessage(message);
				}
			});
		}

		//Activity与Service断开连接时回调该方法
		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			System.out.println("------Service DisConnected-------");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//创建启动Service的Intent,以及Intent属性
		intent = new Intent(this, MyService2.class);

		ButterKnife.bind(this);


	}

	@OnClick({R.id.btnStart, R.id.btnStop, R.id.btnBind, R.id.btnCancel, R.id.btnStatus})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btnStart://开启服务MyService1
				startService(intent);
				break;
			case R.id.btnStop://关闭服务MyService1
				stopService(intent);
				break;
			case R.id.btnBind://绑定serviceMyService2
				bindService(intent, conn, Service.BIND_AUTO_CREATE);
				break;
			case R.id.btnCancel://关闭服务MyService2
				unbindService(conn);
				break;
			case R.id.btnStatus://服务状态MyService2
//				Log.i("MyService2-STATUS", "Service的count的值为:" + binder.getCount);
				break;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			string = msg.obj.toString();
			Log.i("MyService2-STATUS", "Service的count的值为:" + string);
			super.handleMessage(msg);
		}
	};

}
