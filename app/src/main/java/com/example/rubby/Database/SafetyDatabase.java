package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SafetyDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "SAFETY_DATABASE";
    public final static String DATABASE_TABLE = "SAFETY_DATABASE_TABLE";
    public final static String ID = "SAFETY_DATABASE_ID";
    public final static String COMMENTS_POS  = "SAFETY_COMMENTS";
    public final static String PETS_POS   = "SAFETY_PETS";
    public final static String COMMUNITIES_POS  = "SAFETY_COMMUNITIES";
    public final static String MESSAGES_POS   = "SAFETY_MESSAGES";
    public final static String SEARCH_SHOW_SWITCH  = "SAFETY_SEARCH_SHOW_SWITCH";
    public final static String CLOSED_PROFILE_SWITCH  = "SAFETY_CLOSED_PROFILE_SWITCH";
    public final static int DATA_BASE_VERSION = 1;
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;

    public SafetyDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COMMENTS_POS  + " INTEGER DEFAULT 0, " +
                PETS_POS  + " INTEGER DEFAULT 0, " +
                COMMUNITIES_POS  + " INTEGER DEFAULT 0, " +
                MESSAGES_POS  + " INTEGER DEFAULT 0, " +
                SEARCH_SHOW_SWITCH + " INTEGER DEFAULT 0, " +
                CLOSED_PROFILE_SWITCH + " INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getValue(String column){
        sqLiteDatabase = this.getWritableDatabase();
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            contentValues = new ContentValues();
            contentValues.put(column, 0);
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
