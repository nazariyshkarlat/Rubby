package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.UnicodeSet;

import com.example.rubby.Model.BlackListModel;
import com.example.rubby.Model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class BlackListDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "BLACK_LIST_DATABASE";
    public final static String DATABASE_TABLE = "BLACK_LIST_DATABASE_TABLE";
    public final static String ID = "BLACK_LIST_DATABASE_ID";
    public final static String USER_NAME  = "BLACK_LIST_DATABASE_USER_NAME";
    public final static String TIME  = "BLACK_LIST_DATABASE_TIME";
    public final static int DATA_BASE_VERSION = 1;

    public BlackListDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                TIME +" TEXT);");
    }

    public void addItem(String userName, String time){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, userName);
        contentValues.put(TIME, time);
        database.insert(DATABASE_TABLE, null, contentValues);
    }

    public List<BlackListModel> addAll() {
        List<BlackListModel> blackListModels = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                BlackListModel blackListModel = new BlackListModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                blackListModels.add(blackListModel);
            } while (cursor.moveToNext());
        }

        database.close();
        cursor.close();
        return blackListModels;
    }

    public void removeItem(int position){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DATABASE_TABLE, ID + "= " + toString().valueOf(position), null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
