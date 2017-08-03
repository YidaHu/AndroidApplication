package com.huyd.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huyd.alertdialogdemo.R;

/**
 * Author: huyd
 * Date: 2017-08-03
 * Time: 09:52
 * Describe:继承DialogFragment重写onCreateDialog方法
 * 创建view可以通过两个途径，一是fragment中的onCreateView()，二是DialogFragment中的onCreateDialog()。
 * 前者适合对自定义的layout进行设置，具有更大的灵活性
 * 而后者适合对简单dialog进行处理，可以利用Dialog.Builder直接返回Dialog对象
 * 从生命周期的顺序而言，先执行onCreateDialog()，后执行oonCreateView()，我们不应同时使用两者。
 */
public class LoginDialogFragment extends DialogFragment {
	private EditText username;//用户名
	private EditText password;//密码

	//回调接口
	public interface LoginInputListener {
		void onLoginInputComplete(String username, String passwrod);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.login_alert_dialog, null);
		username = view.findViewById(R.id.id_txt_username);
		password = view.findViewById(R.id.id_txt_password);
		builder.setView(view)
				.setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						// 利用回调方法传递usernmae和password参数
						LoginInputListener loginInputListener = (LoginInputListener) getActivity();
						loginInputListener.onLoginInputComplete(username.getText().toString(), password.getText().toString());

//						Toast.makeText(getActivity(), "Sign In", Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("Cancel", null);
		return builder.create();
	}
}
