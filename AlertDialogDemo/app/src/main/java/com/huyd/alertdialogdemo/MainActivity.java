package com.huyd.alertdialogdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.huyd.utils.AlertDialogUtil;
import com.huyd.utils.LoadingProgressDialog;
import com.huyd.utils.LoginDialogFragment;
import com.huyd.utils.SelfDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements LoginDialogFragment.LoginInputListener {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		//我们设置一个List集合，然后向里边添加几条数据
		List<String> ls = new ArrayList<String>();
		ls.add("普通Dialog");
		ls.add("输入框Dialog");
		ls.add("自定义Dialog");
		ls.add("列表Dialog");
		ls.add("单选Dialog");
		ls.add("多选Dialog");
		ls.add("加载框Dialog");
		ls.add("进度条Dialog");
		ls.add("重写Dialog");
		ls.add("DialogFragment");
		ls.add("重写onCreateDialog");
		ls.add("奔跑吧少年");


		//获取xml文件中listView控件
		//然后为listView控件调用setAdapter方法，让数据显示在界面上。
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ls));

		//为listView对象进行监听：当点击子项目的时候触发
		listView.setOnItemClickListener(new ItemClickEvent());

	}


	//继承OnItemClickListener，当子项目被点击的时候触发
	private final class ItemClickEvent implements AdapterView.OnItemClickListener {
		//这里需要注意的是第三个参数arg2，这是代表单击第几个选项
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			//通过单击事件，获得单击选项的内容
			String text = listView.getItemAtPosition(i) + "";
			//通过吐丝对象显示出来。
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			if (demos.length > i) {
				Intent intent = new Intent(MainActivity.this, demos[i].demoClass);
				startActivity(intent);

			}
			switch (i) {
				case 0:
					//普通Dialog
					AlertDialogUtil.showSystemAlertDialog(MainActivity.this);
					break;
				case 1:
					//带输入框Dialog
					AlertDialogUtil.showInputAlertDialog(MainActivity.this);
					break;
				case 2:
					//自定义Dialog
					AlertDialogUtil.showCustomizeAlertDialog(MainActivity.this);
					break;
				case 3:
					//列表Dialog
					AlertDialogUtil.showListDialog(MainActivity.this);
					break;
				case 4:
					//单选Dialog
					AlertDialogUtil.showSingleChoiceDialog(MainActivity.this);
					break;
				case 5:
					//多选Dialog
					AlertDialogUtil.showMultiChoiceDialog(MainActivity.this);
					break;
				case 6:
					//加载Dialog
					AlertDialogUtil.showWaitingDialog(MainActivity.this);
					break;
				case 7:
					//进度条Dialog
					AlertDialogUtil.showProgressDialog(MainActivity.this);
					break;


				case 8:
					//重写Dialog
					AlertDialogUtil.showOverrideAlertDialog(MainActivity.this);
					break;
				case 9:
					//继承DialogFragment重写onCreateView方法
					SelfDialogFragment selfDialogFragment = new SelfDialogFragment();
					selfDialogFragment.show(getFragmentManager(), "Dialog");
					break;
				case 10:
					//继承DialogFragment重写onCreateDialog方法
					LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
					loginDialogFragment.show(getFragmentManager(), "Login");
					break;
				case 11:
					//奔跑的小人,疯狂加载(模仿美团加载页面)
					showLoadingProgressDialog();
			}


		}
	}

	//把每个activity转成class
	private static class DemoInfo {
		private final Class<? extends android.app.Activity> demoClass;

		public DemoInfo(Class<? extends android.app.Activity> demoClass) {
			this.demoClass = demoClass;
		}
	}

	//把每个activity转成xxx.class
	private static final DemoInfo[] demos = {
//			new DemoInfo(WidgetsActivity.class),
	};

	//LoginDialogFragment中回调方法,传递username,password参数
	@Override
	public void onLoginInputComplete(String username, String passwrod) {
		Intent intent = new Intent(MainActivity.this, LoginAlertDialogActivity.class);
		intent.putExtra("info", username + " " + passwrod);
		startActivity(intent);
	}


	//显示加载的ProgressDialog
	public void showLoadingProgressDialog() {
		LoadingProgressDialog loadingProgressDialog = new LoadingProgressDialog(this, R.style.AppTheme, "正在加载中.....", R.anim.frame);
		loadingProgressDialog.show();
		//1.创建Timer对象
		Timer timer = new Timer();
		//3.创建TimerTask对象
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, WelcomeLoadingActivity.class);
				startActivity(intent);
				finish();
			}
		};
		//2.使用timer.schedule（）方法调用timerTask，定时3秒后执行run
		timer.schedule(timerTask, 3000);
	}

}