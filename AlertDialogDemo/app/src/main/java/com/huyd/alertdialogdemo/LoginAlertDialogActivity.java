package com.huyd.alertdialogdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginAlertDialogActivity extends AppCompatActivity {

	@Bind(R.id.tvLoginInfo)
	TextView tvLoginInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_alert_dialog);
		ButterKnife.bind(this);

		Intent intent = getIntent();
		String loginInfo = intent.getStringExtra("info");
		tvLoginInfo.setText(loginInfo);
	}
}
