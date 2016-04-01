package com.shoppingpad.viewmodel;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ContentListViewController;
import com.shoppingpad.databinding.ContentInfoBinding;
import com.shoppingpad.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bridgelabz4 on 21/3/16.
 *
 * 1.it Handles the Data From Controller .
 * 2.it calls ContentInfoDataModel to bind the Data with view.
 * 3.creating methods which will pass data to Adapter.
 */

public class ContentInfoViewHandler
{
    final static  boolean       mPERFORM_UNIT_TEST=false;
    List<ContentInfoDataModel>  mInfoModel;
    ContentInfoDataModel        mContentInfoSSModel;
    ContentListViewController   mContentListViewController;
    UserDetails                 mUserDetails;
    //pattern to search Image extension
    public static final String  mPattern_Image="(bmp|jpg|gif|png)";

    private Pattern pattern;
    private Matcher matcher;

    //the controller calling and unit test action
    public ContentInfoViewHandler(Context context)
    {

        mInfoModel= new ArrayList<>();
        mUserDetails= new UserDetails();

        if(mPERFORM_UNIT_TEST)
        {
            mInfoModel=populateData();
        }
        else
        {
            mContentListViewController= new ContentListViewController(context);
        }
    }

    private List<ContentInfoDataModel> populateData()
    {
        int icon[]={R.drawable.nehra,R.drawable.lego};
        String title[]={"India","Logo"};
        String lastseen[]={"12/1/16","25/2/16"};
        String views[]={"45","23"};
        String action[]={"closed","opened"};
        for(int i=0;i<icon.length && i<action.length; i++)
        {
            ContentInfoDataModel contentInfoSSModel= new ContentInfoDataModel();
            contentInfoSSModel.mParticipantAction=action[i];
            contentInfoSSModel.mParticipantName=title[i];
            contentInfoSSModel.mParticipantLastSeen=lastseen[i];
            contentInfoSSModel.mParticipantViews=views[i];
            mInfoModel.add(contentInfoSSModel);
        }
      return mInfoModel;
    }


    // we will call controller to get the ParticipantsList
    public List<ContentInfoDataModel> getDataForParticipantList(String ContentId)
    {

        List<ContentParticipantsModel> participant=mContentListViewController.
                                               getParticipantData(ContentId);

        //here we will set the view of Participants.
        for(int i=0;i<participant.size();i++)
        {
            mContentInfoSSModel = new ContentInfoDataModel();
            boolean participantImage=ImageValidator(participant.get(i).
                                                          mParticipantImage);
            if(participantImage)
            {
                mContentInfoSSModel.mParticipantIcon = participant.get(i)
                                                            .mParticipantImage;
            }
            else
            {
                String image="https://account.socialbakers.com/default_user.png";
                mContentInfoSSModel.setmParticipantIcon(image);
            }
            mContentInfoSSModel.mParticipantName = participant.get(i)
                                                            .mParticipantName;
            mContentInfoSSModel.mParticipantViews = participant.get(i)
                                                       .mParticipantNoOfViews;
            mContentInfoSSModel.mParticipantLastSeen = participant.get(i)
                                                .mParticipantLastViewDateTime;
            mContentInfoSSModel.mParticipantAction = participant.get(i)
                                                          .mParticipantAction;
            mInfoModel.add(mContentInfoSSModel);
        }
        return mInfoModel;
    }

    //it Check the image extension
    private boolean ImageValidator(String mDisplayProfile)
    {
        pattern = Pattern.compile(mPattern_Image);
        matcher = pattern.matcher(mDisplayProfile);
        return matcher.find();
    }

    //it will return the position of view
    public ContentInfoDataModel getPositionOfView(int position)
    {
        return mInfoModel.get(position);
    }

    //it will return the size of ArrayList
    public int getSizeofList()
    {
        return mInfoModel.size();
    }

    public void getUserInformation(String contentId,ContentInfoBinding binding)
    {
       ContentInfoModel contentInfoModel=mContentListViewController.getUserData(contentId);
        Log.e("conentid",""+contentId);
        mUserDetails.setUserName(contentInfoModel.mDisplay_name);
        mUserDetails.setUserIcon(contentInfoModel.mImagesLink);
        binding.setData(mUserDetails);

    }
}
