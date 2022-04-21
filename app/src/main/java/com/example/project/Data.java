package com.example.project;


public class Data {

  private String title;
  private String Date;

    public Data(String title, String date) {
        this.title = title;
        this.Date = date;
    }

    public String getTitle() { return title;   }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}




