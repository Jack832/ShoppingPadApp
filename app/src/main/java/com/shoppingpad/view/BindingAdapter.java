package com.shoppingpad.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppingpad.BR;
import com.shoppingpad.R;
import com.shoppingpad.databinding.CustomRowBinding;
import com.shoppingpad.viewmodel.ContentListViewHandler;
import com.shoppingpad.viewmodel.ContentViewModel;

/**
 * Created by bridgelabz4 on 18/3/16.
 * purpose:
 * UI adapter for list
 */

public class BindingAdapter extends RecyclerView.Adapter<BindingAdapter.DisplayHolder> {
    // creating Inflator
    LayoutInflater mInflater;
    Context context1;
    ContentListViewHandler mContentListViewHandle;

    //passing context
    public BindingAdapter(Context context, ContentListViewHandler contentListViewHandler) {
        context1 = context;
        mInflater = LayoutInflater.from(context1);
        mContentListViewHandle = contentListViewHandler;

    }

    @Override
    public DisplayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_row, parent, false);
        DisplayHolder holder = new DisplayHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DisplayHolder holder, int position)
    {
        ContentViewModel mContentViewModel = mContentListViewHandle.getContentInfoPosition(position);
        holder.getBinding().setVariable(BR.Information,mContentViewModel);
        holder.getBinding().executePendingBindings();
    }

    //calling required data from ContentListViewHandler also passing the data to view
    @Override
    public int getItemCount()
    {
        return mContentListViewHandle.getContentSize();
    }

    //holder class which will represent TextView and etc.
    public class DisplayHolder extends RecyclerView.ViewHolder
    {
        CustomRowBinding binding;

        public DisplayHolder(View rowView)
        {
            super(rowView);
            binding= DataBindingUtil.bind(rowView);
        }

        public CustomRowBinding getBinding()
        {
            return binding;
        }

    }
}
