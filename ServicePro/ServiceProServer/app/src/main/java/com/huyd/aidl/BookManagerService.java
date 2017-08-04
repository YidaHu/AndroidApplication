package com.huyd.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Author: huyd
 * Date: 2017-07-27
 * Time: 17:22
 * Describe:
 */
public class BookManagerService extends Service {

	private static final String TAG = "BMS";
	private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

	private Binder mBinder = new IBookManager.Stub() {

		@Override
		public List<Book> getBooks() throws RemoteException {
			return mBookList;
		}

		@Override
		public void addBook(Book book) throws RemoteException {
			mBookList.add(book);
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mBookList.add(new Book(1, "Android"));
		mBookList.add(new Book(2, "IOS"));

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
