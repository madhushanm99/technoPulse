package com.example.techno_pulse;

public class FeaturedNews {
    private int imageResId;
    private String title;
    private String author;

    public FeaturedNews(int imageResId, String title, String author) {
        this.imageResId = imageResId;
        this.title = title;
        this.author = author;
    }
    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
}
