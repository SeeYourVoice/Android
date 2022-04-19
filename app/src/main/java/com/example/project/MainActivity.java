package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerAdapter adapter;
    ImageButton btnMypage, btnHome, btnDeptList;

    // 뾰로롱
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    // 뾰로롱

    //Volley통신관련객체
    RequestQueue requestQueue;
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist);

        //RequestQueue객체 초기화
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(MainActivity.this);
        }

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


        init();

        getData();

        //부서정보 서버요청
        getData2();


        btnMypage = findViewById(R.id.btnMypage);
        btnHome = findViewById(R.id.btnHome);
        btnDeptList = findViewById(R.id.btnDeptList);

        // 부서 리스트 보기 (세모 버튼) -> 팝업창 뜨게 할거야
        btnDeptList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUp_Deptlist popUp_deptlist = new PopUp_Deptlist(MainActivity.this);
                popUp_deptlist.setActivity(MainActivity.this);
                popUp_deptlist.show();

                popUp_deptlist.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                        if(requestQueue == null){
                            requestQueue = Volley.newRequestQueue(getApplicationContext());

                        }
                        requestQueue.start();
                        //////////////////////////서버통신 START/////////////////////////////////////


                        String url = "http://121.147.52.219:8081/Moim_server/DeptListService";
                        int method = Request.Method.GET;

                        request = new StringRequest(
                                method,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("부서정보>>", response);

                                        try {
                                            JSONObject jsonObj = new JSONObject(response);
                                            JSONArray jsonArr = jsonObj.getJSONArray("dept_list");

                                            for(int i=0; i<jsonArr.length(); i++){
                                                Log.d("부서명>>", jsonArr.getString(i));
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("부서정보>>", error.toString());
                                    }
                                });

                        //서버요청실행
                        requestQueue.add(request);

                        /////////////////////////////서버통신 END////////////////////////////////////
                    }
                });

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


    // 부서 목록
    private void getData2() {

        // 임의의 데이터입니다.
        List<String> listDept = Arrays.asList("교육지원1팀", "교육지원2팀", "기획팀", "전략기획팀", "홍보팀",
                "전략사업팀", "취업지원팀", "홍보팀");

    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

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

