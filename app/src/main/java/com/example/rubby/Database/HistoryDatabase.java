package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rubby.Model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "HISTORY_DATABASE";
    public final static String DATABASE_TABLE = "HISTORY_DATABASE_TABLE";
    public final static String ID = "HISTORY_DATABASE_ID";
    public final static String DEVICE_NAME  = "HISTORY_DATABASE_DEVICE_NAME";
    public final static String DEVICE_TYPE  = "HISTORY_DATABASE_DEVICE_TYPE";
    public final static String LOCATION  = "HISTORY_DATABASE_LOCATION";
    public final static String TIME  = "HISTORY_DATABASE_TIME";
    public final static String MAC  = "HISTORY_DATABASE_MAC";
    public final static String IP  = "HISTORY_DATABASE_IP";
    public final static String TIME_RANGE  = "HISTORY_DATABASE_TIME_RANDE";
    public final static int DATA_BASE_VERSION = 1;

    public HistoryDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DEVICE_NAME + " TEXT, " +
                DEVICE_TYPE + " INTEGER, " +
                LOCATION + " TEXT, " +
                TIME + " TEXT, " +
                MAC + " TEXT, " +
                IP + " TEXT, " +
                TIME_RANGE +" TEXT);");
    }

    public void addItem(String deviceName, int deviceType, String location, String time, String MAC, String IP, String timeRange){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, deviceName);
        contentValues.put(DEVICE_TYPE, deviceType);
        contentValues.put(LOCATION, location);
        contentValues.put(TIME, time);
        contentValues.put(this.MAC, MAC);
        contentValues.put(this.IP, IP);
        contentValues.put(TIME_RANGE, timeRange);
        database.insert(DATABASE_TABLE, null, contentValues);
    }

    public List<HistoryModel> addAll() {
        List<HistoryModel> historyModels = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HistoryModel historyModel = new HistoryModel(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7));
                historyModels.add(historyModel);
            } while (cursor.moveToNext());
        }

        database.close();
        cursor.close();
        return historyModels;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
