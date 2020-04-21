package com.example.myapplication;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;



public class GetDirectionsDataMaps extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    LatLng latLng;
    int mode;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        mode = (int)objects[2];

        DownloadUrlMaps downloadUrl = new DownloadUrlMaps();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

        String[] directionsList;
        DataParserMaps parser = new DataParserMaps();
        directionsList = parser.parseDirections(s);
        displayDirection(directionsList);

    }

    public void displayDirection(String[] directionsList)
    {
        int count = directionsList.length;
        for(int i = 0;i<count;i++)
        {
            PolylineOptions options = new PolylineOptions();
            if(mode == 1){
                options.color(Color.RED);
                options.width(10);
            }
            else if(mode == 2){
                options.color(Color.BLUE);
                options.width(8);
        }
            else{
                options.color(Color.GREEN);
                options.width(12);
            }
            options.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(options);
        }
    }






}