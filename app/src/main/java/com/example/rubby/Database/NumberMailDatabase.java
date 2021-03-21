package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collections;
import java.util.stream.IntStream;

public class NumberMailDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "NUMBER_MAIL_DATABASE";
    public final static String DATABASE_TABLE = "NUMBER_MAIL_DATABASE_TABLE";
    public final static String ID = "NUMBER_MAIL_DATABASE_ID";
    public final static String NUMBER  = "NUMBER_MAIL_NUMBER";
    public final static String MAIL  = "NUMBER_MAIL_MAIL";
    public final static int DATA_BASE_VERSION = 1;
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;

    public NumberMailDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NUMBER + " TEXT, " +
                MAIL + " TEXT);");
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

    public static String getCodeMail(String mail){
        int dogIndex = mail.indexOf('@');
        int lastIDot = mail.lastIndexOf('.');
        String firstStars = new String(new char[dogIndex-1 - 1]).replace("\0", "*");
        String secondStars = new String(new char[lastIDot-1 - (dogIndex+1)]).replace("\0", "*");
        String thirdStars = new String(new char[mail.length()-1 - (lastIDot+1)]).replace("\0", "*");
        String firstCharacters = mail.substring(0,1);
        String secondCharacters = mail.substring(dogIndex-1,dogIndex+2);
        String thirdCharacters = mail.substring(lastIDot,lastIDot+2);
        return firstCharacters + firstStars + secondCharacters + secondStars + thirdCharacters + thirdStars;
    }

    public static String getCodeNumber(String number){
        String countryCode = number.substring(0, number.length() - 9);
        int countryCodeLength = countryCode.length();
        String stars =  new String(new char[number.substring(countryCodeLength,number.length() - 2).length()]).replace("\0", "*");
        String endCharacters = number.substring(number.length() - 2);
        return countryCode + stars + endCharacters;
    }

}
