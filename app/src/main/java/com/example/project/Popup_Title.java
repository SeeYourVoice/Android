package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Popup_Title extends Dialog {

    TextView tvinput;
    EditText edtNewtitle;
    Button btnTitleSave;

    String title;
    AppCompatActivity activity = null;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }


    public Popup_Title(@NonNull Context context) {
        super(context);
        setContentView(R.layout.input_title_popup);

        tvinput = findViewById(R.id.tvinput);
        edtNewtitle = findViewById(R.id.edtNewtitle);
        btnTitleSave = findViewById(R.id.btnTitleSave);

        btnTitleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtNewtitle.getText().toString();

                dismiss();


            }


        });

    }


}