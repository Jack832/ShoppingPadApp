package com.shoppingpad.model;

import org.json.JSONObject;

/**
 * Created by bridgelabz4 on 21/3/16.
 *
 * purpose:
 * 1.it holds the data for the Participant
 * 2.method receives the JsonObject and set the variables.
 */
public class ContentParticipantsModel
{
    public String mUserId;
    public String mParticipantContentId;
    public String mParticipantName;
    public String mParticipantImage;
    public String mParticipantLastViewDateTime;
    public String mParticipantNoOfViews;
    public String mParticipantAction;

    //set data from jsonObject into Participant Model Variables.
    public void setContentParticipantsModel(JSONObject participant)
    {
        mUserId = participant.optString("userId").toString();
        mParticipantContentId=participant.optString("contentId").toString();
        mParticipantName =participant.optString("Name");
        mParticipantImage=participant.optString("Image");
        mParticipantLastViewDateTime =participant.optString("lastViewedDateTime");
        mParticipantNoOfViews=participant.optString("numberOfViews");
        mParticipantAction =participant.optString("action");
    }
}
