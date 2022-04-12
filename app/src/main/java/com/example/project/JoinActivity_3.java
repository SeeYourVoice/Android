package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity_3 extends AppCompatActivity {

    // 전역변수 선언
    TextView edtCor, edtDep, edtPos;

    Button btnCon, btnLoginGo;

    RequestQueue requestQueue;

    StringRequest request;

    HashMap<String,String> map;

    private boolean validate = false;

    private String[] dept_items = {"교육지원1팀", "교육지원2팀", "기획팀", "사업관리팀",
            "전략기획팀", "전략사업팀", "취업지원팀", "홍보팀"};
    private AlertDialog select_dept_items;

    private String[] position_items = {"원장", "부원장", "본부장", "실장",
            "이사", "부장", "팀장", "실장", "연구원"};
    private AlertDialog select_position_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_3);

        // 초기화
        btnCon = findViewById(R.id.btnCon);
        btnLoginGo = findViewById(R.id.btnLoginGo);

        edtCor = findViewById(R.id.edtCor);
////////////////////////////////////////////////////////////////////////////////////////////////////
        edtDep = (TextView) findViewById(R.id.edtDep);
        // 부서 선택
        edtDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_dept_items.show(); // 클릭하면 부서 리스트 보여주기
            }
        });

        select_dept_items = new AlertDialog.Builder(JoinActivity_3.this)
                .setItems(dept_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edtDep.setText(dept_items[i]);
                    }
                })
                .setTitle("title")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();
        // 직급선택
        edtPos = (TextView) findViewById(R.id.edtPos);
        edtPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_position_items.show(); // 클릭하면 직급 리스트 보여주기
            }
        });

        select_position_items = new AlertDialog.Builder(JoinActivity_3.this)
                .setItems(position_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edtPos.setText(position_items[i]);
                    }
                })
                .setTitle("title")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();
////////////////////////////////////////////////////////////////////////////////////////////////////

        // 로그인 페이지(이미 회원가입을 했다면)
        btnLoginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity_3.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 회원가입_3
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= getIntent();
                HashMap<String, String> joininfo3 = (HashMap<String, String>)intent.getSerializableExtra("joininfo3");

                // 서버통신
                if(requestQueue == null) {

                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                }

                String corporation = edtCor.getText().toString();
                String department = edtDep.getText().toString();
                String position = edtPos.getText().toString();

                String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_JoinService";

                // 회사이름 칸 빈칸 x
                if(validate)
                {
                    return;
                }

                if(corporation.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity_3.this );

                    Toast.makeText(JoinActivity_3.this, "회사 이름을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                //입력값 확인
                Log.d("입력", corporation);
                Log.d("입력", department);
                Log.d("입력", position);

                // 입력
                //map.put("edtCor", corporation);
                //map.put("edtDep", department);
                //map.put("edtPos", position);

                // 회원가입 성공시 LoginActivity로 이동



//////////////////////////////////////////////////////////////////////////////////////
                request = new StringRequest(
                        // Post방식
                        Request.Method.POST,
                        serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("")) {

                                    // Toast메시지 출력
                                    Toast.makeText(JoinActivity_3.this, "가입성공!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(JoinActivity_3.this, "가입실패!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("오류", "가입 오류..!");
                    }
                }

                ) {
                    @Nullable
                    @Override
                    // 서버에 데이터 전달
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        Intent intent = getIntent();
                        // 값 보내기
                        params.put("user_email",intent.getStringExtra("user_email"));
                        params.put("user_password",intent.getStringExtra("user_password"));
                        params.put("first_name",intent.getStringExtra("first_name"));
                        params.put("last_name",intent.getStringExtra("last_name"));
                        params.put("corp_name", corporation); // t_dept //-> 서버단에서 따로 처리 필요함.
                        params.put("dept_name", department); // t_dept
                        params.put("position_name", position); // t_position

                        return params;
                    }
                };



            }
        });

    }
}