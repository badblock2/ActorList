package com.example.actorlist.util;

import android.content.Context;
import android.net.Uri;
import android.os.HandlerThread;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtil {
    private static final String TAG = NetworkUtil.class.getSimpleName();

    private static final String ACTORLIST_URL = "https://api.androidhive.info/contacts/";

    public static String getResponseFromHttpUrl(){
        HttpURLConnection connection = null;
        URL url= null;
        String response = "";

        try {
            Log.d(TAG,"getResponseFromHttpUrl() calls :" + ACTORLIST_URL.toString() );
            url = new URL(ACTORLIST_URL.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if(scanner.hasNext()){
                response = scanner.next();
            }

            Log.d(TAG,"Response length : " + response.length());
            scanner.close();
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null){
                Log.d(TAG,"Connection disconnect");
                connection.disconnect();
            }
        }
    }

}
