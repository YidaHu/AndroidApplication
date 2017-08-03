package com.huyd.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.huyd.alertdialogdemo.R;

/**
 * Author: huyd
 * Date: 2017-08-03
 * Time: 10:46
 * Describe:自定义加载ProgressDialog
 */
public class LoadingProgressDialog extends ProgressDialog {

	private AnimationDrawable animation;
	private Context context;
	private ImageView loadingIV;
	private TextView loadingTV;
	private String loadingTip, oldLoadingTip;
	private int count = 0, resid;


	public LoadingProgressDialog(Context context) {
		super(context);
	}

	public LoadingProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public LoadingProgressDialog(Context context, int theme, String content, int id) {
		super(context, theme);
		this.context = context;
		this.loadingTip = content;
		this.resid = id;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}


	public void setContent(String str) {
		loadingTV.setText(str);
	}

	private void initView() {
		setContentView(R.layout.loding_alert_dialog);
		loadingTV = findViewById(R.id.loadingTv);
		loadingIV = findViewById(R.id.loadingIv);
	}


	private void initData() {
		loadingIV.setBackgroundResource(resid);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		animation = (AnimationDrawable) loadingIV.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		loadingIV.post(new Runnable() {
			@Override
			public void run() {
				animation.start();
			}
		});
		loadingTV.setText(loadingTip);
	}

}
