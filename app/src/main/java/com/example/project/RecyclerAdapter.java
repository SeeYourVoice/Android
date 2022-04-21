package com.example.project;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

    //Volley통신관련객체
    RequestQueue requestQueue ;
    StringRequest request;

    String rec_name;

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));

    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }



    void addItem(ArrayList<Data> list) {
        // 외부에서 item을 추가시킬 함수입니다.

            if (list != null) {
                listData = list;
                notifyDataSetChanged();
            }
    }

    void clear(){
        int size = listData.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listData.remove(0);
            }

            Log.d("데이타크기>>", listData.size()+"");
            notifyItemRangeRemoved(0, size);
        }
    }



    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView1;
        private TextView textView2;

        //>>>>>>>>>>>>>수정
        ItemViewHolder(View itemView) {
            super(itemView);


            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.tvmemo);

           rec_name=textView1.getText().toString(); //이름만 따로 갖고옴.

            itemView.setOnClickListener(this);
        }



        void onBind(Data data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getDate());

        }



        @Override
        public void onClick(View view) {


            //RequestQueue객체 초기화
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(view.getContext());
            }


            String serverURL="http://222.102.104.208:8082/Moim_server/Moim_RecordInfoService";

            request= new StringRequest(
                    Request.Method.GET,
                    serverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //인텐트 넘기고고
                       }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }


            ){

                //request에 값을 넣어서 서버로 넘겨주는 부분.
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();

                    params.put("rec_name",rec_name);
                    return params;
                }
            };

            requestQueue.add(request);
        }


    }
}
