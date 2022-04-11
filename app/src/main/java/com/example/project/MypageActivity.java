package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class MypageActivity extends AppCompatActivity {

    ImageButton btnHome;
    TextView tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        btnHome = findViewById(R.id.btnHome);
        tvName=findViewById(R.id.tvName);

        Intent MypageIntent= getIntent();
        HashMap<String, String> info =(HashMap<String, String>)MypageIntent.getSerializableExtra("info");
        String first_name=info.get("first_name");
        String last_name=info.get("last_name");

        //합쳐서 출력
        tvName.setText(first_name+last_name);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}