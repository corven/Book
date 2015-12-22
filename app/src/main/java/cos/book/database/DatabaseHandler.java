package cos.book.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cos.book.model.Book;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int  DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "library",
            TABLE_NAME = "books",
            KEY_ID = "id",
            KEY_TITLE = "title",
            KEY_NAME = "name";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT," + KEY_NAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void createBook(Book book){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_NAME, book.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteBook(Book book){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(book.getId())});
        db.close();
    }

    public void updateBook(Book book){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_NAME, book.getName());

        db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(book.getId())});
        db.close();
    }

    public int getBooksCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<Book>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()){
            do {
                books.add(new Book(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return books;
    }
}
