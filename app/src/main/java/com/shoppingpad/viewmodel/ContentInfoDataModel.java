package com.shoppingpad.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.shoppingpad.BR;

import java.util.Observable;

/**
 * Created by bridgelabz4 on 21/3/16.
 */
public class ContentInfoDataModel extends BaseObservable{

    public String mParticipantName;                //Participant Name
    public String mParticipantViews;               //Participant No fo views
    public String mParticipantAction;              //Participant Actions done
    public String mParticipantIcon;                //Icon of participants
    public String mParticipantLastSeen;            //LastSeen of Participants


    @Bindable
    public String getmParticipantName() {
        return mParticipantName;
    }

    public void setmParticipantName(String mParticipantName)
    {
        this.mParticipantName = mParticipantName;
        notifyPropertyChanged(BR.mParticipantName);
    }

    @Bindable
    public String getmParticipantViews()
    {
        return mParticipantViews;
    }

    public void setmParticipantViews(String mParticipantViews)
    {
        this.mParticipantViews = mParticipantViews;
        notifyPropertyChanged(BR.mParticipantViews);
    }

    @Bindable
    public String getmParticipantAction()
    {
        return mParticipantAction;
    }

    public void setmParticipantAction(String mParticipantAction)
    {
        this.mParticipantAction = mParticipantAction;
        notifyPropertyChanged(BR.mParticipantAction);
    }

    @Bindable
    public String getmParticipantIcon() {
        return mParticipantIcon;
    }

    public void setmParticipantIcon(String mParticipantIcon) {
        this.mParticipantIcon = mParticipantIcon;
        notifyPropertyChanged(BR.mParticipantIcon);
    }

    @Bindable
    public String getmParticipantLastSeen() {
        return mParticipantLastSeen;
    }

    public void setmParticipantLastSeen(String mParticipantLastSeen) {
        this.mParticipantLastSeen = mParticipantLastSeen;
        notifyPropertyChanged(BR.mParticipantLastSeen);

    }
}
