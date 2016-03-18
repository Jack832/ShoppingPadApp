package com.shoppingpad.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.oberver.ContentListViewObserver;
import com.shoppingpad.viewmodel.ContentListViewHandler;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 * purpose:
 *
 * 1.This will display the RecyclerView with contents list.
 * 2.This is our main UI class holds UI elements text of Activity.
 * 3.It listen to Actions done calls view model to update the UI state.
 * 4.It implements Observer pattern.
 * 5.it is the view of MVVM pattern
 *
 * it will hold the View model object.
 *
 */

public class ContentListView extends AppCompatActivity {
    //contents ViewModel Attribute.
    ContentListViewHandler mContentListViewHandler;
    //initializing recycler view
    RecyclerView mRecyclerView;
    //adapter for passing data
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    int UserId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv1);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.e("pass", "context");
        LoadContentData contentData= new LoadContentData();
        contentData.execute();

   }

    class LoadContentData extends AsyncTask<Void, Void, Void> {
        public ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog= new ProgressDialog(ContentListView.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setTitle("please wait");
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            mContentListViewHandler = new ContentListViewHandler(ContentListView.this);
//            mContentListViewHandle.getContentViewList();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
//            mAdapter = new ContentListAdapter(ContentListView.this,mContentListViewHandler);
            mAdapter = new BindingAdapter(ContentListView.this,mContentListViewHandler);

            mRecyclerView.setAdapter(mAdapter);

        }
    }
}