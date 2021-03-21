package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rubby.Model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileEditDatabase extends SQLiteOpenHelper {
    public final static String DATABASE= "PROFILE_EDIT_DATABASE";
    public final static String DATABASE_TABLE = "PROFILE_EDIT_DATABASE_TABLE";
    public final static String ID = "PROFILE_EDIT_DATABASE_ID";
    public final static String FULL_NAME = "PROFILE_EDIT_DATABASE_FULL_NAME";
    public final static String LOGIN = "PROFILE_EDIT_DATABASE_LOGIN";
    public final static String MORE = "PROFILE_EDIT_DATABASE_MORE";
    public final static String SITE = "PROFILE_EDIT_DATABASE_SITE";
    public final static int DATA_BASE_VERSION = 1;
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;

        public ProfileEditDatabase(Context context) {
            super(context, DATABASE, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FULL_NAME + " TEXT DEFAULT NULL, " +
                    LOGIN + " TEXT DEFAULT NULL, " +
                    MORE + " TEXT DEFAULT NULL, " +
                    SITE + " TEXT DEFAULT NULL);");

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

    public ArrayList<String> addAll() {
            ArrayList<String> arrayList = new ArrayList<>();
            sqLiteDatabase = this.getWritableDatabase();
           cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
           if(!cursor.moveToFirst()){
               contentValues = new ContentValues();
               contentValues.put(FULL_NAME, (String) null);
               sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
               cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
           }
           if (cursor.moveToFirst()) {
               for(int i = 1;i<cursor.getColumnCount();i++) {
                   arrayList.add(cursor.getString(i));
               }
           }
           sqLiteDatabase.close();
           cursor.close();
           return arrayList;
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
