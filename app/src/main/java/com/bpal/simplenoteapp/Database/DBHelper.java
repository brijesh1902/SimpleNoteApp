package com.bpal.simplenoteapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Notes";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String TIME = "time";
    public static final String USER = "user";

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
                + ID + "TEXT NOT NULL, "
                + TITLE + "TEXT NOT NULL, "
                + DESCRIPTION + " TEXT, "
                + TIME + "TEXT ,"
                + USER + "TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<DBNotes> getNotes(String user){
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect = {ID, TITLE, DESCRIPTION, TIME, USER};
        String sqlTable = TABLE_NAME;
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlselect, "user=?", new String[] {user}, null, null, null);

        final List<DBNotes> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new DBNotes(
                        c.getString(c.getColumnIndex(ID)),
                        c.getString(c.getColumnIndex(TITLE)),
                        c.getString(c.getColumnIndex(DESCRIPTION)),
                        c.getString(c.getColumnIndex(TIME)),
                        c.getString(c.getColumnIndex(USER))
                ));
            } while (c.moveToNext());
        }
        return result;
    }

    public void addNotes(DBNotes notes) {
        SQLiteDatabase db = getReadableDatabase();
        String squery = String.format("INSERT OR REPLACE INTO "+TABLE_NAME+"("+TITLE+", "+DESCRIPTION+", "+TIME+", "+USER+") " +
                        "VALUES('%s','%s','%s','%s');",
                 notes.getTitle(), notes.getDesc(), notes.getTime(), notes.getUser());
        db.execSQL(squery);
    }

    public void deleteNote(String user) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM "+TABLE_NAME+" WHERE user='%s'", user);
        db.execSQL(query);
    }

    /*public void updateCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET pquantity='%s' WHERE phone='%s' AND id='%s'", order.getPquantity(), order.getPhone());
        db.execSQL(query);
    }*/

    public void addNote(DBNotes notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, notes.getId());
        values.put(TITLE, notes.getTitle());
        values.put(DESCRIPTION, notes.getDesc());
        values.put(TIME, notes.getTime());
        values.put(USER, notes.getUser());

        db.insert(TABLE_NAME, null, values);

    }

}
