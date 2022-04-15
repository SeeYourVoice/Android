package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.Map;

public class MypageActivity extends AppCompatActivity {

    ImageButton btnHome,btnProfile,btnPw,btnDept,btnPosition,btnDelEmail;
    TextView tvName,tvPos,tvDept,tvMypageEmail;

    PopUp_mypage  PopUp_mypage;

    RequestQueue requestQueue;
    StringRequest request;

    String first_name,last_name,id,pw,profile_img,position_num,position_name,dept_name;

    String org_fname,org_lname,org_pos,org_posnum;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        tvName=findViewById(R.id.tvPos);
        tvPos=findViewById(R.id.tvName);
        tvDept=findViewById(R.id.tvDept);
        tvMypageEmail=findViewById(R.id.tvMypageEmail);

        btnHome = findViewById(R.id.btnHome);

        btnProfile=findViewById(R.id.btnProfile);
        btnPw=findViewById(R.id.btnMypagePw);
        btnDept=findViewById(R.id.btnMypageDept);
        btnPosition=findViewById(R.id.btnMypagePosition);
        btnDelEmail = findViewById(R.id.btnDelEmail);



        //로그인에서 넘어온 회원정보받음.


        sp= getSharedPreferences("info",0);
        first_name=sp.getString("first_name","none");
        last_name=sp.getString("last_name","none");
        id=sp.getString("user_email","none");
        pw=sp.getString("user_password","none");
        profile_img=sp.getString("profile_img","none");
        position_num=sp.getString("position_num","none");
        position_name=sp.getString("position_name","none");
        dept_name=sp.getString("dept","none");

        //원본
        org_fname=first_name;
        org_lname=last_name;
        org_pos=position_name;
        org_posnum=position_num;
        //합쳐서 출력
        tvName.setText(first_name+last_name);
        tvPos.setText(position_name);
        tvDept.setText(dept_name);
        tvMypageEmail.setText(id);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        //홈버튼 클릭   ====================
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        //프로필 수정 클릭   ====================
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
                        sp= getSharedPreferences("info",0);
                        first_name=sp.getString("first_name","none");
                        last_name=sp.getString("last_name","none");

                        Log.d("이름_mypage",first_name);
                        Log.d("이름_mypage",last_name);

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
                                            tvName.setText(org_fname+org_lname);
                                            sp= getSharedPreferences("info",0);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("first_name",org_fname);
                                            editor.putString("last_name",org_lname);
                                            editor.commit();

                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("오류", "오류");
                                        sp= getSharedPreferences("info",0);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("first_name",org_fname);
                                        editor.putString("last_name",org_lname);
                                        editor.commit();
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


        //비밀번호 변경   ====================
        btnPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUp_mypage_PW popUp_mypage_pw=new PopUp_mypage_PW(MypageActivity.this);
                popUp_mypage_pw.setActivity(MypageActivity.this);
                popUp_mypage_pw.show();

                popUp_mypage_pw.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        SharedPreferences sp= getSharedPreferences("info",0);
                        String npw=  sp.getString("user_password","none");

                        if(requestQueue==null){
                            requestQueue = Volley.newRequestQueue(getApplicationContext());

                        }

                        String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_Edit_MyPw";

                        request=new StringRequest(
                                Request.Method.POST,
                                serverUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("성공")){
                                            Toast.makeText(MypageActivity.this,"수정 완료",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(MypageActivity.this, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("오류", "비밀번호 설정오류");
                                    }
                        }){

                            @Nullable
                            @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> PwParams= new HashMap<String, String>();

                            //계정정보 넣고 조회
                            PwParams.put("id",id);
                            PwParams.put("npw",npw);

                            return PwParams;
                        }

                        };

                        requestQueue.add(request);


                    }
                });
            }
        });

        //직급변경    ====================
       btnPosition.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               PopUp_mypage_Pos popUp_mypage_pos = new PopUp_mypage_Pos(MypageActivity.this);
               popUp_mypage_pos.setActivity(MypageActivity.this);
               popUp_mypage_pos.show();

               popUp_mypage_pos.setOnDismissListener(new DialogInterface.OnDismissListener() {
                   @Override
                   public void onDismiss(DialogInterface dialogInterface) {
                       SharedPreferences sp= getSharedPreferences("info",0);

                       if(requestQueue==null){
                           requestQueue = Volley.newRequestQueue(getApplicationContext());

                       }

                       String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_Edit_MyPos";


                       request=new StringRequest(
                               Request.Method.POST,
                               serverUrl,
                               new Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String response) {

                                       if(response.equals("성공")){
                                           tvPos.setText(sp.getString("position_name","none"));
                                       }else{
                                           SharedPreferences.Editor editor = sp.edit();
                                           editor.putString("position_name",org_pos);
                                           editor.commit();
                                       }
                                   }
                               },
                               new Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError error) {


                                   }
                               }
                       ){
                           //계정 정보 편집
                           @Nullable
                           @Override
                           protected Map<String, String> getParams() throws AuthFailureError {
                               Map<String,String> Params= new HashMap<String, String>();

                               Params.put("id",id);
                               Params.put("position_name",sp.getString("position_name","none"));
                               return Params;
                           }

                       };

                       requestQueue.add(request);
                   }
               });

           }
       });





        // 회사변경    ====================
        btnDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        //회원탈퇴   ====================
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