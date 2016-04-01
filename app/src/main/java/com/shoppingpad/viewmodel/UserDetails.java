package com.shoppingpad.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.tool.Binding;

import com.shoppingpad.BR;
import com.shoppingpad.model.ContentInfoModel;

/**
 * Created by bridgelabz4 on 26/3/16.
 * 1.creating Data binding with Collapsing toolbar
 * 2.want to set Image And Tittle
 */
public class UserDetails extends BaseObservable
{
    public String mUserName;       //user Name
    public String mUserIcon;       //user Icon


//    public UserDetails(ContentInfoModel contentInfoModel)
//     {
//        this.mUserIcon=contentInfoModel.mImagesLink;
//        this.mUserName=contentInfoModel.mDisplay_name;
//    }
     @Bindable
    public String getmUserName()
    {
        return mUserName;
    }

    public void setUserName(String mUserName)
    {
        this.mUserName = mUserName;
        notifyPropertyChanged(BR.mUserName);
    }

    @Bindable
    public String getmUserIcon()
    {
        return mUserIcon;
    }

    public void setUserIcon(String mUserIcon)
    {
        this.mUserIcon = mUserIcon;
        notifyPropertyChanged(BR.mUserIcon);
    }
}
