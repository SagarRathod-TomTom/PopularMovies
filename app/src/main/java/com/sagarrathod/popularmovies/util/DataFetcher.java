/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.util;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
        * The base class for all network operation.
        *
        * @author Sagar Rathod
        * @version 1.0
        */
public class DataFetcher {

    /**
     * Connects to the host specified by Uri and retrieves the data.
     * @param uri
     * @return
     */
    @Nullable
    public static String fetchData(Uri uri) {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuffer buffer;
        String data;
        try {

              URL url = new URL(uri.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();

            if (inputStream == null)
                return null;

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            buffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null)
                buffer.append(line);

            if (buffer.length() == 0)
                return null;

            data = buffer.toString();

            return data;

        } catch (MalformedURLException e) {
            Log.e("DataFetcher", "Url error", e);
        } catch (IOException e) {
            Log.e("DataFetcher", "IO Error", e);
        } finally {

            if (httpURLConnection != null)
                httpURLConnection.disconnect();

            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(DataFetcher.class.getName(), "IO Error", e);
                }
        }
        return null;
    }
}
