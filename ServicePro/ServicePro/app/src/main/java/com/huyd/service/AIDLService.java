package com.huyd.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.huyd.service.IPerson.Stub;

/**
 * Author: huyd
 * Date: 2017-07-27
 * Time: 10:57
 * Describe:
 */
public class AIDLService extends Service {

	private IBinder binder = new PersonQueryBinder();
	private String[] names = {"AA", "BB", "CC", "DD", "EE"};

	private String query(int num) {
		if (num > 0 && num < 6) {
			return names[num - 1];
		}
		return null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private final class PersonQueryBinder extends Stub {
		@Override
		public String queryPerson(int num) throws RemoteException {
			return query(num);
		}
	}
}