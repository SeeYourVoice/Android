package com.example.project;

import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        tvDep = findViewById(R.id.tvDep);
        recyclerView = findViewById(R.id.recyclerView2);


        //RequestQueue객체 초기화
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(MainActivity.this);
        }


        // 아이디에 따른 부서 정보를 가져오고
        SharedPreferences sp = getSharedPreferences("rec",0); //rec에 대한 정보를 담을
        SharedPreferences sp_info = getSharedPreferences("info",0);


        //부서 이름 붙임.
        tvDep.setText(sp_info.getString("dept","부서 이름")); //키 / 디폴트


        // 부서에 따른 회의 리스트를 가져와야한다.

        //222.102.104.208:8082
        //121.147.52.219:8081

        String serverUrl = "http://222.102.104.208:8082/Moim_server/Moim_RecordService";

        ArrayList<Data> items = new ArrayList<>();
        request = new StringRequest(
                Request.Method.POST,
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("record check", response);

                        if (!(response == null)) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj= (JSONObject) jsonArray.get(i);
                                    //String text = obj.getString("");

                                    //날짜랑 제목
                                    String rec_name=obj.getString("rec_name");
                                    String rec_date=obj.getString("rec_date");

                                    //Log.d("오류",rec_date+rec_name);
                                    items.add(new Data(rec_name,rec_date));

                                }
                                adapter = new RecyclerAdapter();
                                adapter.addItem(items);
                                recyclerView.setAdapter(adapter);


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
        ){

            //request에 값을 넣어서 서버로 넘겨주는 부분.
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                Log.d("회의리스트 11111", sp_info.getString("dept_seq","none"));
                params.put("dept_seq",sp_info.getString("dept_seq","none"));

                return params;
            }
        };
        requestQueue.add(request);


        /////////////////////////////////////////////////////////////////////////

        // 뾰로롱
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2 = (FloatingActionButton) findViewById(R.id.fab1);

        adapter = new RecyclerAdapter();

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

                adapter.clear();

                popUp_deptlist.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {



                        SharedPreferences sp= getSharedPreferences("info",0);
                        String dept_choice =sp.getString("dept_choice","none");
                        tvDep.setText(dept_choice);


                        if(requestQueue == null){
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                        }

                        //requestQueue.start();

                        //////////////////////////서버통신 START/////////////////////////////////////


                        String serverUrl = "http://222.102.104.208:8082/Moim_server/Moim_RecordService";
                        int method = Request.Method.GET;
                        request = new StringRequest(
                                Request.Method.POST,
                                serverUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("record check", response);

                                        if (!(response == null)) {
                                            try {

                                                JSONArray jsonArray = new JSONArray(response);


                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject obj= (JSONObject) jsonArray.get(i);
                                                    //String text = obj.getString("");

                                                    //날짜랑 제목
                                                    String rec_name=obj.getString("rec_name");
                                                    String rec_date=obj.getString("rec_date");

                                                    //Log.d("오류",rec_date+rec_name);
                                                    items.add(new Data(rec_name,rec_date));

                                                }



                                                adapter.addItem(items);


                                                recyclerView.setAdapter(adapter);


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
                        ){

                            //request에 값을 넣어서 서버로 넘겨주는 부분.
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();


                                SharedPreferences sp= getSharedPreferences("dept_seq",0);
                                String dept_seq=sp.getString(dept_choice,"None");

                                params.put("dept_seq",dept_seq);

                                Log.d("회의리스트", dept_seq);

                                return params;
                            }
                        };
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

