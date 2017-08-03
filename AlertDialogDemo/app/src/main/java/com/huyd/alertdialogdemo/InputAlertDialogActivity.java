package com.huyd.alertdialogdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InputAlertDialogActivity extends AppCompatActivity {


	@Bind(R.id.tvCode)
	TextView tvCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_alert_dialog);
		ButterKnife.bind(this);

		Intent intent = getIntent();
		String code = intent.getStringExtra("code");
		tvCode.setText(code);
	}
}
