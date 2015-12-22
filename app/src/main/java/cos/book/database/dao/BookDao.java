package cos.book.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cos.book.database.adapter.DBAdapter;
import cos.book.database.utils.BookContentValuesBuilder;
import cos.book.database.utils.ListUtilities;
import cos.book.model.Book;

import static cos.book.database.utils.DBQueryStrings.GET_ALL;
import static cos.book.database.utils.DBTableHeaders.KEY_ID;
import static cos.book.database.utils.DBTableHeaders.TABLE_NAME;

public class BookDao {
    private DBAdapter dbAdapter;
    private SQLiteDatabase database;

    public BookDao(Context context) {
        dbAdapter = DBAdapter.getInstance();
        database = dbAdapter.open(context);
    }

    public void create(Book book) {
        ContentValues values = BookContentValuesBuilder.createContentValuesByBook(book);
        database.insert(TABLE_NAME, null, values);
    }

    public void update(Book book) {
        ContentValues values = BookContentValuesBuilder.createContentValuesByBook(book);
        String where = KEY_ID + "=" + book.getId();
        database.update(TABLE_NAME, values, where, null);
    }

    public void delete(Book book) {
        String where = KEY_ID + "=" + book.getId();
        database.delete(TABLE_NAME, where, null);
    }

    public ArrayList<Book> getAllBooks() {
        Cursor cursor = database.rawQuery(GET_ALL, null);
        ArrayList<Book> books = ListUtilities.getAllBooks(cursor);
        cursor.close();

        return books;
    }

    public int getBooksCount() {
        Cursor cursor = database.rawQuery(GET_ALL, null);
        return cursor.getCount();
    }
}
