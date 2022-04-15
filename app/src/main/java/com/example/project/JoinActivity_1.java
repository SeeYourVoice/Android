package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class JoinActivity_1 extends AppCompatActivity {

    TextView edtFname, edtLname;
    Button btnCon, btnLoginGo;

    HashMap<String,String> map;

    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_1);

        edtFname = findViewById(R.id.edtFname);
        edtLname = findViewById(R.id.edtLname);

        btnCon = findViewById(R.id.btnCon);
        btnLoginGo = findViewById(R.id.btnLoginGo);

        // 로그인 페이지(이미 회원가입을 했다면)
        btnLoginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity_1.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 회원가입_1
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String fname = edtFname.getText().toString();
                String lname = edtLname.getText().toString();

                // 첫번째 이름 칸 빈칸 x
                if(validate)
                {
                    return;
                }

                if(fname.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity_1.this );

                    Toast.makeText(JoinActivity_1.this, "성을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();

                    return;
                }
                // 마지막 이름 칸 빈칸 x
                if(validate)
                {
                    return;
                }
                
                if(lname.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity_1.this );

                    Toast.makeText(JoinActivity_1.this, "이름을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();

                    return;
                }
                
                //입력값 확인
                Log.d("입력", fname);
                Log.d("입력", lname);

                // 입력(키, 밸류)
                //map.put("first_name", fname);
                //map.put("last_name", lname);

                Intent intent = new Intent(JoinActivity_1.this, JoinActivity_2.class);
                //intent.putExtra("joininfo1",map);
                intent.putExtra("first_name",fname);
                intent.putExtra("last_name",lname);

                // 버튼을 누르면 intent수행
                // joinActivity_1위에 joinActivity_2 로드
                startActivity(intent);

            }
        });

    }
}
