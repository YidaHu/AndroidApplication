package com.huyd.servicepro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huyd.service.MyService3;

public class Main3Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main3);

		Intent intent1 = new Intent(Main3Activity.this, MyService3.class);
		Bundle bundle1 = new Bundle();
		bundle1.putString("param", "s1");
		intent1.putExtras(bundle1);

		Intent intent2 = new Intent(Main3Activity.this, MyService3.class);
		Bundle bundle2 = new Bundle();
		bundle2.putString("param", "s2");
		intent2.putExtras(bundle2);

		Intent intent3 = new Intent(Main3Activity.this, MyService3.class);
		Bundle bundle3 = new Bundle();
		bundle3.putString("param", "s3");
		intent3.putExtras(bundle3);


		//接着启动多次IntentService,每次启动,都会新建一个工作线程
		//但始终只有一个IntentService实例
		startService(intent1);
		startService(intent2);
		startService(intent3);
	}
}
