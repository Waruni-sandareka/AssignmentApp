package com.example.fotnews.model;
public class News {
    private int id;
    private String title;
    private String content;
    private int imageResId;

    public News(int id, String title, String content, int imageResId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getImageResId() { return imageResId; }
}

