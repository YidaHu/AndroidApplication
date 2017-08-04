package com.huyd.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: huyd
 * Date: 2017-07-27
 * Time: 17:22
 * Describe:
 */
public class BookManagerService extends Service {

	private static final String TAG = "BMS";

	private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

	private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
	//	private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();
	private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();

	private Binder mBinder = new IBookManager.Stub() {

		@Override
		public List<Book> getBooks() throws RemoteException {
			return mBookList;
		}

		@Override
		public void addBook(Book book) throws RemoteException {
			mBookList.add(book);
		}

		@Override
		public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//			if (!mListenerList.contains(listener)) {
//				mListenerList.add(listener);
//			} else {
//				Log.d(TAG, "already exists.");
//			}
//			Log.d(TAG, "registerListener, size:" + mListenerList.size());
			mListenerList.register(listener);
		}

		@Override
		public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//			if (mListenerList.contains(listener)) {
//				mListenerList.remove(listener);
				Log.d(TAG, "unregister listener succuessd.");
//
//			} else {
//				Log.d(TAG, "not found,can not unregister.");
//			}
//			Log.d(TAG, "unregisterListener, current size:" + mListenerList.size());
			mListenerList.unregister(listener);
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mBookList.add(new Book(1, "Android"));
		mBookList.add(new Book(2, "IOS"));
		new Thread(new ServiceWorker()).start();

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onDestroy() {
		mIsServiceDestoryed.set(true);
		super.onDestroy();
	}

	private void onNewBookArrived(Book book) throws RemoteException {
		mBookList.add(book);
//		Log.d(TAG, "onNewBookArrived, notify listeners:" + mListenerList.size());
//		for (int i = 0; i < mListenerList.size(); i++) {
//			IOnNewBookArrivedListener listener = mListenerList.get(i);
//			Log.d(TAG, "onNewBookArrived, notify listener:" + listener);
//			listener.onNewBookArrived(book);
		final int N = mListenerList.beginBroadcast();
		for (int i = 0; i < N; i++) {
			IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
			if (l != null) {
				l.onNewBookArrived(book);
			}
		}
		mListenerList.finishBroadcast();

	}

	private class ServiceWorker implements Runnable {
		@Override
		public void run() {

			//do background processing here...
			while (!mIsServiceDestoryed.get()) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int bookId = mBookList.size() + 1;
				Book newBook = new Book(bookId, "new Book#" + bookId);
				try {
					onNewBookArrived(newBook);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
