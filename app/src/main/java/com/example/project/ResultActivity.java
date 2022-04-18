package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ResultAdapter mResultAdapter;
    private ArrayList<ResultItem> mResultItems;
    Button btnGoToReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

}