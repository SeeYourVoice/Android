package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CalendarActivity extends AppCompatActivity {

    // 필드변수
    public String fname=null;
    public String str=null;
    public CalendarView calendarView;
    public Button btnchange,btndel,btnsave;
    public TextView tvdiary,tvmemo,tvcaltitle;
    public EditText contextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 초기화
        setContentView(R.layout.activity_calendar);
        calendarView=findViewById(R.id.calendarView);
        tvdiary=findViewById(R.id.tvdiary);
        btnsave=findViewById(R.id.btnsave);
        btndel=findViewById(R.id.btndel);
        btnchange=findViewById(R.id.btnchange);
        tvmemo=findViewById(R.id.tvmemo);
        tvcaltitle=findViewById(R.id.tvcaltitle);
        contextEditText=findViewById(R.id.contextEditText);

        //로그인 및 회원가입 엑티비티에서 이름을 받아옴
        Intent intent = getIntent();
        String name = intent.getStringExtra("last_name");
        final String userID = intent.getStringExtra("user_email");
        tvcaltitle.setText(name+"님의 캘린더");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tvdiary.setVisibility(View.VISIBLE);
                btnsave.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                tvmemo.setVisibility(View.INVISIBLE);
                btnchange.setVisibility(View.INVISIBLE);
                btndel.setVisibility(View.INVISIBLE);
                tvdiary.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                contextEditText.setText("");
                checkDay(year,month,dayOfMonth,userID);
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str=contextEditText.getText().toString();
                tvmemo.setText(str);
                btnsave.setVisibility(View.INVISIBLE);
                btnchange.setVisibility(View.VISIBLE);
                btndel.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                tvmemo.setVisibility(View.VISIBLE);

            }
        });
    }

    public void  checkDay(int cYear,int cMonth,int cDay,String userID){
        fname=""+userID+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수

        try{
            fis=openFileInput(fname);

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            tvmemo.setVisibility(View.VISIBLE);
            tvmemo.setText(str);

            btnsave.setVisibility(View.INVISIBLE);
            btnchange.setVisibility(View.VISIBLE);
            btndel.setVisibility(View.VISIBLE);

            btnchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    tvmemo.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    btnsave.setVisibility(View.VISIBLE);
                    btnchange.setVisibility(View.INVISIBLE);
                    btndel.setVisibility(View.INVISIBLE);
                    tvmemo.setText(contextEditText.getText());
                }

            });
            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvmemo.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    btnsave.setVisibility(View.VISIBLE);
                    btnchange.setVisibility(View.INVISIBLE);
                    btndel.setVisibility(View.INVISIBLE);
                    removeDiary(fname);
                }
            });
            if(tvmemo.getText()==null){
                tvmemo.setVisibility(View.INVISIBLE);
                tvdiary.setVisibility(View.VISIBLE);
                btnsave.setVisibility(View.VISIBLE);
                btnchange.setVisibility(View.INVISIBLE);
                btndel.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content="";
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}