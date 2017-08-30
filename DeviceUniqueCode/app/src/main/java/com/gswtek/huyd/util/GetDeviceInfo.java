package com.gswtek.huyd.util;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Author: huyd
 * Date: 2017-08-29
 * Time: 17:01
 * Describe:获取设备信息类
 */
public class GetDeviceInfo {


	/**
	 * 获取WIFI MAC地址
	 *
	 * @param context
	 * @return
	 */
	public static String getMacAddr(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
		//如果WIFI模块没有打开,那么打开WIFI
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return "";
				}

				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(String.format("%02X:", b));
				}

				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (Exception ex) {
		}
		return "02:00:00:00:00:00";
	}


}
