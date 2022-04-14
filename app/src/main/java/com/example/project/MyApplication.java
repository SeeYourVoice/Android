package com.example.project;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    //전역변수들 모임
    private String firstName ;
    private String lastName ;
    private String pw;


    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public String getlastName(){
        return lastName;
    }
    public void setlastName(String lastName){
        this.lastName=lastName;
    }

    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
}
