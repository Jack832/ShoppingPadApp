package com.shoppingpad.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ViewModel;

import java.util.List;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 *
 * purpose:
 * 1.It maintains the database updated whenever SyncDate is changed.
 * 2.using  get and set method it returns the data.
 * 3.when Internet is not connected to device. It will show the data present in database.
 * 4.it will have all the tables required for app.
 *
 *
 */

//database
public class ContentListDBHandler extends SQLiteOpenHelper
{
    //Variables for database
    SQLiteDatabase db;
    public static final int    DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "ShoppingPadDataBase";
    public static final String CONTENT_VIEW_TABLE = "UserContentViews";
    public static final String CONTENT_TABLE = "Content";

    //query for Tables
    public static final  String USER_CONTENT_VIEWS=" CREATE TABLE "+CONTENT_VIEW_TABLE
            +"(UserContentId INT NULL," +
            "userAdminId INT NULL ,Content_id INT ," +
            "userId INT NULL,firstName VARCHAR(45) NULL," +
            "lastName VARCHAR(45) NULL," +
            "email VARCHAR(45) NULL,action VARCHAR(45)," +
            "displayProfile VARCHAR(45) NULL," +
            "lastViewedDateTime DATETIME NULL," +
            "numberOfViews INT NULL,participants INT)";
    public static final String USER_CONTENT_TABLE=" CREATE TABLE "+ CONTENT_TABLE +
            "(Content_id INT NULL,ContentType VARCHAR(45) NULL," +
            "display_name VARCHAR(45) NULL,decription VARCHAR(45) NULL," +
            "imagesLink VARCHAR(45) NULL,syncDateTime VARCHAR(45) NULL," +
            "created_at VARCHAR(45) NULL,contentLink VARCHAR(45) NULL," +
            "modified_at VARCHAR(45) NULL,url TEXT,title INT)";



    //Column of Tables
    public static final String PROFILE_NAME   = "display_name";
    public static final String CONTENT_ID     ="Content_id";
    public static final String PROFILE_IMAGE  ="imagesLink";
    public static final String MODIFIED_AT    = "modified_at";
    public static final String CREATED_AT     = "created_at";
    public static final String SYNC_DATE_TIME = "syncDateTime";
    public static final String DESCRIPTION    = "decription";
    public static final String CONTENT_LINK   = "contentLink";
    public static final String URL            = "url";
    public static final String TITLE          = "title";
    public static final String CONTENT_TYPE   = "ContentType";

    //views
    public static final String CONTENT_ID1      ="Content_id";
    public static final String No_OF_Views      ="numberOfViews";
    public static final String DISPLAY_PROFILE  = "displayProfile";
    public static final String LAST_VIEW_DATE   ="lastViewedDateTime";
    public static final String ACTION           = "action";
    public static final String EMAIL            = "email";
    public static final String LAST_NAME        = "lastName";
    public static final String FIRST_NAME       = "firstName";
    public static final String USER_ID          = "userId";
    public static final String USER_ADMIN_ID    = "userAdminId";
    public static final String USER_CONTENT_ID  = "userContentId";
    public static final String NO_Of_PARTICIPANTS ="participants";

    //constructor to create tables
    public ContentListDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(USER_CONTENT_VIEWS);
        db.execSQL(USER_CONTENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" DROP TABLE IF EXISTS "+ CONTENT_VIEW_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS "+ CONTENT_TABLE);


        onCreate(db);

    }

    //ContentInfo data insert into DB
    public boolean setContentInfo(int contentId,String displayName,
                                  String imagesLink,
                                  String modified_at, String created_at,
                                  String syncDateTime, String decription,
                                  String contentLink, String url,
                                  String title, String contentType)
    {
        SQLiteDatabase db1 = getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(PROFILE_NAME,displayName);
        cv1.put(CONTENT_ID1,contentId);
        cv1.put(PROFILE_IMAGE,imagesLink);
        cv1.put(CREATED_AT,created_at);
        cv1.put(MODIFIED_AT,modified_at);
        cv1.put(SYNC_DATE_TIME,syncDateTime);
        cv1.put(DESCRIPTION,decription);
        cv1.put(CONTENT_LINK,contentLink);
        cv1.put(URL,url);
        cv1.put(TITLE,title);
        cv1.put(CONTENT_TYPE,contentType);


        long result=db1.insert(CONTENT_TABLE, null, cv1);
        if (result == -1)
            return false;
        else
            return true;

    }

    //ContentView data insert into DB
    public boolean setContentViews(int ContentId,String lastViewedDateTime,
                                   String numberOfViews,String participants,
                                   String displayProfile,String email,
                                   String lastName,
                                   String firstName,int userId,
                                   int userAdminId,int userContentId,
                                   String action)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(NO_Of_PARTICIPANTS,participants);
        cv1.put(No_OF_Views,numberOfViews);
        cv1.put(LAST_VIEW_DATE,lastViewedDateTime);
        cv1.put(CONTENT_ID,ContentId);
        cv1.put(DISPLAY_PROFILE,displayProfile);
        cv1.put(ACTION,action);
        cv1.put(EMAIL,email);
        cv1.put(LAST_NAME,lastName);
        cv1.put(FIRST_NAME,firstName);
        cv1.put(USER_ADMIN_ID,userAdminId);
        cv1.put(USER_ID,userId);
        cv1.put(USER_CONTENT_ID,userContentId);

        long result1=db.insert(CONTENT_VIEW_TABLE,null, cv1);
        if (result1 == -1)
            return false;
        else
            return true;
    }

    //set Info content in table
    public void setInfoDataInDB(ContentInfoModel InfoDataList)
    {
        setContentInfo(InfoDataList.mContent_id,
                    InfoDataList.mDisplay_name,
                    InfoDataList.mImagesLink,
                    InfoDataList.mModified_at,
                    InfoDataList.mCreated_at,
                    InfoDataList.mSyncDateTime,
                    InfoDataList.mDecription,
                    InfoDataList.mContentLink,
                    InfoDataList.mUrl,
                    InfoDataList.mTitle,
                    InfoDataList.mContentType);

    }

    //set View data in Table
    public void setViewDataInDB(ViewModel ViewDataList)
    {
        setContentViews(
                    ViewDataList.mContent_idView,
                    ViewDataList.mLastViewedDateTime,
                    ViewDataList.mNumberOfViews,
                    ViewDataList.mParticipants,
                    ViewDataList.mDisplayProfile,
                    ViewDataList.mEmail,
                    ViewDataList.mLastName,
                    ViewDataList.mFirstName,
                    ViewDataList.mUserId,
                    ViewDataList.mUserAdminId,
                    ViewDataList.mUserContentId,
                    ViewDataList.mAction);

     }
}
