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
    public int      mDisplayImage;                 //it holds content ThumbView link.
    public String   mLastViewDateTime;             //it holds content Last seen date and time.
    public String      mNumberofViews;                // number of views
    public String      mNumberofParticipants;         //number of participants


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
        return mNumberofViews;
    }

    public void setmNumberofViews(String mNumberofViews)
    {
        this.mNumberofViews = mNumberofViews;
        notifyPropertyChanged(BR.mNumberofViews);
    }

    @Bindable
    public String getmNumberofParticipants()
    {
        return mNumberofParticipants;
    }

    public void setmNumberofParticipants(String mNumberofParticipants)
    {
        this.mNumberofParticipants = mNumberofParticipants;
        notifyPropertyChanged(BR.mNumberofParticipants);
    }
}
