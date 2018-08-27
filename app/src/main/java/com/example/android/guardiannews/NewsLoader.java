package com.example.android.guardiannews;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {

    String mURL;

    public NewsLoader(Context context,String url) {
        super(context);
        mURL = url;

    }

    @Override
    public List<NewsData> loadInBackground() {
        getNewsData newsData = new getNewsData();
        ArrayList<NewsData> data = newsData.fetchNews(mURL);
        return data;
    }
}
