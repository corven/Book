package cos.book.database.utils;

import android.database.Cursor;

import java.util.ArrayList;

import cos.book.model.Book;

import static cos.book.database.utils.DBTableHeaders.KEY_ID;
import static cos.book.database.utils.DBTableHeaders.KEY_NAME;
import static cos.book.database.utils.DBTableHeaders.KEY_TITLE;


public class ListUtilities {
    public static ArrayList<Book> getAllBooks(Cursor c) {
        ArrayList<Book> books = new ArrayList<>();

        c.moveToFirst();
        while (!c.isAfterLast()) {
            books.add(createBookBy(c));
            c.moveToNext();
        }
        return books;
    }

    public static Book createBookBy(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_ID));
        String title = c.getString(c.getColumnIndex(KEY_TITLE));
        String name = c.getString(c.getColumnIndex(KEY_NAME));

        return new Book(id, title, name);
    }
}
