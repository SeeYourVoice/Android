package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class PopUp_mypage extends Dialog {

    EditText edtMypageFname,edtMypageLname;
    Button btnSave;
    String fname,lname;
    AppCompatActivity activity = null;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }



    public PopUp_mypage(@NonNull Context context){
        super(context);
        setContentView(R.layout.mypage_info);

        edtMypageFname=findViewById(R.id.edtMypageFname);
        edtMypageLname=findViewById(R.id.edtMypageLname);

        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=edtMypageFname.getText().toString();
                lname=edtMypageLname.getText().toString();

                MyApplication app = (MyApplication)PopUp_mypage.this.activity.getApplication();
                app.setFirstName(fname);
                app.setlastName(lname);
                app.flag=true;

                Log.d("이름_popup",fname);
                Log.d("이름_popup",lname);


                dismiss();
            }
        });



    }





}