package com.example.android.guardiannews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;


public class getNewsData {

    private static final String LOG_TAG = "getNewsData";


    private URL createURL(String url) {
        Log.d(LOG_TAG, "Inside the createURL :" + url);
        String inURL = url;
        URL outURL = null;
        try {
            outURL = new URL(inURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return outURL;
    }

    private String makeHttpRequest(URL inputURL) {
        Log.d(LOG_TAG, "Inside the makeHttpRequest");
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        String responseInString = "";
        try {
            httpURLConnection = (HttpURLConnection) inputURL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            responseInString = readResponseFromStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseInString;
    }

    private String readResponseFromStream(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "";
        StringBuilder builder = new StringBuilder();
        String responseString = "";
        try {
            line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line.trim());
                line = bufferedReader.readLine();
            }
            responseString = builder.toString();
            // Log.d("readfromStream",responseString);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return responseString;
    }


    private ArrayList<NewsData> parseResponseJson(String response) {
        Log.d(LOG_TAG, "Inside the parseResponseJson");

        // Log.d(LOG_TAG,"Inside the parseResponseJson"+response);


        String newsSection = "";
        String newsTitle = "";
        String newsAuthor = "";
        String newsPublishedDate = "";
        String newsWebURL = "";
        String newsThumbNailImageURL = "";
        ArrayList<NewsData> news = new ArrayList<>();

        if (response == null) {
            return null;
        } else {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject responseObject = jsonObject.optJSONObject("response");
                if (responseObject != null) {
                    JSONArray resultsArray = responseObject.optJSONArray("results");
                    JSONArray editorPicksArray = responseObject.optJSONArray("editorsPicks");

                    if (editorPicksArray != null) {

                        Log.d(LOG_TAG, "Inside the editorPicksArray" + editorPicksArray.length());
                        for (int i = 0; i < editorPicksArray.length(); i++) {
                            JSONObject editorPickObject = editorPicksArray.optJSONObject(i);
                            if (editorPickObject != null) {
                                Log.d(LOG_TAG, "Inside the result object");
                                newsSection = editorPickObject.optString("sectionName");
                                newsTitle = editorPickObject.optString("webTitle");
                                newsWebURL = editorPickObject.optString("webUrl");
                                newsPublishedDate = editorPickObject.optString("webPublicationDate");
                                JSONObject fieldObject = editorPickObject.optJSONObject("fields");
                                if (fieldObject != null) {
                                    newsAuthor = fieldObject.optString("byline");
                                    newsThumbNailImageURL = fieldObject.optString("thumbnail");

                                }

                            }

                            Log.d("Section", newsSection);
                            Log.d("Title", newsTitle);
                            Log.d("WebURL", newsWebURL);
                            Log.d("Date", newsPublishedDate);
                            Log.d("Author", newsAuthor);
                            Log.d("ImageURL", newsThumbNailImageURL);
                            //formatDate(newsPublishedDate);

                            NewsData newsData = new NewsData(newsSection, newsTitle, newsAuthor, formatDate(newsPublishedDate), formatTime(newsPublishedDate), newsThumbNailImageURL, newsWebURL);
                            news.add(newsData);
                        }

                    }
                }

            } catch (JSONException e) {
                Log.d("parse", "Error in the JSON");
                e.printStackTrace();
            }
        }
        return news;
    }


    public ArrayList<NewsData> fetchNews(String inputURL) {

        Log.d(LOG_TAG, "Inside the fetchNews");

        URL url = createURL(inputURL);

        String response = null;

        response = makeHttpRequest(url);


        ArrayList<NewsData> newsData = parseResponseJson(response);

        return newsData;


    }

    private String formatDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, date1.toString());

        SimpleDateFormat sf = new SimpleDateFormat("MMM dd,yyyy ");
        Log.d(LOG_TAG, sf.format(date1));

        return sf.format(date1);

    }

    private String formatTime(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, date1.toString());

        SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a");
        Log.d(LOG_TAG, sf.format(date1));

        return sf.format(date1);

    }

    public Bitmap getImageBitmap(String imageURL) {
        Bitmap imageBitmap = null;

        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        try {
            httpURLConnection = (HttpURLConnection) createURL(imageURL).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            imageBitmap = BitmapFactory.decodeStream(inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBitmap;


    }
}
