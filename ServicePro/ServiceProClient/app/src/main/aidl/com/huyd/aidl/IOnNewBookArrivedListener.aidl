// IOnNewBookArrivedListener.aidl
package com.huyd.aidl;
import com.huyd.aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);

}
