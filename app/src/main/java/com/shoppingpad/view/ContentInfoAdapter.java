package com.shoppingpad.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppingpad.BR;
import com.shoppingpad.R;
import com.shoppingpad.databinding.CustomRowInfoBinding;
import com.shoppingpad.viewmodel.ContentInfoDataModel;
import com.shoppingpad.viewmodel.ContentInfoViewHandler;

/**
 * Created by bridgelabz4 on 21/3/16.
 * 1.Adapter for Recycler View  it will use binding
 * 2.holder is use to set required row attributes
 */

public class ContentInfoAdapter extends RecyclerView.Adapter
                                          <ContentInfoAdapter.DataHolder>
{
    LayoutInflater         mLayoutInflater;
    Context                context;
    ContentInfoViewHandler mContentInfoHandler;

    public ContentInfoAdapter(Context content,ContentInfoViewHandler
                                                  contentInfoViewHandler)
    {
        context=content;
        mLayoutInflater=LayoutInflater.from(context);
        mContentInfoHandler=contentInfoViewHandler;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=mLayoutInflater.inflate(R.layout.custom_row_info,
                                                          parent, false);
        DataHolder dataHolder= new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final DataHolder holder, int position)
    {
        ContentInfoDataModel mContentInfoModel= mContentInfoHandler.
                                              getPositionOfView(position);
        holder.binding.setVariable(BR.ParticipantInfo,mContentInfoModel);
        holder.binding.executePendingBindings();

//    To send SMS :
      holder.binding.ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSms=new Intent(Intent.ACTION_VIEW);
                sendSms.putExtra("sms_body","");
                sendSms.setType("vnd.android-dir/mms-sms");
                v.getContext().startActivity(sendSms);
         }
        });
    }

    @Override
    public int getItemCount()
    {
        return mContentInfoHandler.getSizeofList();
    }
    public class DataHolder extends RecyclerView.ViewHolder
    {
        CustomRowInfoBinding binding;
        public DataHolder(View itemView)
        {
            super(itemView);
            binding=DataBindingUtil.bind(itemView);
        }

    }
}
