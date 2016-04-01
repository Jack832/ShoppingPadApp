package com.shoppingpad.viewmodel;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.shoppingpad.BR;

/**
 * Created by bridgelabz4 on 7/3/16.
 *
 * purpose:
 *
 * 1.This store the attribute required for view list.
 * 2.using Bindable

*/

public class ContentViewModel extends BaseObservable
{
    //class member attributes
    public String   mDisplayName;                  //it holds content name.
    public String   mDisplayImage;                 //it holds content ThumbView link.
    public String   mLastViewDateTime;             //it holds content Last seen date and time.
    public String   mNumberOfViews;                // number of views
    public String   mNumberOfParticipants;         //number of participants
    public String   mContentId;                    //content Id

    public String getmContentId()
    {
        return mContentId;
    }

    public void setmContentId(String mContentId)
    {
        this.mContentId = mContentId;
    }

    @Bindable
    public String getmDisplayImage()
    {
        return mDisplayImage;
    }

    public void setmDisplayImage(String mDisplayImage)
    {
        this.mDisplayImage = mDisplayImage;
        notifyPropertyChanged(BR.mDisplayImage);
    }

    @Bindable
    public String getmDisplayName()
    {
        return mDisplayName;
    }

    public void setmDisplayName(String mDisplayName)
    {
        this.mDisplayName = mDisplayName;
        notifyPropertyChanged(BR.mDisplayName);
    }

    @Bindable
    public String getmLastViewDateTime()
    {
        return mLastViewDateTime;
    }

    public void setmLastViewDateTime(String mLastViewDateTime)
    {
        this.mLastViewDateTime = mLastViewDateTime;
        notifyPropertyChanged(BR.mLastViewDateTime);
    }

    @Bindable
    public String getmNumberofViews()
    {
        return mNumberOfViews;
    }

    public void setmNumberofViews(String mNumberOfViews)
    {
        this.mNumberOfViews = mNumberOfViews;
        notifyPropertyChanged(BR.mNumberofViews);
    }

    @Bindable
    public String getmNumberofParticipants()
    {
        return mNumberOfParticipants;
    }

    public void setmNumberofParticipants(String mNumberOfParticipants)
    {
        this.mNumberOfParticipants = mNumberOfParticipants;
        notifyPropertyChanged(BR.mNumberofParticipants);
    }
}
