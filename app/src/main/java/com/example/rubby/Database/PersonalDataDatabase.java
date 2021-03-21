package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonalDataDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "PERSONAL_DATA_DATABASE";
    public final static String DATABASE_TABLE = "PERSONAL_DATA_DATABASE_TABLE";
    public final static String ID = "PERSONAL_DATA_DATABASE_ID";
    public final static String SEX_POS  = "PERSONAL_DATA_SEX_POS";
    public final static String YEAR_INT = "PERSONAL_DATA_YEAR_INT";
    public final static String MONTH_INT = "PERSONAL_DATA_MOTH_INT";
    public final static String DAY_INT = "PERSONAL_DATA_DAY_INT";
    public final static String LOCATION = "PERSONAL_DATA_LOCATION";
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;
    public final static int DATA_BASE_VERSION = 1;

    public PersonalDataDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SEX_POS + " INTEGER DEFAULT 0, " +
                YEAR_INT + " INTEGER DEFAULT 0, " +
                MONTH_INT + " INTEGER DEFAULT 0, " +
                DAY_INT + " INTEGER DEFAULT 0, " +
                LOCATION + " TEXT DEFAULT NULL);");
    }

    public int getIntValue(String column){
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

    public String getStringValue(String column){
        sqLiteDatabase = this.getWritableDatabase();
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            contentValues = new ContentValues();
            contentValues.put(column, (String)null);
            sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
            cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        }
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(column);

        String value = cursor.getString(index);
        cursor.close();
        return value;
    }

    public void setIntValue(String column, int intValue){
        contentValues = new ContentValues();
        sqLiteDatabase = this.getWritableDatabase();
        contentValues.put(column, 0);
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst())
            sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
        else
            sqLiteDatabase.update(DATABASE_TABLE, contentValues, ID + "= 1", null);
        cursor.close();
    }

    public void setStringValue(String column, int stringValue){
        contentValues = new ContentValues();
        sqLiteDatabase = this.getWritableDatabase();
        contentValues.put(column, (String)null);
        cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst())
            sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
        else
            sqLiteDatabase.update(DATABASE_TABLE, contentValues, ID + "= 1", null);
        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}