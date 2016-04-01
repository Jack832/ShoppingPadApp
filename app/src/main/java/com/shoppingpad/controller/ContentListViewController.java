package com.shoppingpad.controller;


import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.shoppingpad.localdatabase.ContentListDBHandler;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ContentParticipantsModel;
import com.shoppingpad.model.ViewModel;
import com.shoppingpad.restservice.ContentListServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 * purpose:
 *
 * 1.It is data controller in MVVM.
 * 2.interact with local db and cloud interaction.
 * 3.it provide interface for VM to interact with DbController,it abstract the
 * DBHandler,model and service.
 * 4.Controller controls the flow of data between the models,DB handler and
 * services.it will acts as manager.
 * 5.controller will get the data from server then controller will handle database call .
 * then controller will call database and give required attributes to viewModel.
 *
 */

public class ContentListViewController
{
    final static boolean mPERFORM_UNIT_TEST = false;

    //ContentServiceHandler Object
    ContentListServiceHandler mContentListServiceHandler;
    //list which will store the name and Image
    ContentListDBHandler mContentListDBHandler;
    //content List Model
    ContentInfoModel mContentInfoModel;
    //content List view Model
    ViewModel mViewModel;
    //creating Info list from jsonArray
    List<ContentInfoModel> mInfoDataListFromREST;
    //creating ViewData list from jsonArray
    public static List<ViewModel> mViewDataListFromREST;
    //creating participantsData list from jsonArray
    List<ContentParticipantsModel> mContentParticipantsModels;
    //participant model class
    ContentParticipantsModel mParticipantsModel;
    Context context;
    //to check internet Connection
    boolean isInternetWorking;
    //to store images in file
    File directory;
    //Image Url verification
    Pattern pattern;
    Matcher matcher;
    String mPattern_Image="(bmp|jpg|gif|png)";



    //calling the serviceHandler and DBHandler.
    public ContentListViewController(Context context)
    {
        mInfoDataListFromREST = new ArrayList<>();
        mViewDataListFromREST = new ArrayList<>();
        mContentParticipantsModels = new ArrayList<>();
        this.context = context;
        if (mPERFORM_UNIT_TEST)
        {
            //populateDummyData();
        } else
        {
            mContentListDBHandler = new ContentListDBHandler(context);
            mContentListServiceHandler = new ContentListServiceHandler();
            isInternetWorking = checkInternetConnection();
        }
    }

    //checking the internetConnection
    private boolean checkInternetConnection()
    {
        ConnectivityManager connectivity = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info != null && info.isConnected())
        {
            return true;
        }
        return false;
    }

    //reading json and passing Object in InfoList
    public List<ContentInfoModel> getJsonInfoData()
    {
        try
        {
            if (!isInternetWorking)
            {
                JSONArray ContentInfoList = ContentInfoList();
                setContentInfoListData(ContentInfoList);
            }
            else
            {
                JSONArray jsonArrayInfo = mContentListServiceHandler
                        .getJsonArrayOfInfo();
                setContentInfoListData(jsonArrayInfo);
                insertInfoDataIntoTables();
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return mInfoDataListFromREST;
    }

    //to set ContentInfo Data Model and List
    private void setContentInfoListData(JSONArray ContentInfo)
    {
        for (int i = 0; i < ContentInfo.length(); i++)
        {
            try
            {
                mContentInfoModel = new ContentInfoModel();
                JSONObject jsonObject1 = ContentInfo.getJSONObject(i);
                //if internet is Working
                if (isInternetWorking)
                {
                    String ImageURL= jsonObject1.optString("imagesLink")
                                                            .toString();
                    boolean isImage= checkImageUrl(ImageURL);
                    if(isImage)
                    {
                        String ImageName="Image"+i+"";
                        urlToImageConversion(ImageURL,ImageName);
                        String fileName=Environment.getExternalStorageDirectory()
                                .getAbsolutePath()+"/"+ImageName;
                        mContentInfoModel.setContentInfoData(jsonObject1,
                               fileName);
                    }
                    //if image is inValid
                    else
                    {
                        mContentInfoModel.setContentInfoData(jsonObject1, null);
                    }
                }
                //if no internet
                else
                {
                    mContentInfoModel.setContentInfoFromDataBase(jsonObject1);
                }
                mInfoDataListFromREST.add(mContentInfoModel);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean checkImageUrl(String image)
    {
        pattern = Pattern.compile(mPattern_Image);
        matcher = pattern.matcher(image);
        return matcher.find();
    }

    //to read and save Json ViewList
    public List<ViewModel> getJsonViewData() {
        try {
            if (!isInternetWorking) {
                JSONArray ContentView = ContentViewList();
                Log.d("value", "getJsonInfoData:No internetConnection ");
                setContentViewListData(ContentView);
            } else {
                JSONArray jsonArrayView = mContentListServiceHandler
                        .getJsonArrayOfView();
                setContentViewListData(jsonArrayView);
                insertViewDataIntoTables();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return mViewDataListFromREST;
    }

    //called to set the DataModel
    private void setContentViewListData(JSONArray contentViewList) {
        for (int i = 0; i < contentViewList.length(); i++) {
            try {
                mViewModel = new ViewModel();
                JSONObject jsonObject1 = contentViewList.getJSONObject(i);
                mViewModel.setContentViewData(jsonObject1);
                mViewDataListFromREST.add(mViewModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //set the database by calling database Info method
    public void insertInfoDataIntoTables() {
        for (int i = 0; i < mInfoDataListFromREST.size(); i++) {
            mContentListDBHandler.setInfoDataInDB(mInfoDataListFromREST.get(i));
        }
    }

    //set the database by calling database View method
    public void insertViewDataIntoTables() {
        for (int i = 0; i < mViewDataListFromREST.size(); i++) {
            mContentListDBHandler.setViewDataInDB(mViewDataListFromREST.get(i));
        }
    }

    //call rest to get the ContentInfo SS information
    public List<ContentParticipantsModel> getParticipantData(String ContentId) {
        JSONArray jsonArray = mContentListServiceHandler
                .getDataForContentParticipants(ContentId);
        try {
            if (jsonArray != null) {

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    try
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mParticipantsModel = new ContentParticipantsModel();
                        mParticipantsModel.setContentParticipantsModel(jsonObject);
                        mContentParticipantsModels.add(mParticipantsModel);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                setParticipantDetailsInDB();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return mContentParticipantsModels;
    }

    //set the Participants details in db
    public void setParticipantDetailsInDB()
    {
        for (int i = 0; i < mContentParticipantsModels.size(); i++)
        {
            mContentListDBHandler.setParticipantData(mContentParticipantsModels.get(i));
        }
    }

    //to get the Image and Name from contentId
    public ContentInfoModel getUserData(String contentId)
    {
        Cursor cursor = mContentListDBHandler.getUserDetails(contentId);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    mContentInfoModel = new ContentInfoModel();
                    mContentInfoModel.setContentInfoDataModel(cursor);
                }
                while (cursor.moveToNext());
            }
        }
        return mContentInfoModel;
    }

    //getting contentInfo data from database
    public JSONArray ContentInfoList()
    {
        JSONArray cursor = mContentListDBHandler.getContentInfoDatabase();
        return cursor;
    }

    //ContentView Data from Database
    public JSONArray ContentViewList()
    {
        JSONArray cursor = mContentListDBHandler.getContentViewDatabase();
        return cursor;
    }

    //Convert imageUrl to Image
    public void urlToImageConversion(String imageLink,String ImageName)
    {
        try
        {
            URL url = new URL(imageLink);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection()
                    .getInputStream());
            storeImageInFile(bitmap,ImageName);

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    //store image in sdcard
    public void storeImageInFile(Bitmap bitmap,String ImageName)
    {
        String root= Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

        try
        {
            directory = new File(root+"/Images");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            OutputStream fOut = null;

            File myPath = new File(directory, ImageName);
            if (myPath.exists())
                myPath.delete();
            myPath.createNewFile();
            fOut = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
