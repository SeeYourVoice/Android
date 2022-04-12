package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class MypageActivity extends AppCompatActivity {

    ImageButton btnHome,btnProfile;
    TextView tvName;

    PopUp_mypage  PopUp_mypage;

    RequestQueue requestQueue;
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        btnHome = findViewById(R.id.btnHome);
        btnProfile=findViewById(R.id.btnProfile);
        tvName=findViewById(R.id.tvName);

        Intent MypageIntent= getIntent();

        HashMap<String, String> info =(HashMap<String, String>)MypageIntent.getSerializableExtra("info");
        String first_name=info.get("first_name");
        String last_name=info.get("last_name");
        String id=info.get("user_email");
        String pw=info.get("user_password");

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
                PopUp_mypage.show();

                //팝업창이 닫혔다면
                if(!PopUp_mypage.isShowing()){
                    if(requestQueue==null){
                        requestQueue = Volley.newRequestQueue(getApplicationContext());

                    }

                    String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_SearchService";

                    request= new StringRequest(
                            Request.Method.POST,
                            serverUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj= new JSONObject(response);

                                        tvName.setText( obj.getString("first_name")
                                                +obj.getString("last_name"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("오류", "조회 오류");
                                }
                            }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params= new HashMap<String, String>();

                            //계정정보 넣고 조회
                            params.put("id",id);
                            params.put("pw",pw);

                            return params;
                        }
                    };

                }


            }
        });

    }
}