package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class JoinActivity_2 extends AppCompatActivity {

    TextView edtNewEmail, edtNewPw, edtPwCheck;
    Button btnCon, btnLoginGo;

    RequestQueue requestQueue;

    HashMap<String, String> map;

    private Button validateButton;;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_2);

        edtNewEmail = findViewById(R.id.edtNewEmail);
        edtNewPw = findViewById(R.id.edtNewPw);
        edtPwCheck = findViewById(R.id.edtPwCheck);

        btnCon = findViewById(R.id.btnCon);
        btnLoginGo = findViewById(R.id.btnLoginGo);

        // 로그인 페이지(이미 회원가입을 했다면)
        btnLoginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity_2.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 회원가입_2
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= getIntent();
                HashMap<String, String> joininfo2 = (HashMap<String, String>)intent.getSerializableExtra("joininfo2");

                if(requestQueue == null){
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                }

                String newemail = edtNewEmail.getText().toString();
                String newpw = edtNewPw.getText().toString();
                String pwcheck = edtPwCheck.getText().toString();

                // 이메일 칸 빈칸 x
                if(validate)
                {
                    return;
                }
                if(newemail.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity_2.this );

                    Toast.makeText(JoinActivity_2.this, "이메일을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // 이메일 중복확인
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity_2.this );
                            if(success){
                                Toast.makeText(JoinActivity_2.this, "사용할 수 있는 아이디입니다.",
                                        Toast.LENGTH_SHORT).show();

                                edtNewEmail.setEnabled(false);
                                validate=true;
                                validateButton.setText("확인");
                            }
                            else{
                                Toast.makeText(JoinActivity_2.this, "사용할 수 없는 아이디입니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 비밀번호 확인
                if(!edtNewPw.getText().toString().equals(edtPwCheck.getText().toString())){
                    Toast.makeText(JoinActivity_2.this, "비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                    edtNewPw.setText("");
                    edtPwCheck.setText("");
                    edtNewPw.requestFocus();
                    return;

                }

                //입력값 확인
                Log.d("입력", newemail);
                Log.d("입력", newpw);
                Log.d("입력", pwcheck);

                // 입력
                //map.put("user_email", newemail);
                //map.put("user_password", newpw);
                //map.put("user_password_check", pwcheck);


                intent=getIntent();
                String fname=intent.getStringExtra("first_name");
                String lname= intent.getStringExtra("last_name");


                intent = new Intent(JoinActivity_2.this,JoinActivity_3.class);
                //intent.putExtra("joininfo2",map);

                intent.putExtra("first_name",fname);
                intent.putExtra("last_name",lname);
                intent.putExtra("user_email",newemail);
                intent.putExtra("user_password",newpw);
                //intent.putExtra("user_password_check",pwcheck);

                startActivity(intent);


            }
        });

    }
}