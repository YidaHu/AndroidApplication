// IBookManager.aidl
package com.huyd.aidl;
import com.huyd.aidl.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBooks();
    void addBook(inout Book book);
}
