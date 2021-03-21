package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rubby.Model.CreditCardModel;
import com.example.rubby.Model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class CreditCardsDatabase extends SQLiteOpenHelper {

    public final static String DATABASE= "CREDIT_CARDS_DATABASE";
    public final static String DATABASE_TABLE = "CREDIT_CARDS_DATABASE_TABLE";
    public final static String ID = "CREDIT_CARDS_DATABASE_ID";
    public final static String CARD_NUMBER  = "CREDIT_CARDS_CARD_NUMBER";
    public final static String CARD_VALIDITY  = "CREDIT_CARDS_CARD_VALIDITY";
    public final static String SECURITY_CODE = "CREDIT_CARDS_SECURITY_CODE";
    public final static String OWNER_NAME  = "CREDIT_CARDS_OWNER_NAME";
    public final static int DATA_BASE_VERSION = 1;

    public CreditCardsDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CARD_NUMBER + " TEXT, " +
                CARD_VALIDITY + " TEXT, " +
                SECURITY_CODE + " TEXT, " +
                OWNER_NAME + " TEXT);");
    }

    public void addItem(String cardNumber , String cardValidity, String securityCode, String ownerName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARD_NUMBER, cardNumber);
        contentValues.put(CARD_VALIDITY, cardValidity);
        contentValues.put(SECURITY_CODE, securityCode);
        contentValues.put(OWNER_NAME, ownerName);
        database.insert(DATABASE_TABLE, null, contentValues);
    }

    public void updateItem(String cardNumber , String cardValidity, String securityCode, String ownerName, int position){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARD_NUMBER, cardNumber);
        contentValues.put(CARD_VALIDITY, cardValidity);
        contentValues.put(SECURITY_CODE, securityCode);
        contentValues.put(OWNER_NAME, ownerName);
        database.update(DATABASE_TABLE, contentValues, ID + "= " +(position+1), null);
        database.close();
    }

    public CreditCardModel getItem(int position){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);
        cursor.moveToPosition(position);
        CreditCardModel creditCardModel = new CreditCardModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        cursor.close();
        return creditCardModel;
    }

    public List<CreditCardModel> addAll() {
        List<CreditCardModel> creditCardModels = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CreditCardModel creditCardModel = new CreditCardModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                creditCardModels.add(creditCardModel);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return creditCardModels;
    }

    public void removeItem(int position){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DATABASE_TABLE, ID + "= " + toString().valueOf(position), null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
