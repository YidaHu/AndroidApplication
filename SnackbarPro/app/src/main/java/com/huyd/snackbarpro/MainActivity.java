package com.huyd.snackbarpro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huyd.utils.SnackbarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.*;

public class MainActivity extends AppCompatActivity {

	private FloatingActionButton fabAdd;
	private TextView tvAdd;
	private CoordinatorLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
		tvAdd = (TextView) findViewById(R.id.tvAdd);
		container = (CoordinatorLayout) findViewById(R.id.container);

		fabAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//默认方式
//				Snackbar.make(container, "女神向你发送了一条消息", Snackbar.LENGTH_SHORT)
//						.setAction("OK", new View.OnClickListener() {
//							@Override
//							public void onClick(View view) {
//								Toast.makeText(MainActivity.this, "You click OK !", Toast.LENGTH_SHORT).show();
//							}
//						})
//						.show();
				//自定义样式
//				Snackbar snackbar = Snackbar.make(container, "女神撤回了一条信息", Snackbar.LENGTH_SHORT);
//				setSnackbarColor(snackbar, R.color.fuchsia, R.color.goldenrod);
//				snackbar.setAction("OK", new View.OnClickListener() {
//					@Override
//					public void onClick(View view) {
//
//					}
//				});
//				snackbar.show();

				//自定义并封装方式
				Snackbar snackbar = SnackbarUtil.ShortSnackbar(container, "女神删了你发出的消息", SnackbarUtil.Warning)
						.setActionTextColor(Color.RED).setAction("再次发送", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								SnackbarUtil.LongSnackbar(container, "女神已将你拉黑", SnackbarUtil.Alert).setActionTextColor(Color.WHITE).show();
							}
						});
				//自定义布局方式
//				SnackbarUtil.SnackbarAddView(snackbar, R.layout.snackbar_addview, 0);
				snackbar.show();


			}
		});
	}

	/**
	 * 自定义Snackbar颜色
	 *
	 * @param snackbar
	 * @param messageColor
	 * @param backgroundColor
	 */
	public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
		View view = snackbar.getView();//获取Snackbar的view
		if (view != null) {
			view.setBackgroundColor(backgroundColor);//修改view的背景色
			((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);        // 获取Snackbar的message控件，修改字体颜色
		}
	}


}
