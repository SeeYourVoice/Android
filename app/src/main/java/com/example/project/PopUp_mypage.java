package com.example.project;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PopUp_mypage extends Dialog {

    EditText edtText;
    Button btnSave;

    public PopUp_mypage(@NonNull Context context){
        super(context);
        setContentView(R.layout.mypage_info);

        edtText=findViewById(R.id.edtMypageFname);
        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }




}