package com.example.project;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btnJoin;
    Button btnLogin;

    EditText edtEmail, edtPassword;

    RequestQueue requestQueue;
    StringRequest request;

    HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);

        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);


        //회원가입
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity_1.class);
                startActivity(intent);
            }
        });

        //로그인
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestQueue==null){
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                }
                //아이디, 비밀번호를 loginActivity에서 받아오고
                String id= edtEmail.getText().toString();
                String pw= edtPassword.getText().toString();

                //입력값 확인
                Log.d("입력",id);
                Log.d("입력",pw);

                //임의로 설정한 서버 주소
                String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_LoginService";

                request=new StringRequest(
                        Request.Method.POST,
                        serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("응답",response);

                                try {
                                    if(!response.equals("false")) { //로그인이 성공했다면
                                        JSONObject obj= new JSONObject(response);

                                        //StringBuffer sb = new StringBuffer();

                                        map= new HashMap<String,String>();

                                        map.put("user_email",obj.getString("user_email"));
                                        map.put("user_password",obj.getString("user_password"));
                                        map.put("first_name",obj.getString("first_name"));
                                        map.put("last_name",obj.getString("last_name"));
                                        map.put("profile_img",obj.getString("profile_img"));
                                        map.put("user_joindate",obj.getString("user_joindate"));
                                        map.put("dept_seq",obj.getString("dept_seq"));
                                        map.put("position_num",obj.getString("position_num"));

                                       // Log.d("이미지",map.get("profile_img"));
                                        Intent intent = new Intent(LoginActivity.this, MypageActivity.class);
                                        intent.putExtra("info",map);

                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("오류","login 오류");
                            }
                        }
                ){
                    //request에 값을 넣어서 서버로 넘겨주는 부분.
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();

                        params.put("id",id);
                        params.put("pw",pw);
                        //서버로  id, pw 넘겨줘용

                        return params;
                    }
                };

                requestQueue.add(request);


            }
        });




    }
}