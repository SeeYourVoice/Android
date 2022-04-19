package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopUp_Deptlist extends Dialog {
    Spinner spinner;
    TextView edtDeptlist;
    Button btnChoice;

    RequestQueue requestQueue;
    StringRequest request;

    AppCompatActivity activity = null;

    String text;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public PopUp_Deptlist(@NonNull Context context) {
        super(context);
        setContentView(R.layout.deptlist);

        edtDeptlist = findViewById(R.id.edtDeptlist);
        spinner = findViewById(R.id.dep_spinner);
        btnChoice =findViewById(R.id.btnChoice);


        ArrayList<String> items=new ArrayList<>();

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }


        String serverUrl="http://121.147.52.219:8081/Moim_server/DeptListService";

        request=new StringRequest(
                Request.Method.POST, // 방식
                serverUrl, // 받아올 서버url
                new Response.Listener<String>() { // 응답을 받아왔을 때
                    @Override
                    public void onResponse(String response) {
                        if(!(response ==null)){
                            try {
                                JSONArray objArray= new JSONArray(response);
                                for(int i=0; i<objArray.length(); i++ ){
                                    JSONObject obj=(JSONObject) objArray.get(i);
                                    items.add(obj.getString("dept_list"));
                                }

                                ArrayAdapter adapter= new ArrayAdapter( getContext(), android.R.layout.simple_spinner_dropdown_item,items);
                                spinner.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() { // 에러발생
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("오류", "부서선택 오류");
                    }
                }

        );

        requestQueue.add(request);

        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //선택된 값
                text = spinner.getSelectedItem().toString();
                SharedPreferences sp= context.getSharedPreferences("info",0);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("dept_name",text);
                editor.commit();
                Log.d("선택된 부서",text);
            }
        });


        dismiss();
    }
}



