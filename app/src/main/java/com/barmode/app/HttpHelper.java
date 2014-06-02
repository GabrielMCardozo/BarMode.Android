package com.barmode.app;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gabriel on 25/05/2014.
 */
public class HttpHelper {

    public static InputStream postStream(String url,String jsonData) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(url);

        try {

            postRequest.setHeader("Content-Type","application/json");
            postRequest.setEntity( new StringEntity(jsonData));


            HttpResponse getResponse = client.execute(postRequest);

            final int statusCode = getResponse.getStatusLine().getStatusCode();


            if (statusCode >= 400) {
                final String reasonPhrase = getResponse.getStatusLine().getReasonPhrase();
                Log.w("HttpHelper", "Error: "+ reasonPhrase + statusCode + " for URL " + url);
              return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();
        }
        catch (IOException e) {
            Log.w("HttpHelper", "Error for URL " + url, e);
        }

        return null;
    }

    public static InputStream putStream(String url,String jsonData) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPut putRequest = new HttpPut(url);

        try {

            putRequest.setHeader("Content-Type","application/json");
            putRequest.setEntity( new StringEntity(jsonData));

            HttpResponse getResponse = client.execute(putRequest);

            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode >= 400) {
                final String reasonPhrase = getResponse.getStatusLine().getReasonPhrase();
                Log.w("HttpHelper", "Error: "+ reasonPhrase + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();
        }
        catch (IOException e) {
            Log.w("HttpHelper", "Error for URL " + url, e);
        }

        return null;
    }

    public static InputStream retrieveStream(String url)  {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode >= 400){
                final String reasonPhrase = getResponse.getStatusLine().getReasonPhrase();
                Log.w("HttpHelper", "Error " + statusCode + " for URL " + url);
               return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        }
        catch (IOException e) {
            getRequest.abort();
            Log.w("HttpHelper", "Error for URL " + url, e);
        }
        return null;
    }

}
