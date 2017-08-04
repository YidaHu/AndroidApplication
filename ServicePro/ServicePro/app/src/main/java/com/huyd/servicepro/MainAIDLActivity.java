package com.huyd.servicepro;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huyd.service.IPerson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAIDLActivity extends AppCompatActivity {

	@Bind(R.id.btnQuery)
	Button btnQuery;
	@Bind(R.id.tvName)
	TextView tvName;
	@Bind(R.id.editName)
	EditText editName;

	private IPerson iPerson;
	private PersonConnection conn = new PersonConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_aidl);
		ButterKnife.bind(this);

		//绑定远程Service
		Intent service = new Intent("android.intent.action.AIDLService");
		service.setPackage("com.huyd.service");

		bindService(service, conn, BIND_AUTO_CREATE);
	}

	@OnClick(R.id.btnQuery)
	public void onViewClicked() {
		String number = editName.getText().toString();
		int num = Integer.valueOf(number);
		try {
			tvName.setText(iPerson.queryPerson(num));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		editName.setText("");

	}

	private final class PersonConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName name, IBinder service) {
			iPerson = IPerson.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName name) {
			iPerson = null;
		}
	}
}
