package com.example.android.guardiannews;

public class NewsData {

    private String mNewsSection;
    private String mNewsTitle;
    private String mNewsAuthor;
    private String mNewsPublishedDate;
    private String mNewsPublishedDateTime;
    private String mNewsImageURL;
    private String mNewsWebURL;

    public String getmNewsSection() {
        return mNewsSection;
    }

    public void setmNewsSection(String mNewsSection) {
        this.mNewsSection = mNewsSection;
    }

    public String getmNewsTitle() {
        return mNewsTitle;
    }

    public void setmNewsTitle(String mNewsTitle) {
        this.mNewsTitle = mNewsTitle;
    }

    public String getmNewsAuthor() {
        return mNewsAuthor;
    }

    public void setmNewsAuthor(String mNewsAuthor) {
        this.mNewsAuthor = mNewsAuthor;
    }

    public String getmNewsPublishedDate() {
        return mNewsPublishedDate;
    }

    public void setmNewsPublishedDate(String mNewsPublishedDate) {
        this.mNewsPublishedDate = mNewsPublishedDate;
    }

    public String getmNewsImageURL() {
        return mNewsImageURL;
    }

    public void setmNewsImageURL(String mNewsImageURL) {
        this.mNewsImageURL = mNewsImageURL;
    }

    public String getmNewsWebURL() {
        return mNewsWebURL;
    }

    public void setmNewsWebURL(String mNewsWebURL) {
        this.mNewsWebURL = mNewsWebURL;
    }

    public String getmNewsPublishedDateTime() {
        return mNewsPublishedDateTime;
    }

    public void setmNewsPublishedDateTime(String mNewsPublishedDateTime) {
        this.mNewsPublishedDateTime = mNewsPublishedDateTime;
    }

    public NewsData(String mNewsSection, String mNewsTitle, String mNewsAuthor, String mNewsPublishedDate, String mNewsPublishedDateTime, String mNewsImageURL, String mNewsWebURL) {

        this.mNewsSection = mNewsSection;
        this.mNewsTitle = mNewsTitle;
        this.mNewsAuthor = mNewsAuthor;
        this.mNewsPublishedDate = mNewsPublishedDate;
        this.mNewsImageURL = mNewsImageURL;
        this.mNewsWebURL = mNewsWebURL;
        this.mNewsPublishedDateTime = mNewsPublishedDateTime;
    }
}
