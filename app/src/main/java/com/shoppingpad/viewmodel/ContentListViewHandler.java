package com.shoppingpad.viewmodel;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ContentListViewController;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ViewModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    final static boolean             mPERFORM_UNIT_TEST = false;
    //hold the content list
    public List<ContentViewModel>    mContentList;
    //controller object;
    public ContentListViewController mContentListViewController;
    //contentViewModel Object;
    ContentViewModel mContentViewData;
    //to verify image we are using regex here
    public static final String Pattern_Image="(bmp|jpg|gif|png)";

    //populating dummy data , checking Unit test Condition
    //calling Controller
    public ContentListViewHandler(Context context)
    {
        mContentList = new ArrayList<>();
        if (mPERFORM_UNIT_TEST)
        {
            //To Check Working of the Screen using Dummy data
            // mContentList = populateDummyData();
        }
        else
        {
            mContentList= new ArrayList<>();
            mContentListViewController = new ContentListViewController(context);
        }
    }

    //this will call controller and ask for particular dataLists and
    //it also set the data with ViewModelController attribute
    public List<ContentViewModel> getContentViewList()
    {
        List<ContentInfoModel> contentInfoList=mContentListViewController.
                                                   getJsonInfoData();
        List<ViewModel> contentViewList=mContentListViewController.
                                                  getJsonViewData();

       for(int i=0;i<contentInfoList.size();i++)
       {
           mContentViewData = new ContentViewModel();

           boolean imageLink=ImageValidator(contentInfoList.get(i).mImagesLink);
           if(imageLink)
           {
               mContentViewData.setmDisplayImage(contentInfoList.get(i)
                                                                 .mImagesLink);
           }
           else
           {
               String image = "https://account.socialbakers.com/default_user.png";
               mContentViewData.setmDisplayImage(image);
           }
           mContentViewData.setmContentId(contentInfoList.get(i).mContent_id);
           mContentViewData.setmLastViewDateTime(contentViewList.get(i)
                                                        .mLastViewedDateTime);
           mContentViewData.setmNumberofParticipants(contentViewList.get(i)
                                                              .mParticipants);
           mContentViewData.setmNumberofViews(contentViewList.get(i)
                                                             .mNumberOfViews);
           mContentViewData.setmDisplayName(contentInfoList.get(i)
                                                              .mDisplay_name);
           mContentList.add(mContentViewData);
       }
       return mContentList;
    }


    //using regex to check image type is right or not
    private boolean ImageValidator(String mImagesLink)
    {
        Pattern pattern=Pattern.compile(Pattern_Image);
        Matcher matcher=pattern.matcher(mImagesLink);
        return matcher.find();
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
