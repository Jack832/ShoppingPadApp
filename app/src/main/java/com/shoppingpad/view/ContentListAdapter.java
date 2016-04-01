package com.shoppingpad.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shoppingpad.BR;
import com.shoppingpad.R;
import com.shoppingpad.databinding.CustomRowBinding;
import com.shoppingpad.viewmodel.ContentListViewHandler;
import com.shoppingpad.viewmodel.ContentViewModel;

import java.io.ByteArrayOutputStream;

/**
 * Created by bridgelabz4 on 18/3/16.
 * purpose:
 * 1.UI adapter for ContentList
 * 2.It pass the Data to the Content Info Screen
 *
 */

public class ContentListAdapter extends RecyclerView.Adapter
                                        <ContentListAdapter.DisplayHolder>
{
    // creating Inflater
    LayoutInflater mInflater;
    Context context1;
    ContentListViewHandler mContentListViewHandle;
    //passing context
    public ContentListAdapter(Context context, ContentListViewHandler
            contentListViewHandler)
    {
        context1 = context;
        mInflater = LayoutInflater.from(context1);
        mContentListViewHandle = contentListViewHandler;
    }

    @Override
    public DisplayHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.custom_row, parent, false);
        DisplayHolder holder = new DisplayHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DisplayHolder holder, int position)
    {
       final ContentViewModel mContentViewModel = mContentListViewHandle.
                                            getContentInfoPosition(position);

       holder.getBinding().setVariable(BR.Information, mContentViewModel);
       holder.getBinding().executePendingBindings();

       //want to send Profile Data to next screen
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle bundle = new Bundle();
               bundle.putString("ContentId", mContentViewModel.mContentId);
               Intent intent = new Intent(context1, ContentInfoView.class)
                                                         .putExtras(bundle);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               v.getContext().startActivity(intent);
       }
       });

   }

    //calling method of ContentListViewHandler to get the size of list
    @Override
    public int getItemCount()
    {
        return mContentListViewHandle.getContentSize();
    }

    //holder class use Binding and return CustomRowClass object
    public class DisplayHolder extends RecyclerView.ViewHolder
    {
        CustomRowBinding binding;
        public DisplayHolder(View itemView)
        {
            super(itemView);
            binding= DataBindingUtil.bind(itemView);
        }
        //passing Binding Object in  this method
        public CustomRowBinding getBinding()
        {
            return binding;
        }
    }


}
