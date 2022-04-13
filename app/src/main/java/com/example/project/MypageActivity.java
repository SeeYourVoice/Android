package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MypageActivity extends AppCompatActivity {

    ImageButton btnHome,btnProfile,btnDelEmail;
    TextView tvName;

    PopUp_mypage  PopUp_mypage;

    RequestQueue requestQueue;
    StringRequest request;

    String first_name,last_name,id,pw,profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        btnHome = findViewById(R.id.btnHome);
        btnProfile=findViewById(R.id.btnProfile);
        tvName=findViewById(R.id.tvName);
        btnDelEmail = findViewById(R.id.btnDelEmail);

        Intent MypageIntent= getIntent();

        HashMap<String, String> info =(HashMap<String, String>)MypageIntent.getSerializableExtra("info");
        first_name=info.get("first_name");
        last_name=info.get("last_name");
        id=info.get("user_email");
        pw=info.get("user_password");
        profile_img=info.get("profile_img");



        //합쳐서 출력
        tvName.setText(first_name+last_name);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        //홈버튼 클릭
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        //프로필 수정 클릭

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUp_mypage=new PopUp_mypage(MypageActivity.this);
                PopUp_mypage.setActivity( MypageActivity.this );
                PopUp_mypage.show();

                PopUp_mypage.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d("pop up ","꺼짐ㅡ");
                        MyApplication app = (MyApplication) MypageActivity.this.getApplication();
                        first_name= app.getFirstName();
                        last_name=app.getlastName();

                        if(requestQueue==null){
                            requestQueue = Volley.newRequestQueue(getApplicationContext());

                        }

                        String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_Edit_MyInfo_Service";

                        request= new StringRequest(
                                Request.Method.POST,
                                serverUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("성공")){
                                            Toast.makeText(MypageActivity.this,"수정 완료",Toast.LENGTH_SHORT).show();
                                            tvName.setText(first_name+last_name);

                                        }else{
                                            Toast.makeText(MypageActivity.this, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                            first_name=info.get("first_name");
                                            last_name=info.get("last_name");
                                            tvName.setText(first_name+last_name);
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("오류", "오류");
                                        first_name=info.get("first_name");
                                        last_name=info.get("last_name");
                                        tvName.setText(first_name+last_name);
                                    }
                                }
                        ){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> MypageParams= new HashMap<String, String>();

                                //계정정보 넣고 조회
                                MypageParams.put("id",id);
                                MypageParams.put("fname",first_name);
                                MypageParams.put("lname",last_name);

                                return MypageParams;
                            }
                        };

                        requestQueue.add(request);
                    }
                });






            }
        });




        btnDelEmail.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                new AlertDialog.Builder(MypageActivity.this) // TestActivity 부분에는 현재 Activity의 이름 입력.
                        .setMessage("계정을 삭제하시겠습니까?")     // 제목 부분 (직접 작성)
                        .setPositiveButton("아니요", new DialogInterface.OnClickListener() {      // 버튼1 (직접 작성)
                            public void onClick(DialogInterface dialog, int which){
                                // 여기서부터 코드 작성
                                Toast.makeText(getApplicationContext(), "취소 누름", Toast.LENGTH_SHORT).show(); // 실행할 코드



                            }
                        })
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {     // 버튼2 (직접 작성)
                            public void onClick(DialogInterface dialog, int which){
                                // 여기서부터 코드 작성
                                Toast.makeText(getApplicationContext(), "확인 누름", Toast.LENGTH_SHORT).show(); // 실행할 코드



                            }
                        })
                        .show();

            }
        });

    }




}