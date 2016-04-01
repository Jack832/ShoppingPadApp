package com.shoppingpad.model;

import org.json.JSONObject;

/**
 * Created by bridgelabz4 on 15/3/16.
 */
public class ViewModel
{
    public   String  mNumberOfViews;             // number of views
    public   String  mDisplayProfile;            //profile image
    public   String  mLastViewedDateTime;        //it holds content Last seen
                                                 // date and time.
    public   String  mEmail;                     //contain email id
    public   String  mLastName;                  //last name of ContentUser
    public   String  mFirstName;                 //first name of contentUser
    public   String  mParticipants;              //no of participants
    public   String  mAction;                    //action performed
    public   int     mUserId;                    //user id
    public   int     mContent_idView;            //content id
    public   int     mUserAdminId;               //Admin id
    public   String  mUserContentId ;            //User content Id

    //reading values from Json Object .
    public void setContentViewData(JSONObject jsonObject)
    {
        mContent_idView    = jsonObject.optInt("contentId");
        mNumberOfViews     = jsonObject.optString("numberOfViews").toString();
        mLastViewedDateTime= jsonObject.optString("lastViewedDateTime")
                             .toString();
        mDisplayProfile    = jsonObject.optString("displayProfile").toString();
        mLastName          =jsonObject.optString("lastName").toString();
        mFirstName         = jsonObject.optString("firstName").toString();
        mUserId            = Integer.parseInt(jsonObject.optString("userId").
                                                                   toString());
        mUserAdminId       =Integer.parseInt(jsonObject.optString("userAdminId")
                                                                  .toString());
        mUserContentId     =jsonObject.optString("userContentId").toString();
        mEmail             =jsonObject.optString("email").toString();
        mParticipants      =jsonObject.optString("numberofparticipant").toString();
        mAction            =jsonObject.optString("action").toString();
    }
}
