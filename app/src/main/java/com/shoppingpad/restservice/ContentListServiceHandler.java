package com.shoppingpad.restservice;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ControllerModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 * purpose:
 * 1.this will call the RestService which is server .
 * 2.it will fetch the data from server
 * the data from server is given to controller.
 */
public class ContentListServiceHandler {
    public static final boolean mPERFORM_UNIT_TEST = false;
    //url of Json
    String ContentInfoUrl = "http://54.165.130.78:3000/api/v4/contentinfo";
    String ContentViewUrl = "http://54.165.130.78:3000/api/v4/usercontentview";
    static JSONArray jsonArray = null;
    public JSONArray jsonArrayView;
    public JSONArray jsonArrayInfo;

    //generating Dummy data here.
    public ContentListServiceHandler() {
        if (mPERFORM_UNIT_TEST) {
            populateDummyData();
        } else {
            jsonArrayView = new JSONArray();
            jsonArrayInfo = new JSONArray();
            jsonArrayView = getJsonArrayOfView();
            jsonArrayInfo = getJsonArrayOfInfo();
        }
    }

    //creating method which will create dummy for ContentInfo and ContentViews
    private void populateDummyData() {
        // populateContentInfoDummyData();
        // populateContentViewDummyData();
    }

    //send jsonArray to Controller
    public JSONArray getJsonArrayOfView() {
        jsonArrayView = getJsonData(ContentViewUrl);
        return jsonArrayView;
    }

    //send jsonArray to Controller
    public JSONArray getJsonArrayOfInfo() {
        JSONArray jsonArrayInfo = getJsonData(ContentInfoUrl);
        return jsonArrayInfo;
    }


    //the url call and json reading to get JsonArray
    public JSONArray getJsonData(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int Statuscode = statusLine.getStatusCode();
            if (Statuscode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                InputStream content = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                String Line;
                while ((Line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(Line);
                }
            } else {
                Log.e("d==", "download not completed");
            }
        } catch (ClientProtocolException eb) {
            eb.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jsonArray = new JSONArray(stringBuilder.toString());
        } catch (JSONException e) {
            Log.e("json", "Error" + e.toString());
        }
        return jsonArray;
    }
}
    //Dummy data for ContentViews
    /*
    private void populateContentViewDummyData()
    {
        // json array creation
        contentViewData="{\"ShoppingPad\":[{\"Content_id\":0,\"lastViewedDateTime\":" +
                "\"12/1/16\",\"numberOfViews\":52}," +
                "{\"Content_id\":1,\"lastViewedDateTime\":\"12/2/16\"," +
                "\"numberOfViews\":42}]}";
      try
        {
            JSONObject jsonObject= new JSONObject(contentViewData);
            JSONArray jsonArray=jsonObject.optJSONArray("ShoppingPad");
            for(int i=0;i<jsonArray.length();i++)
            {
            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            ContentServiceModel contentServiceModel =new ContentServiceModel();
            contentServiceModel.mLastViewDateTimeService = jsonObject1.
                    optString("lastSeen").toString();
            contentServiceModel.mNumberofViewsService    = Integer.parseInt
                    (jsonObject1.optString("noOfView").toString());
            contentServiceModel.mNumberofParticipantsService = Integer.parseInt
                    (jsonObject1.optString("participants").toString());
            contentServiceModel.mContentidService=Integer.parseInt
                    (jsonObject1.optString("ContentId").toString());
            mServiceViewModel.add(contentServiceModel);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
 }

    //Dummy Data for ContentInfo
    private void populateContentInfoDummyData()
    {

        contentInfoData="{\"ShoppingPad\":[{\"Content_id\":0,\"display_name\":\"Karma\"}," +
                "{\"Content_id\":1,\"display_name\":\"Sharma\"}]}";

    }*/


