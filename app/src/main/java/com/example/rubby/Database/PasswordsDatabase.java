package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PasswordsDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "PASSWORDS_DATABASE_DATABASE";
    public final static String DATABASE_TABLE = "PASSWORDS_DATABASE_TABLE";
    public final static String ID = "PASSWORDS_DATABASE_ID";
    public final static String PASSWORD  = "PASSWORDS_DATABASE_PASSWORD";
    public final static String PASSWORD_CODE  = "PASSWORDS_DATABASE_PASSWORD_CODE";
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;
    public final static int DATA_BASE_VERSION = 1;

    public PasswordsDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PASSWORD + " TEXT, " +
                PASSWORD_CODE + " TEXT DEFAULT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getValue(String column){
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

    public void setValue(String column, String value){
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
