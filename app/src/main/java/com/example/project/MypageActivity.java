package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;




public class MypageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnHome,btnProfile,btnPw,btnDept,btnPosition,btnDelEmail;
    TextView tvName,tvPos,tvDept,tvMypageEmail;
    CircleImageView circle_Page;

    PopUp_mypage  PopUp_mypage;

    RequestQueue requestQueue;
    StringRequest request;

    String first_name,last_name,id,pw,profile_img,position_num,position_name,dept_name;

    String org_fname,org_lname,org_pos,org_posnum;

    SharedPreferences sp;

    private final int GET_GALLERY_IMAGE = 200;

    File file;

    // ?????????
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    // ?????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        // ?????????
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2 = (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        // ?????????




        tvName=findViewById(R.id.tvPos);
        tvPos=findViewById(R.id.tvName);
        tvDept=findViewById(R.id.tvDept);
        tvMypageEmail=findViewById(R.id.tvMypageEmail);
        circle_Page=findViewById(R.id.circle_Page);

        btnHome = findViewById(R.id.btnHome);

        btnProfile=findViewById(R.id.btnProfile);
        btnPw=findViewById(R.id.btnMypagePw);
        btnDept=findViewById(R.id.btnMypageDept);
        btnPosition=findViewById(R.id.btnMypagePosition);
        btnDelEmail = findViewById(R.id.btnDelEmail);



        //??????????????? ????????? ??????????????????.


        sp= getSharedPreferences("info",0);
        first_name=sp.getString("first_name","none");
        last_name=sp.getString("last_name","none");
        id=sp.getString("user_email","none");
        pw=sp.getString("user_password","none");
        profile_img=sp.getString("profile_img","none");
        position_num=sp.getString("position_num","none");
        position_name=sp.getString("position_name","none");
        dept_name=sp.getString("dept","none");

        //??????
        org_fname=first_name;
        org_lname=last_name;
        org_pos=position_name;
        org_posnum=position_num;


        //????????? ??????
        tvName.setText(first_name+last_name);
        tvPos.setText(position_name);
        tvDept.setText(dept_name);
        tvMypageEmail.setText(id);

        //??????????????? ?????? ????????? ????????? ????????????
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);



       // String url="https://s3.us-east-2.amazonaws.com/momoyami/moim/";
        //https://s3.us-east-2.amazonaws.com/momoyami/moim/moim_Profile
        Glide.with(this).load(profile_img).error(R.drawable.default_img).into(circle_Page);


        //????????? ??????   ====================
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        //????????? ?????? ??????   ====================
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUp_mypage=new PopUp_mypage(MypageActivity.this);
                PopUp_mypage.setActivity( MypageActivity.this );
                PopUp_mypage.show();

                PopUp_mypage.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d("pop up ","?????????");
                        sp= getSharedPreferences("info",0);
                        first_name=sp.getString("first_name","none");
                        last_name=sp.getString("last_name","none");

                        Log.d("??????_mypage",first_name);
                        Log.d("??????_mypage",last_name);

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
                                        if(response.equals("??????")){
                                            Toast.makeText(MypageActivity.this,"?????? ??????",Toast.LENGTH_SHORT).show();
                                            tvName.setText(first_name+last_name);

                                        }else{
                                            Toast.makeText(MypageActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
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
                                        Log.d("??????", "??????");
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

                                //???????????? ?????? ??????
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


        //???????????? ??????   ====================
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
                                        if(response.equals("??????")){
                                            Toast.makeText(MypageActivity.this,"?????? ??????",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(MypageActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("??????", "???????????? ????????????");
                                    }
                        }){

                            @Nullable
                            @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> PwParams= new HashMap<String, String>();

                            //???????????? ?????? ??????
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

        //????????????    ====================
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

                                       if(response.equals("??????")){
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
                           //?????? ?????? ??????
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


        // ????????????    ====================
        btnDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        //????????????   ====================
        btnDelEmail.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                new AlertDialog.Builder(MypageActivity.this) // TestActivity ???????????? ?????? Activity??? ?????? ??????.
                        .setMessage("????????? ?????????????????????????")     // ?????? ?????? (?????? ??????)
                        .setPositiveButton("?????????", new DialogInterface.OnClickListener() {      // ??????1 (?????? ??????)
                            public void onClick(DialogInterface dialog, int which){
                                // ??????????????? ?????? ??????
                                Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show(); // ????????? ??????



                            }
                        })
                        .setNegativeButton("???", new DialogInterface.OnClickListener() {     // ??????2 (?????? ??????)
                            public void onClick(DialogInterface dialog, int which){
                                // ??????????????? ?????? ??????
                                Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show(); // ????????? ??????



                            }
                        })
                        .show();

            }
        });

        //?????? ?????? ??????   ====================
        circle_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(MypageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(MypageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MypageActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0x0000001);
                }



                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);




            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            circle_Page.setImageURI(selectedImageUri);

            String path = getPathFromUri(selectedImageUri);

            file = new File(path);

            uploadWithTransferUtilty(id+"_Profile", file);
        }

    }

    @SuppressLint("Range")
    public String getPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();
        return path;
    }

    public void uploadWithTransferUtilty(String fileName, File file) {

        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIASSS4QVJUPHIBRAOF", "yjOkST71e9ZXfla5iIpJMkfHoAcwz1GaJxHwbL95");    // IAM ???????????? ?????? ??? ??????
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.US_EAST_2));

        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(getApplicationContext()).build();
        TransferNetworkLossHandler.getInstance(getApplicationContext());

        TransferObserver uploadObserver = transferUtility.upload("momoyami/moim", fileName, file);    // (bucket api, file??????, file??????)

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                }
            }

            @Override
            public void onProgressChanged(int id, long current, long total) {
                int done = (int) (((double) current / total) * 100.0);
                Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX:" + ex.toString());
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                anim();
                //Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                anim();
                //Intent intent = new Intent(MainActivity.this, ?????????.class);
                // startActivity(intent);
                //Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                anim();
                Intent intent2 = new Intent(MypageActivity.this, CalendarActivity.class);
                startActivity(intent2);
                //Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
}