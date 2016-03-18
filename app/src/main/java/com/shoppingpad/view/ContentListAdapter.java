package com.shoppingpad.view;

import android.content.Context;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingpad.R;
import com.shoppingpad.viewmodel.ContentListViewHandler;
import com.shoppingpad.viewmodel.ContentViewModel;

/**
 * Created by bridgelabz4 on 6/3/16.
 * purpose:
 * UI adapter for list
 */

public class ContentListAdapter extends RecyclerView.Adapter
                                        <ContentListAdapter.DisplayViewHolder>
{
    // creating Inflator
    LayoutInflater mInflater;
    Context context1;
    ContentViewModel mContentViewModel;
    ContentListViewHandler mContentListViewHandle;

    //passing context
    public ContentListAdapter(Context context,ContentListViewHandler contentListViewHandler)
    {
        context1 = context;
        mInflater = LayoutInflater.from(context1);
        mContentListViewHandle =contentListViewHandler;
        //mContentViewModel= new ContentViewModel();
    }

    @Override
    public DisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.custom_row, parent, false);
        DisplayViewHolder holder = new DisplayViewHolder(view);
        return holder;
    }

    //calling required data from ContentListViewHandler also passing the data to view
    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position)
    {
        mContentViewModel = mContentListViewHandle.getContentInfoPosition(position);

        if (mContentViewModel != null)
        {
            holder.displayTitle.setText(mContentViewModel.mDisplayName);
            holder.displayImage.setImageResource(mContentViewModel.mDisplayImage);
            holder.participants.setText(mContentViewModel.mNumberofParticipants);
            holder.noOfViews.setText(mContentViewModel.mNumberofViews);
            holder.lastSeen.setText(mContentViewModel.mLastViewDateTime);
        }
    }

    @Override
    public int getItemCount()
    {
        return mContentListViewHandle.getContentSize();
    }

    //holder class which will represent TextView and etc.
    public class DisplayViewHolder extends RecyclerView.ViewHolder
    {
        ImageView displayImage;
        TextView displayTitle, lastSeen, noOfViews, participants;
        //finding the view using CustomRow
        public DisplayViewHolder(View itemView)
        {
            super(itemView);
            displayImage = (ImageView) itemView.findViewById(R.id.mainIcon);
            displayTitle = (TextView) itemView.findViewById(R.id.displayTitle);
            lastSeen = (TextView) itemView.findViewById(R.id.lastseen);
            noOfViews = (TextView) itemView.findViewById(R.id.noOfView);
            participants = (TextView) itemView.findViewById(R.id.participantsNo);
        }
    }
}