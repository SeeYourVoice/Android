package com.example.project;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    //전역변수들 모임
    private String firstName ;
    private String lastName ;
    boolean flag=false;

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

}
