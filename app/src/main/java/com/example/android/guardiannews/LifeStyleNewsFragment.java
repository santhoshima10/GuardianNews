package com.example.android.guardiannews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LifeStyleNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsData>> {

    private static final String LOG_TAG = "LifeStyleNewsFragment";
    private ArrayList<NewsData> mNewsData = new ArrayList<>();

    private ImageView noNewsImage;
    private ImageView noInternet;
    private TextView noInternetMessage;
    private TextView noNewsMessage;
    private NewsAdapter mListViewAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lifestyle_news, container, false);
        ListView listView = rootView.findViewById(R.id.list_view_id);
        noNewsImage = rootView.findViewById(R.id.no_news_img_id);
        noInternet = rootView.findViewById(R.id.no_internet_img_id);
        noInternetMessage = rootView.findViewById(R.id.no_internet_msg_id);
        noNewsMessage = rootView.findViewById(R.id.no_news_msg_id);

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            noInternet.setVisibility(View.VISIBLE);
            noInternetMessage.setVisibility(View.VISIBLE);
        } else {

            mListViewAdapter = new NewsAdapter(getActivity(), mNewsData);
            listView.setAdapter(mListViewAdapter);
            getActivity().getSupportLoaderManager().initLoader(3, null, this).forceLoad();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mNewsData.get(position).getmNewsWebURL()));
                    startActivity(intent);
                }
            });
        }
        return rootView;

    }

    @NonNull
    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "Inside the OnCreateLoader");
        String inputURL = buildURL();
        return new NewsLoader(getActivity(), inputURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsData>> loader, List<NewsData> data) {

        mListViewAdapter.clear();

        Log.d(LOG_TAG, "Inside the OnLoadFinished");
        if (data != null && !data.isEmpty()) {
            mListViewAdapter.addAll(data);
        } else {
            noNewsImage.setVisibility(View.VISIBLE);
            noNewsMessage.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsData>> loader) {
        mListViewAdapter.clear();

    }

    private String buildURL() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("us")
                .appendPath("lifeandstyle")
                .appendQueryParameter("show-editors-picks", "true")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-fields", "byline,thumbnail")
                .appendQueryParameter("api-key", BuildConfig.GuardianAPIKey);
        Log.d(LOG_TAG, "the url" + builder.toString());

        return builder.toString();
    }

}
