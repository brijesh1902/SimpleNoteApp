package com.bpal.simplenoteapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteAssetHelper {

    public static final String TABLE_NAME = "Note";
    private static final String DB_NAME = "Notes.db";

    private static final int DB_VERSION = 1;

    // Constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<DBNotes> getNotes(String user){
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect = {"title", "desc", "time", "user"};
        String sqlTable = "Note";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlselect, "user=?", new String[] {user}, null, null, null);

        final ArrayList<DBNotes> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new DBNotes(
                        c.getString(c.getColumnIndex("title")),
                        c.getString(c.getColumnIndex("desc")),
                        c.getString(c.getColumnIndex("time")),
                        c.getString(c.getColumnIndex("user"))
                ));
            } while (c.moveToNext());
        }
        return result;
    }

   public void addNotes(DBNotes notes) {
        SQLiteDatabase db = getReadableDatabase();
        String squery = String.format("INSERT OR REPLACE INTO Note (title, desc, time, user) " +
                        "VALUES('%s','%s','%s','%s');",
                notes.getTitle(), notes.getDesc(), notes.getTime(), notes.getUser());
        db.execSQL(squery);
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("DELETE FROM Note WHERE time='%s'", id);
        db.execSQL(query);
    }
}
