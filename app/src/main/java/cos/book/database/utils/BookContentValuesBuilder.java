package cos.book.database.utils;

import android.content.ContentValues;

import cos.book.model.Book;

import static cos.book.database.utils.DBTableHeaders.KEY_NAME;
import static cos.book.database.utils.DBTableHeaders.KEY_TITLE;

public class BookContentValuesBuilder {
    public static ContentValues createContentValuesByBook(Book book) {
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_NAME, book.getName());

        return values;
    }
}
