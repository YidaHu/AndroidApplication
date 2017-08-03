package com.huyd.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huyd.alertdialogdemo.InputAlertDialogActivity;
import com.huyd.alertdialogdemo.OrdinaryDialogActivity;
import com.huyd.alertdialogdemo.R;
import com.huyd.views.CustomDialog;

/**
 * Author: huyd
 * Date: 2017-08-02
 * Time: 17:58
 * Describe:警示框工具类
 */
public class AlertDialogUtil {

	/**
	 * 普通警示框
	 *
	 * @param context
	 */
	public static void showSystemAlertDialog(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle("警示通知")
				.setMessage("暗网很暗,且行且珍惜,你确定要进入暗网？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(context, OrdinaryDialogActivity.class);
						context.startActivity(intent);
					}
				})
				.setNegativeButton("否", null)
				.setCancelable(false)//不关闭写法
				.show();
	}

	/**
	 * 带输入框的警示框
	 *
	 * @param context
	 */
	public static void showInputAlertDialog(final Context context) {
		LayoutInflater factory = LayoutInflater.from(context);//提示框
		final View view = factory.inflate(R.layout.input_alertdialog, null);//这里必须是final的
		final EditText edit = (EditText) view.findViewById(R.id.etPasswd);//获得输入框对象
		new AlertDialog.Builder(context)
				.setTitle("请输入暗号")
				.setIcon(android.R.drawable.ic_lock_lock)
				.setView(view)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						String checkCode = edit.getText().toString();//获取输入框值
						Intent intent = new Intent(context, InputAlertDialogActivity.class);
						intent.putExtra("code", checkCode);
						context.startActivity(intent);
					}
				})
				.setNegativeButton("取消", null)
				.show();
	}

	/**
	 * 自定义的警示框
	 *
	 * @param context
	 */
	public static void showCustomizeAlertDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(context, R.layout.activity_customize_alert_dialog, null);
		TextView tvA = (TextView) view.findViewById(R.id.tvA);
		TextView tvB = (TextView) view.findViewById(R.id.tvB);
		tvA.setOnClickListener(new View.OnClickListener() {// 发射子弹
			@Override
			public void onClick(View v) {
//				selectFromAlbum();
				Toast.makeText(context, "子弹发射成功!", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		tvB.setOnClickListener(new View.OnClickListener() {// 发射核弹
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "核弹发射成功!", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		dialog.setView(view);
		dialog.show();
	}

	/**
	 * 重写Dialog警示框
	 *
	 * @param context
	 */
	public static void showOverrideAlertDialog(final Context context) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("这是一个重写Dialog警示框");
		builder.setTitle("重写Dialog警示框");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

				Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
				dialogInterface.dismiss();

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
				dialogInterface.dismiss();

			}
		});
		builder.create().show();
	}


}
