package com.gswtek.huyd.deviceuniquecode;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gswtek.huyd.util.GetDeviceInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private Button btnGetWifiMac;
	private Button btnShowSPInfo;
	private Button btnShowFileInfo;
	private Button btnIsMacInfo;
	private TextView showDeviceInfo;


	SharedPreferences sharedPreferences;
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();


	}

	private void initViews() {
		btnGetWifiMac = (Button) findViewById(R.id.btnGetWifiMac);
		btnShowSPInfo = (Button) findViewById(R.id.btnShowSPInfo);
		btnShowFileInfo = (Button) findViewById(R.id.btnShowFileInfo);
		btnIsMacInfo = (Button) findViewById(R.id.btnIsMacInfo);

		showDeviceInfo = (TextView) findViewById(R.id.showDeviceInfo);
		btnGetWifiMac.setOnClickListener(this);
		btnShowSPInfo.setOnClickListener(this);
		btnShowFileInfo.setOnClickListener(this);
		btnIsMacInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnGetWifiMac://获取WIFI模块MAC
				String wifiMac = GetDeviceInfo.getMacAddr(this);
				setSharedPreference(wifiMac);
				try {
					setMacInfoToFile(wifiMac);
				} catch (IOException e) {
					e.printStackTrace();
				}
				showDeviceInfo.setText(wifiMac);
				break;
			case R.id.btnShowSPInfo://显示SP中的MAC信息
				String name = getSahrePreference();
				showDeviceInfo.setText("From SP:" + name);
				break;
			case R.id.btnShowFileInfo://显示文件中的MAC信息
				try {
					String name1 = getMacInfoFromFile();
					showDeviceInfo.setText("From File:" + name1);
				} catch (IOException e) {
					e.printStackTrace();
				}


				break;
			case R.id.btnIsMacInfo://比较MAC信息
				String localMac = GetDeviceInfo.getMacAddr(this);
				String SPMac = getSahrePreference();
				String fileMac = "";
				try {
					fileMac = getMacInfoFromFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (SPMac == null || SPMac.length() <= 0) {
					if (localMac.equals(fileMac)) {
						showDeviceInfo.setText("From File:" + fileMac + " 匹配结果相同");
					} else {
						showDeviceInfo.setText("From File:" + fileMac + " 匹配结果不相同");
					}
				} else {
					if (localMac.equals(SPMac)) {
						showDeviceInfo.setText("From SP:" + SPMac + " 匹配结果相同");
					} else {
						showDeviceInfo.setText("From SP:" + SPMac + " 匹配结果不相同");
					}
				}
				break;
		}

	}

	// 存储MAC信息到sharedpreferences
	public void setSharedPreference(String macinfo) {
		sharedPreferences = getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("macinfo", macinfo);
		editor.commit();// 提交修改
	}

	// 获得存到sharedpreferences中的MAC信息
	public String getSahrePreference() {
		sharedPreferences = getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
		String name = sharedPreferences.getString("macinfo", "");
		return name;
	}

	/**
	 * 保存MAC信息到文件中
	 *
	 * @param string
	 * @throws IOException
	 */
	public void setMacInfoToFile(String string) throws IOException {
		Log.i("uriuriuriuri++", Environment.getExternalStorageDirectory().getPath() + "/.macinfo.txt");
		File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/.macinfo.txt");
		if (!fileUri.exists()) {
			fileUri.createNewFile();
		}
		Uri imageUri;
		if (Build.VERSION.SDK_INT >= 24) {
			imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.gswtek.huyd.deviceuniquecode.fileprovider", fileUri);
		} else {
			imageUri = Uri.fromFile(fileUri);
		}
		Log.i("uriuriuriuri", imageUri.toString());
		ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(imageUri, "w");
		FileDescriptor fileDescriptor = pfd.getFileDescriptor();
//		FileOutputStream out = new FileOutputStream(fileDescriptor);
//		OutputStreamWriter pw = null;//定义一个流
//		pw = new OutputStreamWriter(out, "GBK");//确认流的输出文件和编码格式，此过程创建了“test.txt”实例
//		pw.write(string);//此处写入的就是多个字符串
//		pw.close();//关闭流
		writeData(fileDescriptor, string);

	}

	/**
	 * 获取保存在文件中的MAC信息
	 *
	 * @return
	 * @throws IOException
	 */
	public String getMacInfoFromFile() throws IOException {
		File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/.macinfo.txt");
		if (!fileUri.exists()) {
			fileUri.createNewFile();
		}
		Uri imageUri;
		//如果SDK版本大于24,那么使用文件提供者,否则直接获取
		if (Build.VERSION.SDK_INT >= 24) {
			imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.gswtek.huyd.deviceuniquecode.fileprovider", fileUri);
		} else {
			imageUri = Uri.fromFile(fileUri);
		}
		Log.i("uriuriuriuri", imageUri.toString());
		ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(imageUri, "r");
		FileDescriptor fileDescriptor = pfd.getFileDescriptor();
		return readData(fileDescriptor);
	}

	/**
	 * 往FileDescriptor中写入数据
	 *
	 * @param fileDescriptor
	 * @param content
	 */
	private void writeData(FileDescriptor fileDescriptor, String content) {
		FileOutputStream fos = new FileOutputStream(fileDescriptor);
		try {
			fos.write(content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 从FileDescriptor中读取数据
	 *
	 * @param fileDescriptor
	 * @return
	 */
	private String readData(FileDescriptor fileDescriptor) {
		FileInputStream fis = new FileInputStream(fileDescriptor);
		byte[] b = new byte[1024];
		int read;
		String content = null;
		try {
			while ((read = fis.read(b)) != -1) {
				content = new String(b, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

}
