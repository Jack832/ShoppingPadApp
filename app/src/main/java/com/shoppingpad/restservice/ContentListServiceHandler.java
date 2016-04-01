package com.shoppingpad.restservice;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by bridgelabz4 on 6/3/16.
 *
 * purpose:
 * 1.this will call the RestService which is server .
 * 2.it will fetch the data from server
 * the data from server is given to controller.
 * 3.it calls the server using get method .
 * 4.it pass content Id to the server.
 */

public class ContentListServiceHandler
{
    public static final boolean mPERFORM_UNIT_TEST = false;
    //url of Json
    String ContentInfoUrl ="http://54.86.64.100:3000/api/v1/content/content-info";
    String ContentViewUrl ="http://54.86.64.100:3000/api/v1/content/user-content-view";
    static JSONArray jsonArray = null;
    //using JsonArray to load the data from Url
    public JSONArray jsonArrayView;
    public JSONArray jsonArrayInfo;
    public JSONArray participantJson=null;

    //generating Dummy data here.
    public ContentListServiceHandler()
    {
        if (mPERFORM_UNIT_TEST)
        {
            populateDummyData();
        } else
        {
            jsonArrayView = new JSONArray();
            jsonArrayInfo = new JSONArray();
            participantJson= new JSONArray();
        }
    }

    //creating method which will create dummy for ContentInfo and ContentViews
    private void populateDummyData()
    {
        // To generate Dummy Data
        // populateContentInfoDummyData();
        // populateContentViewDummyData();
    }

    //reading Url to get the data of ViewList
    public JSONArray getJsonArrayOfView()
    {
        jsonArrayView = getJsonData(ContentViewUrl);
        return jsonArrayView;
    }

    //reading Url to get the data of InfoList
    public JSONArray getJsonArrayOfInfo()
    {
        jsonArrayInfo = getJsonData(ContentInfoUrl);

        return jsonArrayInfo;
    }


    //this method is called by getJsonArrayOfInfo & View
    // the url call and json reading which returns JsonArray
    public JSONArray getJsonData(String url)
    {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try
        {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int Statuscode = statusLine.getStatusCode();
            if (Statuscode == 200)
            {
                HttpEntity entity = httpResponse.getEntity();
                InputStream content = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new
                                                   InputStreamReader(content));
                String Line;
                while ((Line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(Line);
                }
            }
            else
            {
                Log.e("d==", "download not completed");
            }
        }
        catch (ClientProtocolException eb)
        {
            eb.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            jsonArray = new JSONArray(stringBuilder.toString());
        }
        catch (JSONException e)
        {
            Log.e("json", "Error" + e.toString());
        }
        return jsonArray;
    }

    //post method to get the data from server .
    //passing contentId as parameter
    public JSONArray getDataForContentParticipants(String contentId)
    {
        int content=Integer.parseInt(contentId);
        participantJson= getContentParticipants(content);
        return participantJson;
    }

    private JSONArray getContentParticipants(int contentId)
    {
       StringBuilder stringBuilder= new StringBuilder();
       try
       {
           URL url= new URL("http://54.86.64.100:3000/api/v1/content/"+
                                                     contentId+"/participant");
           Log.e("url","url is "+url.toString());
           URLConnection connection=url.openConnection();
           connection.setDoOutput(true);
           OutputStreamWriter outputStreamWriter=new OutputStreamWriter
                                                (connection.getOutputStream());
           outputStreamWriter.flush();
           //Response from url
           BufferedReader bufferedReader=new BufferedReader(new InputStreamReader
                                                 (connection.getInputStream()));
           //Reading The Data
           String line;
           while ((line=bufferedReader.readLine()) != null)
           {
              stringBuilder.append(line);
           }
           outputStreamWriter.close();
           bufferedReader.close();
       }
       catch (UnsupportedEncodingException e)
       {
           e.printStackTrace();
       }
       catch (MalformedURLException e)
       {
           e.printStackTrace();
       }
       catch (IOException e)
       {
           e.printStackTrace();
       }
       try
       {
         jsonArray= new JSONArray(stringBuilder.toString());
       }
       catch (JSONException e)
       {
            e.printStackTrace();
       }
       return jsonArray;
    }
}



