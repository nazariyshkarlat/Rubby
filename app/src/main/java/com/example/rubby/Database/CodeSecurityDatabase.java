package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CodeSecurityDatabase extends SQLiteOpenHelper {

    public final static String DATABASE = "CODE_SECURITY_DATABASE";
    public final static String DATABASE_TABLE = "CODE_SECURITY_DATABASE_TABLE";
    public final static String ID = "CODE_SECURITY_DATABASE_ID";
    public final static String ON_SWITCH = "CODE_SECURITY_ON_SWITCH";
    public final static String FINGERPRINT_SWITCH= "CODE_SECURITY_FINGERPRINT_SWITCH";
    public final static String REPEAT_TIME_POS= "CODE_SECURITY_REPEAT_TIME_POS";
    public final static int DATA_BASE_VERSION = 1;
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;

    public CodeSecurityDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ON_SWITCH + " INTEGER DEFAULT -1, " +
                FINGERPRINT_SWITCH + " INTEGER DEFAULT 0, " +
                REPEAT_TIME_POS + " INTEGER DEFAULT -1);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getValue(String column){
        sqLiteDatabase = this.getWritableDatabase();
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            contentValues = new ContentValues();
            contentValues.put(column, -1);
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
