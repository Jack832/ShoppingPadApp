package com.shoppingpad.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shoppingpad.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * Created by bridgelabz4 on 18/3/16.
 * purpose:
 * 1. to set binding with imageView
 */
public class ImageBinding
{
    //to bind image we use this method it contanis the url and image view
    //position we are using picasso to load the image from url
    @BindingAdapter("bind:imageUrl")
    public static void getDisplayImage(final ImageView imageView,
                                                      final String url)
    {
        final Context context=imageView.getContext();
        Picasso.with(context).load(url).networkPolicy(NetworkPolicy.OFFLINE)
        .into(imageView, new Callback()
        {
            @Override
            public void onSuccess()
            {}
            @Override
            public void onError()
            {
            Picasso.with(context).load(url).placeholder(R.drawable.nehra)
                      .error(R.drawable.nehra).into(imageView);
            }
        });


    }
}
