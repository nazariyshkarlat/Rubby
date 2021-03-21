package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TwoStepAuthenticationDatabase extends SQLiteOpenHelper {

    public final static String DATABASE = "TWO_STEP_AUTHENTICATION_DATABASE";
    public final static String DATABASE_TABLE = "TWO_STEP_AUTHENTICATION_DATABASE_TABLE";
    public final static String ID = "TWO_STEP_AUTHENTICATION_DATABASE_ID";
    public final static String AUTHENTICATION_SWITCH = "TWO_STEP_AUTHENTICATION_SWITCH";
    public final static String CONFIRMATION_METHOD_POS = "TWO_STEP_AUTHENTICATION_CONFIRMATION_METHOD_POS";
    public final static String APP_AUTHENTICATOR_POS= "TWO_STEP_AUTHENTICATION_APP_AUTHENTICATOR_POS";
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;
    public final static int DATA_BASE_VERSION = 1;

    public TwoStepAuthenticationDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AUTHENTICATION_SWITCH + " INTEGER DEFAULT 0, " +
                CONFIRMATION_METHOD_POS + " INTEGER DEFAULT -1, " +
                APP_AUTHENTICATOR_POS + " INTEGER DEFAULT -1);");

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
