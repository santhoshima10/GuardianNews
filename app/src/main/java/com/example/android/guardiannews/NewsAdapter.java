package com.example.android.guardiannews;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsData> {

    List<NewsData> mData;

    public NewsAdapter(@NonNull Context context, @NonNull List<NewsData> objects) {
        super(context, 0, objects);
        mData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listViewItem = convertView;
        TextView newsSectionTxtVw;
        TextView newsTitleTxtVw;
        TextView newsAuthorTxtVw;
        TextView newsPublishedDateTxtVw;
        TextView newsPublishedDateTimeTxtVw;
        ImageView newsImageThumbNail;


        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        newsSectionTxtVw = listViewItem.findViewById(R.id.news_article_section_id);
        newsTitleTxtVw = listViewItem.findViewById(R.id.news_article_title_id);
        newsAuthorTxtVw = listViewItem.findViewById(R.id.news_article_author_id);
        newsPublishedDateTxtVw = listViewItem.findViewById(R.id.news_article_published_date_id);
        newsImageThumbNail = listViewItem.findViewById(R.id.news_article_thumbnail_img_id);
        newsPublishedDateTimeTxtVw = listViewItem.findViewById(R.id.news_article_published_time_id);

        newsSectionTxtVw.setText(mData.get(position).getmNewsSection());
        newsTitleTxtVw.setText(mData.get(position).getmNewsTitle());
        newsAuthorTxtVw.setText(mData.get(position).getmNewsAuthor());
        newsPublishedDateTxtVw.setText(mData.get(position).getmNewsPublishedDate());
        newsPublishedDateTimeTxtVw.setText(mData.get(position).getmNewsPublishedDateTime());

        if (!mData.get(position).getmNewsImageURL().isEmpty()) {
            new imageLoaderAsyncTask(newsImageThumbNail).execute(mData.get(position).getmNewsImageURL());
        }
        // Picasso.get().load(mData.get(position).getmNewsImageURL()).into(newsImageThumbNail);

        return listViewItem;
    }

    private class imageLoaderAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageVw;

        public imageLoaderAsyncTask(ImageView imageView) {
            imageVw = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            getNewsData getNewsData = new getNewsData();
            Bitmap imageBmp = getNewsData.getImageBitmap(strings[0]);
            return imageBmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageVw.setImageBitmap(bitmap);
        }
    }
}
