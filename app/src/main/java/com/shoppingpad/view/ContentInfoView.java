package com.shoppingpad.view;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingpad.BR;
import com.shoppingpad.R;
import com.shoppingpad.databinding.ContentInfoBinding;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.viewmodel.ContentInfoViewHandler;
import com.shoppingpad.viewmodel.UserDetails;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by bridgelabz4 on 21/3/16.
 *
 * 1.this is Content info view of user.
 * 2.it shows recycler view with Participants .
 * 3.it call viewModel to set the data of view.
 * 4.also using collapsing toolbar in it.
 * 5.this class get data from its previous screen using Bundle
 */

public class ContentInfoView extends AppCompatActivity
{
    private Toolbar mToolbar;
    ContentInfoViewHandler mContentInfoHandler;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    String contentId;
    TextView title;
    ImageView imageView;
    UserDetails mUserDetails;
    public ContentInfoBinding binding;
    public ContentInfoModel mContentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.content_info);
        mContentModel = new ContentInfoModel();
        mToolbar= (Toolbar) findViewById(R.id.Toolbar);

        title= (TextView) findViewById(R.id.title);
        setSupportActionBar(mToolbar);
        mContentInfoHandler = new ContentInfoViewHandler(getApplicationContext());
        mCollapsingToolbarLayout= (CollapsingToolbarLayout) findViewById
                                                         (R.id.CollapsingToolbar);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            return;
        }
        contentId= extras.getString("ContentId");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView= (RecyclerView) findViewById(R.id.participants);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .showLastDivider()
                .color(Color.GRAY)
                .sizeResId(R.dimen.divider)
                .marginResId(R.dimen.leftMargin, R.dimen.rightMargin)
                .build());
        AsyncCallForParticipantData participantData= new AsyncCallForParticipantData();
        participantData.execute();

        //ToolBarText(title);
        imageView= (ImageView) findViewById(R.id.profile_id);
        //ToolBarColorChange();
    }

    //providing the size of text for collapsing effect
    private void ToolBarText(TextView title)
    {
//        title.setMaxWidth(R.style.collapse);
//        title.setMinWidth(R.style.expand);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance
                                                (R.style.collapse);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance
                (R.style.expand);
    }

    //this will change toolbarColor as per image
    private void ToolBarColorChange()
    {
         BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
         Bitmap bitmap = drawable.getBitmap();
         //to set the only one Color for Toolbar
         //Bitmap bitmap= BitmapFactory.decodeResource(getResources(),
                                                   // R.drawable.nehra);
         Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
             @Override
             public void onGenerated(Palette palette) {
                 mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor
                         (R.attr.colorPrimary));
                 mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor
                         (R.attr.colorPrimaryDark));
             }
         });
    }

    //Thread which will download data of participants for particular
    // contentId user
    public class AsyncCallForParticipantData extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog= new ProgressDialog(ContentInfoView.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setTitle("please wait");
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            mContentInfoHandler.getDataForParticipantList(contentId);
            mContentInfoHandler.getUserInformation(contentId,binding);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            mAdapter=new ContentInfoAdapter(ContentInfoView.this,mContentInfoHandler);
            mRecyclerView.setAdapter(mAdapter);
            mProgressDialog.dismiss();
        }
    }
}
