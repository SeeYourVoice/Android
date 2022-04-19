package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ResultAdapter mResultAdapter;
    private ArrayList<ResultItem> mResultItems;
    Button btnGoToReport;
    ImageButton btnHome, btnMypage;


    // 뾰로롱
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    // 뾰로롱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 뾰로롱
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2 = (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        // 뾰로롱

        btnHome = findViewById(R.id.btnHome);
        btnMypage = findViewById(R.id.btnMypage);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView2);

        /* initiate adapter */
        mResultAdapter = new ResultAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mResultAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        /* adapt data */
        mResultItems = new ArrayList<>();
        for(int i=1;i<=100;i++){
                mResultItems.add(new ResultItem(R.drawable.logo1,i + "번 째 메세지"));
        }
        mResultAdapter.setFriendList(mResultItems);


        btnGoToReport = findViewById(R.id.btnGoToReport);
        btnGoToReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                anim();
                //Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                anim();
                //Intent intent = new Intent(MainActivity.this, 레코드.class);
                // startActivity(intent);
                //Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                anim();
                Intent intent2 = new Intent(ResultActivity.this, CalendarActivity.class);
                startActivity(intent2);
                //Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }

    }
}