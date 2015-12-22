package cos.book.database.utils;

import static cos.book.database.utils.DBTableHeaders.*;

public class DBQueryStrings {
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TITLE + " TEXT," + KEY_NAME + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String GET_ALL = "SELECT * FROM " + TABLE_NAME;
}
