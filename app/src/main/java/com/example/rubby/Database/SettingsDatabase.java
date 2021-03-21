package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsDatabase extends SQLiteOpenHelper {

    public final static String DATABASE = "SETTINGS_DATABASE";
    public final static String DATABASE_TABLE = "SETTINGS_DATABASE_TABLE";
    public final static String ID = "SETTINGS_DATABASE_ID";
    public final static String ANIMATION_SWITCH = "SETTINGS_DATABASE_ANIMATION_SWITCH";
    public final static String NOTIFICATIONS_SWITCH = "SETTINGS_DATABASE_NOTIFICATIONS_SWITCH";
    public final static String TRAFFIC_SAVING_SWITCH = "SETTINGS_DATABASE_TRAFFIC_SAVING_SWITCH";
    public final static String SAVE_PLACE_SWITCH= "SETTINGS_DATABASE_SAVE_PLACE_SWITCH";
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;
    public final static int DATA_BASE_VERSION = 1;

    public SettingsDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ANIMATION_SWITCH + " INTEGER DEFAULT 0, " +
                NOTIFICATIONS_SWITCH + " INTEGER DEFAULT 0, " +
                TRAFFIC_SAVING_SWITCH + " INTEGER DEFAULT 0, " +
                SAVE_PLACE_SWITCH + " INTEGER DEFAULT 0);");

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
        }
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
