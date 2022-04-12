package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PopUp_mypage extends Dialog {

    EditText edtMypageFname,edtMypageLname;
    Button btnSave;

    RequestQueue requestQueue;
    StringRequest request;



    public PopUp_mypage(@NonNull Context context){
        super(context);
        setContentView(R.layout.mypage_info);

        edtMypageFname=findViewById(R.id.edtMypageFname);
        edtMypageLname=findViewById(R.id.edtMypageLname);

        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname=edtMypageFname.getText().toString();
                String lname=edtMypageLname.getText().toString();

                if(requestQueue==null){
                    requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                }

                String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_InfoEditService";

                request=new StringRequest(
                        Request.Method.POST,
                        serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("응답", "이름 정보수정 완료");
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("오류","mypage pop up수정 오류");
                    }
                }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params= new HashMap<String, String>();

                        params.put("fname",fname);
                        params.put("lname",lname);


                        return params;
                    }
                };

                requestQueue.add(request);

               dismiss();
            }
        });


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }



}