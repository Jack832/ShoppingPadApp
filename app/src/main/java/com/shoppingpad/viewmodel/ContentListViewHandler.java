package com.shoppingpad.viewmodel;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ContentListViewController;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ViewModel;

import java.util.ArrayList;
import java.util.List;
/**
* purpose:
 *
 * 1.This class join the required Attribute from List.
 * 2.it call controller and take the data lists
 * *
 */

public class ContentListViewHandler
{
    //unit test
    final static boolean mPERFORM_UNIT_TEST = false;
    //hold the content list
    public List<ContentViewModel> mContentList;
    //controller object;
    public ContentListViewController mContentListViewController;
    //contentViewModel Object;
    ContentViewModel contentViewData;

     //populating dummy data , checking Unit test Condition
     //calling Controller
    public ContentListViewHandler(Context context)
    {
        mContentList = new ArrayList<>();
        if (mPERFORM_UNIT_TEST)
        {
            mContentList = populateDummyData();
        }
        else
        {
            mContentListViewController = new ContentListViewController(context);
            mContentList=getContentViewList();
            Log.d("values of List",""+mContentList.size());
        }
    }

    //initialize the data with required attribute
    public List<ContentViewModel> populateDummyData()
    {
        int icon[]         = {R.drawable.lego, R.drawable.lego};
        String title[]     = {"Sham", "sam"};
        String lastSeen[]  = {"12/8/16", "30/1/16"};
        int participants[] = {45,52};
        int noofviews[]    = {51,12};

        for (int i = 0; i < icon.length && i < title.length; i++)
        {
            ContentViewModel contentViewData  = new ContentViewModel();
           // contentViewData.mDisplayImage     = icon[i];
            contentViewData.mDisplayName      = title[i];
            contentViewData.mLastViewDateTime = lastSeen[i];
            //contentViewData.mNumberofViews    = noofviews[i];
            //contentViewData.mNumberofParticipants = participants[i];
            mContentList.add(contentViewData);
        }
        return mContentList;
    }

     //this will call controller and ask for particular dataLists and
    //it also set the data with ViewModelController attribute
    public List<ContentViewModel> getContentViewList()
    {
        List<ContentInfoModel> contentInfoList=mContentListViewController.
                                                   getJsonInfoData();
        List<ViewModel> contentViewList=mContentListViewController.
                                                  getJsonViewData();

        List<ContentViewModel> contentValues = new ArrayList<>();
        for(int i=0;i<contentInfoList.size();i++)
       {
          contentViewData = new ContentViewModel();
                    //contentViewData.mDisplayImage=contentInfoList.get(i)
                    //        .mDisplayImageService;

           contentViewData.setmLastViewDateTime(contentViewList.get(i).mLastViewedDateTime);
           contentViewData.setmNumberofParticipants(contentViewList.get(i).mParticipants);
           contentViewData.setmNumberofViews(contentViewList.get(i).mNumberOfViews);
           contentViewData.setmDisplayName(contentInfoList.get(i).mDisplay_name);


           contentViewData.mDisplayName          = contentInfoList.get(i)
                                                   .mDisplay_name;
           contentViewData.mNumberofParticipants = contentViewList.get(i)
                                                   .mParticipants;
           contentViewData.mLastViewDateTime     =  contentViewList.get(i)
                                                   .mLastViewedDateTime;
           contentViewData.mNumberofViews        =  contentViewList.get(i)
                                                   .mNumberOfViews;
            //mContentList.add(contentViewData);
           contentValues.add(contentViewData);
       }

        return contentValues;
    }

    //pass the data for required position
    public ContentViewModel getContentInfoPosition(int position)
    {

        return mContentList.get(position);
    }

    //using size() to get size of data
    public int getContentSize()
    {
        return mContentList.size();
    }
}
