package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerAdapter adapter, adapter2;
    ImageButton btnMypage, btnHome, btnDeptList;

    // 뾰로롱
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    // 뾰로롱


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist);

        // 뾰로롱
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 =(FloatingActionButton) findViewById(R.id.fab2);
        fab2 = (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        // 뾰로롱
//

        init();
        init2();

        getData();


        btnMypage = findViewById(R.id.btnMypage);
        btnHome = findViewById(R.id.btnHome);
        btnDeptList = findViewById(R.id.btnDeptList);

        btnDeptList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);


            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });


    }
    /////////////////////////////////////////////////////////////////////////////////////
    private void init2(){ // 부서목록 리사이클러뷰
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(linearLayoutManager);

        adapter2 = new RecyclerAdapter();
        recyclerView2.setAdapter(adapter2);


    }

    // 부서 목록
    private void getData2() {
        // 임의의 데이터입니다.
        List<String> listDept = Arrays.asList("교육지원1팀", "교육지원2팀", "기획팀", "전략기획팀", "홍보팀",
                "전략사업팀", "취업지원팀", "홍보팀");

        for (int i = 0; i < listDept.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data2 = new Data();
            data2.setTitle(listDept.get(i));


            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter2.addItem(data2);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter2.notifyDataSetChanged();
    }///////////////////////////////////////////////////////////////////////////////////////

    private void init() { // 회의목록 리사이클러뷰
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
     }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("국화", "사막", "수국", "해파리", "코알라", "등대", "펭귄", "튤립",
                "국화", "사막", "수국", "해파리", "코알라", "등대", "펭귄", "튤립");
        List<String> listContent = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다.",
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다."
        );

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
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
                Intent intent2 = new Intent(MainActivity.this, CalendarActivity.class);
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


