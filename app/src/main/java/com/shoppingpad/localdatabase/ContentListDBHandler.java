package com.shoppingpad.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ContentParticipantsModel;
import com.shoppingpad.model.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

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
 */

//database
public class ContentListDBHandler extends SQLiteOpenHelper
{
    //Variables for database Name ,Version,Table Names
    SQLiteDatabase db;
    public static final int    DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingDatabase";
    public static final String CONTENT_VIEW_TABLE = "UserContentViews";
    public static final String CONTENT_TABLE = "Content";
    public static final String PARTICIPANT_TABLE = "ParticipantDetails";

    //query for Tables
    public static final  String USER_CONTENT_VIEWS=" CREATE TABLE "
            +CONTENT_VIEW_TABLE
            +"(UserContentId INT NULL," +
            "userAdminId INT NULL ,Content_id INT ," +
            "userId INT NULL,firstName VARCHAR(45) NULL," +
            "lastName VARCHAR(45) NULL," +
            "email VARCHAR(45) NULL,action VARCHAR(45)," +
            "displayProfile VARCHAR(45) NULL," +
            "lastViewedDateTime DATETIME NULL," +
            "numberOfViews INT NULL,participants INT)";
    public static final String USER_CONTENT_TABLE=" CREATE TABLE "
            + CONTENT_TABLE +
            "(content_id INT NULL,contentType VARCHAR(45) NULL," +
            "display_name VARCHAR(45) NULL,localImage VARCHAR(45),decription VARCHAR(45) NULL," +
            "imagesLink VARCHAR(45) NULL,syncDateTime VARCHAR(45) NULL," +
            "created_at VARCHAR(45) NULL,contentLink VARCHAR(45) NULL," +
            "modified_at VARCHAR(45) NULL,url TEXT,title INT)";
    public static final String PARTICIPANT_TABLE_QUERY="CREATE TABLE "
            + PARTICIPANT_TABLE +
            "(contentId INT NULL,userId VARCHAR(45) NULL," +
            "Name VARCHAR(45) NULL,Image VARCHAR(45) NULL," +
            "lastViewedDateTime VARCHAR(45) NULL,numberOfViews VARCHAR(45) NULL," +
            "action VARCHAR(45) NULL)";

    //Column of InfoList Tables
    public static final String PROFILE_NAME   = "display_name";
    public static final String CONTENT_ID     ="content_id";
    public static final String PROFILE_IMAGE  ="imagesLink";
    public static final String MODIFIED_AT    = "modified_at";
    public static final String CREATED_AT     = "created_at";
    public static final String SYNC_DATE_TIME = "syncDateTime";
    public static final String DESCRIPTION    = "decription";
    public static final String CONTENT_LINK   = "contentLink";
    public static final String URL            = "url";
    public static final String TITLE          = "title";
    public static final String CONTENT_TYPE   = "ContentType";
    public static final String LOCAL_IMAGE    = "localImage";

    //column of viewList Tables
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

    //columns of Participants
    public static final String PARTICIPANT_LAST_VIEW_DATE= "lastViewedDateTime";
    public static final String PARTICIPANT_ACTION        = "action";
    public static final String PARTICIPANT_NAME          = "Name";
    public static final String PARTICIPANT_USER_ID       = "userId";
    public static final String PARTICIPANT_CONTENT_ID    = "contentId";
    public static final String PARTICIPANT_IMAGE         = "Image";
    public static final String PARTICIPANT_NO_OF_VIEWS   = "numberOfViews";


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
        db.execSQL(PARTICIPANT_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" DROP TABLE IF EXISTS "+ CONTENT_VIEW_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS "+ CONTENT_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS "+ PARTICIPANT_TABLE);
        onCreate(db);
    }

    //ContentInfo data insert into DB
    public boolean setContentInfo(String contentId,String displayName,
                                  String imagesLink,
                                  String modified_at, String created_at,
                                  String syncDateTime, String description,
                                  String contentLink, String url,
                                  String title, String contentType
                                 ,String localImage)
    {
        SQLiteDatabase db1 = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_NAME, displayName);
        contentValues.put(CONTENT_ID1, contentId);
        contentValues.put(PROFILE_IMAGE, imagesLink);
        contentValues.put(CREATED_AT, created_at);
        contentValues.put(MODIFIED_AT, modified_at);
        contentValues.put(SYNC_DATE_TIME, syncDateTime);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(CONTENT_LINK, contentLink);
        contentValues.put(URL, url);
        contentValues.put(TITLE, title);
        contentValues.put(CONTENT_TYPE, contentType);
        contentValues.put(LOCAL_IMAGE,localImage);

        long result=db1.insert(CONTENT_TABLE, null, contentValues);
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
                                   int userAdminId,String userContentId,
                                   String action)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NO_Of_PARTICIPANTS, participants);
        contentValues.put(No_OF_Views, numberOfViews);
        contentValues.put(LAST_VIEW_DATE, lastViewedDateTime);
        contentValues.put(CONTENT_ID, ContentId);
        contentValues.put(DISPLAY_PROFILE, displayProfile);
        contentValues.put(ACTION, action);
        contentValues.put(EMAIL, email);
        contentValues.put(LAST_NAME, lastName);
        contentValues.put(FIRST_NAME, firstName);
        contentValues.put(USER_ADMIN_ID, userAdminId);
        contentValues.put(USER_ID, userId);
        contentValues.put(USER_CONTENT_ID, userContentId);

        long result1=db.insert(CONTENT_VIEW_TABLE,null, contentValues);
        if (result1 == -1)
            return false;
        else
            return true;
    }

    //ContentParticipant data insert into DB
    public boolean setParticipant(String CONTENT_ID,String USER_ID,
                                  String ACTION,String PROFILE_NAME,
                                  String NO_OF_VIEWS,String IMAGE,
                                  String LAST_VIEW_DATE)
    {
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARTICIPANT_NAME,PROFILE_NAME);
        contentValues.put(PARTICIPANT_ACTION,ACTION);
        contentValues.put(PARTICIPANT_CONTENT_ID,CONTENT_ID);
        contentValues.put(PARTICIPANT_NO_OF_VIEWS,NO_OF_VIEWS);
        contentValues.put(PARTICIPANT_LAST_VIEW_DATE,LAST_VIEW_DATE);
        contentValues.put(PARTICIPANT_USER_ID,USER_ID);
        contentValues.put(PARTICIPANT_IMAGE,IMAGE);
        long result=sqLiteDatabase.insert(PARTICIPANT_TABLE,null,contentValues);
        if(result == -1)
            return  false;
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
                InfoDataList.mContentType,
                InfoDataList.mLocalImage);

    }

    //set View data in Table
    public void setViewDataInDB(ViewModel ViewDataList)
    {
        setContentViews(ViewDataList.mContent_idView,
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

    //set the Participant Details in Table
    public void setParticipantData(ContentParticipantsModel participantsModel)
    {
        setParticipant(participantsModel.mParticipantContentId,
                participantsModel.mUserId,
                participantsModel.mParticipantAction,
                participantsModel.mParticipantName,
                participantsModel.mParticipantNoOfViews,
                participantsModel.mParticipantLastViewDateTime,
                participantsModel.mParticipantImage);
    }

    //reading data by contentId and reading the Tittle and Image
    public Cursor getUserDetails(String contentId)
    {

        int ContentIdNo=Integer.parseInt(contentId);
        Log.e("conentid",""+contentId);
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery(" SELECT * FROM " + CONTENT_TABLE +
                " WHERE " + CONTENT_ID + " = " + "" + ContentIdNo + " ", null);
        return cursor;
    }

    //collecting UserInfo from DataBase Table
    public JSONArray getContentInfoDatabase()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor ContentInformation=sqLiteDatabase.rawQuery("SELECT * FROM " + CONTENT_TABLE, null);
        JSONArray ContentInfoData = ConvertToJsonArray(ContentInformation);
        return ContentInfoData;
    }

    //selecting data from ContentView Table
    public JSONArray getContentViewDatabase()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor contentViewData=sqLiteDatabase.rawQuery(" SELECT * FROM " + CONTENT_VIEW_TABLE, null);
        JSONArray contentView=ConvertToJsonArray(contentViewData);
        return contentView;
    }

    //converting Cursor data into Json Array
    public JSONArray ConvertToJsonArray(Cursor cursor)
    {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++)
            {
                if (cursor.getColumnName(i) != null)
                {
                    try
                    {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }
}
