package com.bpal.simplenoteapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Notes";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String TIME = "time";

    public static final String DB_NAME = "Notes.db";

    static final int DB_VERSION = 1;

    // Constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating Table
        String query = "create table " + TABLE_NAME + "("
                + ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + "TEXT NOT NULL, "
                + DESCRIPTION + " TEXT, "
                + TIME + "TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNote(String title, String desc, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(DESCRIPTION, desc);
        values.put(TIME, time);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

}
