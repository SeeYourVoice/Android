package com.example.project;

public class ResultItem {
    String message;
    int resourceId;

    public ResultItem(int resourceId, String message) {
        this.message = message;
        this.resourceId = resourceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}