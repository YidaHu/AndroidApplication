package com.huyd.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huyd.alertdialogdemo.R;
import com.huyd.alertdialogdemo.SelfActivity;

/**
 * Author: huyd
 * Date: 2017-08-03
 * Time: 09:30
 * Describe:自定义,使用onCreateView.
 * 创建view可以通过两个途径，一是fragment中的onCreateView()，二是DialogFragment中的onCreateDialog()。
 * 前者适合对自定义的layout进行设置，具有更大的灵活性
 * 而后者适合对简单dialog进行处理，可以利用Dialog.Builder直接返回Dialog对象
 * 从生命周期的顺序而言，先执行onCreateDialog()，后执行oonCreateView()，我们不应同时使用两者。
 */
public class SelfDialogFragment extends android.app.DialogFragment {

	private EditText editText;
	private Button button;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_alert_dialog, container);
		editText = view.findViewById(R.id.id_txt_your_name);
		button = view.findViewById(R.id.id_sure_edit_name);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String name = editText.getText().toString();
				if (name.equals("helloworld")) {
					Intent intent = new Intent(getActivity(), SelfActivity.class);
					intent.putExtra("name", name);
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), "暗号错误!", Toast.LENGTH_SHORT).show();
				}

			}
		});
		return view;
	}
}
