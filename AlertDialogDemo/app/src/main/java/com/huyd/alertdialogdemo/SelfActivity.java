package com.huyd.alertdialogdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelfActivity extends AppCompatActivity {

	@Bind(R.id.tvinfo)
	TextView tvinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self);
		ButterKnife.bind(this);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		tvinfo.setText(name);
	}
}
