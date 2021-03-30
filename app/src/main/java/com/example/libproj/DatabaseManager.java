package com.example.libproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "books";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_AUTHOR = "author";

    public DatabaseManager (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                " "+ COLUMN_ID +" INTEGER NOT NULL CONSTRAINT books_pk PRIMARY KEY AUTOINCREMENT," +
                " "+ COLUMN_NAME +" varchar(200) NOT NULL," +
                " "+ COLUMN_GENRE +" varchar(200) NOT NULL," +
                " "+ COLUMN_AUTHOR +" varchar(200) NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean addBook(String book, String genre, String author ) {

       SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, book);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_AUTHOR, author);
       return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1 ;
    }

    Cursor getAllBooks() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null );
    }

    boolean updateBook(int id, String book, String genre, String author ) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, book);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_AUTHOR, author);
        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID+"=?", new String[]{String.valueOf(id)}) > 0;
    }

        boolean deleteBook(int id) { //изтриване на запис в базата данни
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {String.valueOf(id)}) > 0;
    }
}
