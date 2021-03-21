package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotificationsDatabase extends SQLiteOpenHelper {

    public final static String DATABASE = "NOTIFICATIONS_DATABASE";
    public final static String DATABASE_TABLE = "NOTIFICATIONS_DATABASE_TABLE";
    public final static String ID = "NOTIFICATIONS_DATABASE_ID";
    public final static String NEW_FOLLOWERS = "NOTIFICATIONS_NEW_FOLLOWERS";
    public final static String NEW_MESSAGES = "NOTIFICATIONS_NEW_MESSAGES";
    public final static String COMMENT_ANSWERS = "NOTIFICATIONS_COMMENT_ANSWERS";
    public final static String POSTS_MARKS = "NOTIFICATIONS_POSTS_MARKS";
    public final static String LIKES = "NOTIFICATIONS_LIKES";
    public final static int DATA_BASE_VERSION = 1;
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;

    public NotificationsDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NEW_FOLLOWERS + " INTEGER DEFAULT 1, " +
                NEW_MESSAGES + " INTEGER DEFAULT 1, " +
                COMMENT_ANSWERS + " INTEGER DEFAULT 1, " +
                POSTS_MARKS + " INTEGER DEFAULT 1, " +
                LIKES + " INTEGER DEFAULT 1);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getValue(String column){
        sqLiteDatabase = this.getWritableDatabase();
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            contentValues = new ContentValues();
            contentValues.put(column, 1);
            sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
            cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        }
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(column);

        int value = cursor.getInt(index);
        cursor.close();
        return value;
    }

    public void setValue(String column, int value){
        contentValues = new ContentValues();
        sqLiteDatabase = this.getWritableDatabase();
        contentValues.put(column, value);
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst())
            sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
        else
            sqLiteDatabase.update(DATABASE_TABLE, contentValues, ID + "= 1", null);

        cursor.close();
    }

}
