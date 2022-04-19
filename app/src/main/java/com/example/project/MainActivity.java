package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerAdapter adapter;
    ImageButton btnMypage, btnHome, btnDeptList;
    TextView tvDep;
    RecyclerView recyclerView;

    String text;
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

        btnMypage = findViewById(R.id.btnMypage);
        btnHome = findViewById(R.id.btnHome);
        btnDeptList = findViewById(R.id.btnDeptList);

        // 부서 리스트 보기 (팝업창) ======================================================
        btnDeptList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUp_Deptlist popUp_deptlist = new PopUp_Deptlist(MainActivity.this);
                popUp_deptlist.setActivity(MainActivity.this);
                popUp_deptlist.show();

                popUp_deptlist.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                        SharedPreferences sp= getSharedPreferences("info",0);

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
////////////////////////////////////////////////////////////////////////////////////////////////////

    // 회의 리스트 ======================================================================

    // 1. Volley로 통신 -> 맨 처음엔 사용자 부서에 따른 회의 목록을 보여줘야한다.(request1)

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        tvDep = findViewById(R.id.tvDep);
        recyclerView = findViewById(R.id.recyclerView2);

        ArrayList<String> items = new ArrayList<>();

        // 아이디에 따른 부서 정보를 가져오고

        SharedPreferences sp = getSharedPreferences("rec",0);

        // 부서에 따른 회의 리스트를 가져와야한다.

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String serverUrl = "http://121.147.52.219:8081/Moim_server/RecordService";

        request = new StringRequest(
                Request.Method.POST,
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("record check", response);

                        if (!(response == null)) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray objArray = jsonObj.getJSONArray("rec_list");



                                for (int i = 0; i < items.size(); i++) {

                                    Data data = new Data();
                                    data.setTitle((String) objArray.get(i));

                                    adapter.addItem(data);
                                }
                                adapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


    }

    // 2. 부서 목록 + 버튼을 누르면 부서 리스트 팝업이 뜨고, 타 부서 이름을 누르면 타 부서의 회의 목록을 보여줘야한다. (request2)

////////////////////////////////////////////////////////////////////////////////////////////////////
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

