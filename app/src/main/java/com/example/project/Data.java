package com.example.project;


public class Data {

    private String title;
    private String content;
    private int resId, image;

    public Data() {

    }
    public Data(int image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getImage() {return image;}

    public void setImage(int image) {
        this.image = image;
    }
}




