package com.example.app.ksugym;

public class News
{
    String newsText, imgUrl;

    public News() {
    }

    public News(String newsText, String imgUrl) {
        this.newsText = newsText;
        this.imgUrl = imgUrl;
    }

    public String getNewsText() {
        return newsText;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
