package com.example.rubby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rubby.Model.BlackListModel;
import com.example.rubby.Model.PromotionsModel;

import java.util.ArrayList;
import java.util.List;

public class PromotionsDatabase  extends SQLiteOpenHelper {

    public final static String DATABASE= "PROMOTIONS_DATABASE";
    public final static String DATABASE_TABLE = "PROMOTIONS_DATABASE_TABLE";
    public final static String ID = "PROMOTIONS_DATABASE_ID";
    public final static String CAMPAIGN_TYPE = "PROMOTIONS_DATABASE_TYPE";
    public final static String PRODUCT_URL = "PROMOTIONS_DATABASE_CAMPAIGN_PRODUCT_URL";
    public final static String CAMPAIGN_NAME = "PROMOTIONS_DATABASE_CAMPAIGN_NAME";
    public final static String AUDITORY_SEX = "PROMOTIONS_DATABASE_CAMPAIGN_AUDITORY_SEX";
    public final static String AUDITORY_AGE = "PROMOTIONS_DATABASE_CAMPAIGN_AUDITORY_AGE";
    public final static String AUDITORY_PETS = "PROMOTIONS_DATABASE_CAMPAIGN_AUDITORY_PETS";
    public final static String AUDITORY_LOCATION = "PROMOTIONS_DATABASE_CAMPAIGN_AUDITORY_LOCATION";
    public final static String AUDITORY_PLATFORM = "PROMOTIONS_DATABASE_CAMPAIGN_AUDITORY_PLATFORM";
    public final static String OFF_COMMENTS = "PROMOTIONS_DATABASE_CAMPAIGN_OFF_COMMENTS";
    public final static String ORGANIZATION_NAME = "PROMOTIONS_DATABASE_CAMPAIGN_ORGANIZATION_NAME";
    public final static String CAMPAIGN_ABOUT = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_ABOUT";
    public final static String CAMPAIGN_URL = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_URL";
    public final static String CAMPAIGN_START_DATE = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_START_DATE";
    public final static String CAMPAIGN_END_DATE = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_END_DATE";
    public final static String CAMPAIGN_PAYMENT_METHOD = "PROMOTIONS_DATABASE_CAMPAIGN_PAYMENT_METHOD";
    public final static String CAMPAIGN_VIEWS = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_VIEWS";
    public final static String CAMPAIGN_CLICKS = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_CLICKS";
    public final static String CAMPAIGN_LIKES = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_LIKES";
    public final static String CAMPAIGN_COMMENTS = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_COMMENTS";
    public final static String CAMPAIGN_FAVORITES = "PROMOTIONS_DATABASE_CAMPAIGN_CAMPAIGN_FAVORITES";
    private Cursor cursor;
    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;
    public final static int DATA_BASE_VERSION = 1;

    public final static int CAMPAIGN_TYPE_USER_POST = 0;
    public final static int CAMPAIGN_TYPE_COMMUNITY_POST = 1;
    public final static int CAMPAIGN_TYPE_PET = 2;
    public final static int CAMPAIGN_TYPE_ANOTHER_PRODUCT = 3;
    public final static int CAMPAIGN_TYPE_ANOTHER_APP = 4;
    public final static int NO_SEX = 0;
    public final static int SEX_MALE = 1;
    public final static int SEX_FEMALE = 2;
    public final static int NO_PLATFORM = -1;
    public final static int PLATFORM_ANDROID = 0;
    public final static int PLATFORM_IOS = 1;
    public final static int PLATFORM_WINDOWS = 2;

    public final static int CAMPAIGN_TYPE_INDEX = 1;
    public final static int PRODUCT_URL_INDEX = 2;
    public final static int CAMPAIGN_NAME_INDEX = 3;
    public final static int AUDITORY_SEX_INDEX = 4;
    public final static int AUDITORY_AGE_INDEX = 5;
    public final static int AUDITORY_PETS_INDEX = 6;
    public final static int AUDITORY_LOCATION_INDEX = 7;
    public final static int AUDITORY_PLATFORM_INDEX = 8;
    public final static int OFF_COMMENTS_INDEX = 9;
    public final static int ORGANIZATION_NAME_INDEX = 10;
    public final static int CAMPAIGN_ABOUT_INDEX = 11;
    public final static int CAMPAIGN_URL_INDEX = 12;
    public final static int CAMPAIGN_START_DATE_INDEX = 13;
    public final static int CAMPAIGN_END_DATE_INDEX = 14;
    public final static int CAMPAIGN_PAYMENT_METHOD_INDEX = 15;
    public final static int CAMPAIGN_VIEWS_INDEX = 16;
    public final static int CAMPAIGN_CLICKS_INDEX = 17;
    public final static int CAMPAIGN_LIKES_INDEX = 18;
    public final static int CAMPAIGN_COMMENTS_INDEX = 19;
    public final static int CAMPAIGN_FAVORITES_INDEX = 20;

    public PromotionsDatabase(Context context) {
        super(context, DATABASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAMPAIGN_TYPE + " INTEGER DEFAULT -1, " +
                PRODUCT_URL + " TEXT DEFAULT NULL, " +
                CAMPAIGN_NAME + " TEXT DEFAULT NULL, " +
                AUDITORY_SEX + " INTEGER DEFAULT 0, " +
                AUDITORY_AGE + " TEXT DEFAULT NULL, " +
                AUDITORY_PETS + " TEXT DEFAULT NULL, " +
                AUDITORY_LOCATION + " TEXT DEFAULT NULL, " +
                AUDITORY_PLATFORM + " INTEGER DEFAULT -1, " +
                OFF_COMMENTS + " INTEGER DEFAULT 0, " +
                ORGANIZATION_NAME + " TEXT DEFAULT NULL, " +
                CAMPAIGN_ABOUT + " TEXT DEFAULT NULL, " +
                CAMPAIGN_URL + " TEXT DEFAULT NULL, " +
                CAMPAIGN_START_DATE + " INTEGER DEFAULT 0, " +
                CAMPAIGN_END_DATE + " INTEGER DEFAULT 0, " +
                CAMPAIGN_PAYMENT_METHOD + " INTEGER DEFAULT 0, " +
                CAMPAIGN_VIEWS + " INTEGER DEFAULT 0, " +
                CAMPAIGN_CLICKS + " INTEGER DEFAULT 0, " +
                CAMPAIGN_LIKES + " INTEGER DEFAULT 0, " +
                CAMPAIGN_COMMENTS + " INTEGER DEFAULT 0, " +
                CAMPAIGN_FAVORITES + " INTEGER DEFAULT 0);");
    }

    public void addItemByModel(PromotionsModel promotionsModel){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAMPAIGN_TYPE, promotionsModel.campaignType);
        contentValues.put(PRODUCT_URL, promotionsModel.productUrl);
        contentValues.put(CAMPAIGN_NAME, promotionsModel.campaignName);
        contentValues.put(AUDITORY_SEX, promotionsModel.auditorySex);
        contentValues.put(AUDITORY_AGE, promotionsModel.auditoryAge);
        contentValues.put(AUDITORY_PETS, promotionsModel.auditoryPets);
        contentValues.put(AUDITORY_LOCATION, promotionsModel.auditoryLocation);
        contentValues.put(AUDITORY_PLATFORM, promotionsModel.auditoryPlatform);
        contentValues.put(OFF_COMMENTS, promotionsModel.offComments);
        contentValues.put(ORGANIZATION_NAME, promotionsModel.organizationName);
        contentValues.put(CAMPAIGN_ABOUT, promotionsModel.campaignAbout);
        contentValues.put(CAMPAIGN_URL, promotionsModel.campaignUrl);
        contentValues.put(CAMPAIGN_START_DATE, promotionsModel.campaignStartDate);
        contentValues.put(CAMPAIGN_END_DATE, promotionsModel.campaignEndDate);
        contentValues.put(CAMPAIGN_PAYMENT_METHOD, promotionsModel.campaignPaymentMethod);
        contentValues.put(CAMPAIGN_VIEWS, promotionsModel.campaignViews);
        contentValues.put(CAMPAIGN_CLICKS, promotionsModel.campaignClicks);
        contentValues.put(CAMPAIGN_LIKES, promotionsModel.campaignLikes);
        contentValues.put(CAMPAIGN_COMMENTS, promotionsModel.campaignComments);
        contentValues.put(CAMPAIGN_FAVORITES, promotionsModel.campaignFavorites);
        database.insert(DATABASE_TABLE, null, contentValues);
        database.close();
    }

    public void updateItem(ContentValues contentValues, int position){
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(DATABASE_TABLE, contentValues, ID + "= " +(position+1), null);
        database.close();
    }

    public List<PromotionsModel> addModelForList() {
        List<PromotionsModel> promotionsModels = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PromotionsModel promotionsModel = new PromotionsModel(cursor.getString(CAMPAIGN_NAME_INDEX),cursor.getString(CAMPAIGN_START_DATE_INDEX), cursor.getString(CAMPAIGN_END_DATE_INDEX));
                promotionsModels.add(promotionsModel);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return promotionsModels;
    }

    public PromotionsModel getModelForInfo(int position){
        PromotionsModel promotionsModel;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);
        cursor.moveToPosition(position);
        promotionsModel = new PromotionsModel(cursor.getString(CAMPAIGN_NAME_INDEX),cursor.getString(CAMPAIGN_START_DATE_INDEX), cursor.getString(CAMPAIGN_END_DATE_INDEX));
        database.close();
        cursor.close();
        return promotionsModel;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
