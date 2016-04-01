package com.shoppingpad.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bridgelabz4 on 6/3/16.
 * <p/>
 * purpose:
 * 1.interact with controller and only store the data objects.
 * 2.it will user getter and setter method.
 */

public class ContentInfoModel
{

    public  String mContent_id;              //content id
    public  String mModified_at;             //info modified Date
    public  String mCreated_at;              //info created date
    public  String mSyncDateTime;            //syncDate time
    public  String mDecription;              //description about content
    public  String mContentLink;             //content image
    public  String mImagesLink;              //it holds content ThumbView link.
    public  String mUrl ;                    //url
    public  String mTitle ;                  //title
    public  String mDisplay_name ;           //it holds content name.
    public  String mContentType ;            //ContentType
    public  String mLocalImage ;             //Uri of local image


    //extract the object here and set ListInformation
    public void setContentInfoData(JSONObject jsonObject,String localImage)
    {
        mContent_id   =String.valueOf(jsonObject.optInt("content_id"));
        mModified_at  =jsonObject.optString("modified_at").toString();
        mCreated_at   =jsonObject.optString("created_at").toString();
        mSyncDateTime =jsonObject.optString("syncDateTime").toString();
        mDecription   =jsonObject.optString("decription").toString();
        mContentLink  =jsonObject.optString("contentLink").toString();
        mImagesLink   =jsonObject.optString("imagesLink").toString();

        mUrl          =jsonObject.optString("url").toString();
        mDisplay_name =jsonObject.optString("display_name").toString();
        mTitle        =jsonObject.optString("title").toString();
        mContentType  =jsonObject.optString("contentType").toString();

        mLocalImage   =localImage;
    }
    //it set the Icon And display Name
    public void setContentInfoDataModel(Cursor cursor)
    {
        mDisplay_name = cursor.getString(cursor.getColumnIndex("display_name"));
        mImagesLink = cursor.getString(cursor.getColumnIndex("imagesLink"));
    }

    public void setContentInfoFromDataBase(JSONObject jsonObject)
    {
        mContent_id   =String.valueOf(jsonObject.optInt("content_id"));
        mModified_at  =jsonObject.optString("modified_at").toString();
        mCreated_at   =jsonObject.optString("created_at").toString();
        mSyncDateTime =jsonObject.optString("syncDateTime").toString();
        mDecription   =jsonObject.optString("decription").toString();
        mContentLink  =jsonObject.optString("contentLink").toString();
        mImagesLink   =jsonObject.optString("imagesLink").toString();

        mUrl          =jsonObject.optString("url").toString();
        mDisplay_name =jsonObject.optString("display_name").toString();
        mTitle        =jsonObject.optString("title").toString();
        mContentType  =jsonObject.optString("contentType").toString();

        mLocalImage   =jsonObject.optString("localImage").toString();

    }
}
