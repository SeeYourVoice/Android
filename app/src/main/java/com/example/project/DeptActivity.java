package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DeptActivity extends AppCompatActivity implements{
    ArrayList<ItemData> dataList = new ArrayList<>();
    int[] cat = {'교육지원1팀', '교육지원2팀', '기획팀', '전략기획팀', '홍보팀',
            '전략사업팀', '사업관리팀', '취업지원팀'};

    final MyRecyclerAdapter adapter = new MyRecyclerAdapter(dataList);
    static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        for (int i=0; i<10; i++) {
            dataList.add(new ItemData(cat[i], "TITLE "+i,
                    String.format("리사이클러뷰 %03d", i)));
        }

        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getApplicationContext(), "Item : "+position, Toast.LENGTH_SHORT).show();
    }

    public void onTitleClicked(int position) {
        Toast.makeText(getApplicationContext(), "Title : "+position, Toast.LENGTH_SHORT).show();
    }

    public void onContentClicked(int position) {
        Toast.makeText(getApplicationContext(), "Content : "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageViewClicked(int position) {
        Toast.makeText(getApplicationContext(), "Image : "+position, Toast.LENGTH_SHORT).show();
    }

    public void onItemLongClicked(int position) {
        adapter.remove(position);
        Toast.makeText(getApplicationContext(),
                dataList.get(position).getTitle()+" is removed",Toast.LENGTH_SHORT).show();
    }


}
