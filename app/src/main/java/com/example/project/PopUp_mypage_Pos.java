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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopUp_mypage_Pos extends Dialog {
    Spinner spinner;
    Button btnSavePos;

    RequestQueue requestQueue;
    StringRequest request;

    AppCompatActivity activity = null;

    String text;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public PopUp_mypage_Pos(@NonNull Context context) {
        super(context);
        setContentView(R.layout.my_page_position);

        spinner=findViewById(R.id.pos_spinner);
        btnSavePos=findViewById(R.id.btnSavePos);


        ArrayList<String> items=new ArrayList<>();

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }


        String serverUrl="http://121.147.52.219:8081/Moim_server/Moim_LoadPos";

        request=new StringRequest(
                Request.Method.POST,
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!(response ==null)){
                            try {
                                JSONArray objArray= new JSONArray(response);
                                for(int i=0; i<objArray.length(); i++ ){
                                    JSONObject obj=(JSONObject) objArray.get(i);
                                    items.add(obj.getString("pos"));
                                }

                                ArrayAdapter adapter= new ArrayAdapter( getContext(), android.R.layout.simple_spinner_dropdown_item,items);
                                spinner.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("오류", "직급 오류");
                    }
                }

        );

        requestQueue.add(request);

        btnSavePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //선택된 값
                text = spinner.getSelectedItem().toString();
                SharedPreferences sp= context.getSharedPreferences("info",0);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("position_name",text);
                editor.commit();
                Log.d("변경 직급",text);
            }
        });


    dismiss();
    }

}
