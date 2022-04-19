package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;


public class PopUp_mypage extends Dialog {


    CircleImageView circle_iv;
    EditText edtMypageFname,edtMypageLname;
    Button btnSave, btnImgFind;

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
        circle_iv=findViewById(R.id.circle_Page);

        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=edtMypageFname.getText().toString();
                lname=edtMypageLname.getText().toString();

                SharedPreferences sp= getContext().getSharedPreferences("info",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("first_name",fname);
                editor.putString("last_name",lname);
                editor.commit();

                Log.d("이름_popup",fname);
                Log.d("이름_popup",lname);


                dismiss();
            }
        });


    }




}