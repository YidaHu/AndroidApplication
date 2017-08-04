package com.huyd.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lypeer.ipcclient.R;

import java.util.List;


public class BookManageerActivity extends AppCompatActivity {

	private static final String TAG = "BookManagerActivity";
	private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

	private IBookManager mRemoteBookManger;

	//5秒获取一次数据
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_NEW_BOOK_ARRIVED:
					Log.d(TAG, "receive new book :" + msg.obj);
					break;
				default:
					super.handleMessage(msg);
			}
		}
	};

	//建立连接
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
			try {
				mRemoteBookManger = bookManager;
				List<Book> list = bookManager.getBooks();//获取图书
				Log.i(TAG, "query book list,list type" + list.getClass().getCanonicalName());
				Log.i(TAG, "query book list,list type" + list.toString());

				Book newBook = new Book(3, "Java");
				bookManager.addBook(newBook);//添加书籍
				Log.i(TAG, "add book" + newBook);
				List<Book> newList = bookManager.getBooks();//获取图书
				Log.i(TAG, "query book list:" + newList.toString());

				bookManager.registerListener(mOnNewBookArrivedListener);//注册IOnNewBookArrivedListener到远程服务端

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_manageer);

		//绑定服务
		Intent intent = new Intent(this, BookManagerService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

	}

	//有新书时通知当前客户端
	private IOnNewBookArrivedListener mOnNewBookArrivedListener = new com.huyd.aidl.IOnNewBookArrivedListener.Stub() {

		@Override
		public void onNewBookArrived(Book newBook) throws RemoteException {
			mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
		}
	};

	@Override
	protected void onDestroy() {

		if (mRemoteBookManger != null && mRemoteBookManger.asBinder().isBinderAlive()) {
			Log.i(TAG, "unregister listener:" + mOnNewBookArrivedListener);
			try {
				mRemoteBookManger.unregisterListener(mOnNewBookArrivedListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		unbindService(mConnection);//取消绑定服务
		super.onDestroy();

	}
}
