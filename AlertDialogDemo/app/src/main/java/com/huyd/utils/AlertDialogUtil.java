package com.huyd.utils;

import android.app.ProgressDialog;
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

import java.util.ArrayList;

/**
 * Author: huyd
 * Date: 2017-08-02
 * Time: 17:58
 * Describe:警示框工具类
 */
public class AlertDialogUtil {

	static int yourChoice;

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

	/**
	 * 列表Dialog
	 *
	 * @param context
	 */
	public static void showListDialog(final Context context) {
		final String[] items = {"艾欧尼亚", "弗雷尔卓德", "皮尔特沃夫", "比尔吉沃特"};
		AlertDialog.Builder listDialog =
				new AlertDialog.Builder(context);
		listDialog.setTitle("符文之地，魔法就是一切");
		listDialog.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// which 下标从0开始
				// ...To-do
				Toast.makeText(context,
						"你点击了" + items[which],
						Toast.LENGTH_SHORT).show();
			}
		});
		listDialog.show();
	}


	/**
	 * 单选Dialog
	 *
	 * @param context
	 */
	public static void showSingleChoiceDialog(final Context context) {
		final String[] items = {"复仇之矛", "迷失之牙", "虚空之眼", "弗雷尔卓德之心"};
		yourChoice = -1;
		AlertDialog.Builder singleChoiceDialog =
				new AlertDialog.Builder(context);
		singleChoiceDialog.setTitle("请选择一个英雄");
		// 第二个参数是默认选项，此处设置为0
		singleChoiceDialog.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						yourChoice = which;
					}
				});
		singleChoiceDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (yourChoice != -1) {
							Toast.makeText(context,
									"你选择了" + items[yourChoice],
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		singleChoiceDialog.show();
	}


	static ArrayList<Integer> yourChoices = new ArrayList<>();

	/**
	 * 多选Dialog
	 * @param context
	 */
	public static void showMultiChoiceDialog(final Context context) {
		final String[] items = {"暴风之剑", "幻影之舞", "无尽之刃", "玛莫提乌斯之噬"};
		// 设置默认选中的选项，全为false默认均未选中
		final boolean initChoiceSets[] = {false, false, false, false};
		yourChoices.clear();
		AlertDialog.Builder multiChoiceDialog =
				new AlertDialog.Builder(context);
		multiChoiceDialog.setTitle("拾起你的武器,战斗吧");
		multiChoiceDialog.setMultiChoiceItems(items, initChoiceSets,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which,
					                    boolean isChecked) {
						if (isChecked) {
							yourChoices.add(which);
						} else {
							yourChoices.remove(which);
						}
					}
				});
		multiChoiceDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int size = yourChoices.size();
						String str = "";
						for (int i = 0; i < size; i++) {
							str += items[yourChoices.get(i)] + " ";
						}
						Toast.makeText(context,
								"你选中了" + str,
								Toast.LENGTH_SHORT).show();
					}
				});
		multiChoiceDialog.show();
	}


	/**
	 * 加载Dialog
	 *
	 * @param context
	 */
	public static void showWaitingDialog(Context context) {
	/* 等待Dialog具有屏蔽其他控件的交互能力
	 * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
     * 下载等事件完成后，主动调用函数关闭该Dialog
     */
		ProgressDialog waitingDialog =
				new ProgressDialog(context);
		waitingDialog.setTitle("匹配战队");
		waitingDialog.setMessage("匹配队友中...");
		waitingDialog.setIndeterminate(true);
//		waitingDialog.setCancelable(false);
		waitingDialog.show();
	}

	/**
	 * 进度条Dialog
	 *
	 * @param context
	 */
	public static void showProgressDialog(Context context) {
	/* @setProgress 设置初始进度
	 * @setProgressStyle 设置样式（水平进度条）
     * @setMax 设置进度最大值
     */
		final int MAX_PROGRESS = 100;
		final ProgressDialog progressDialog =
				new ProgressDialog(context);
		progressDialog.setProgress(0);
		progressDialog.setTitle("敌军将在30秒后到达战场");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMax(MAX_PROGRESS);
		progressDialog.show();
	/* 模拟进度增加的过程
	 * 新开一个线程，每个100ms，进度增加1
     */
		new Thread(new Runnable() {
			@Override
			public void run() {
				int progress = 0;
				while (progress < MAX_PROGRESS) {
					try {
						Thread.sleep(100);
						progress++;
						progressDialog.setProgress(progress);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 进度达到最大值后，窗口消失
				progressDialog.cancel();
			}
		}).start();
	}


}
