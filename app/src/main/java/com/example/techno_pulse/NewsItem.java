package com.example.techno_pulse;

public class NewsItem {
    private int imageResId;
    private String title;
    private String author;
    private String body;
    private String date;
    private String caption;

    public NewsItem(int imageResId, String title, String author, String body, String date, String caption) {
        this.imageResId = imageResId;
        this.title = title;
        this.author = author;
        this.body = body;
        this.date = date;
        this.caption = caption;
    }
    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getBody() {
        return body;
    }
    public String getDate() {
        return date;
    }
    public String getCaption() {
        return caption;
    }
}

