package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PopUp_mypage_PW extends Dialog {

    EditText edtMypagePPw,edtMypageNPw,edtMypageCPw;
    Button btnPwSave;

    String pw,ppw,npw,cpw;

    AppCompatActivity activity = null;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public PopUp_mypage_PW(@NonNull Context context) {
        super(context);
        setContentView(R.layout.mypage_pw);

        edtMypagePPw=findViewById(R.id.edtMypagePPw);
        edtMypageNPw=findViewById(R.id.edtMypageNPw);
        edtMypageCPw=findViewById(R.id.edtMypageCPw);

        btnPwSave=findViewById(R.id.btnPwSave);

        btnPwSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication app=(MyApplication)PopUp_mypage_PW.this.activity.getApplication();
                pw=app.getPw(); //원본

                ppw=edtMypagePPw.getText().toString();
                npw=edtMypageNPw.getText().toString();
                cpw=edtMypageCPw.getText().toString();

                //1. 현재 비밀번호가 지금 비밀번호가 맞는가?
               if(pw.equals(ppw)){
                   //2. 새 비밀번호와 확인란의 텍스트값이 일치하는가?
                    if(npw.equals(cpw)){
                        app.setPw(cpw);//변경할 비밀번호 넘겨줌.
                        dismiss();
                    }else{

                        //이거 안됨.

                        edtMypageCPw.setText("일치하지 않습니다.");
                    }


               }else{
                   edtMypagePPw.setText("비밀번호가 다릅니다");
               }

            }
        });

    }
}
