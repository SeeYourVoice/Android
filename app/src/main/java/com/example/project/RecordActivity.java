package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecordActivity extends AppCompatActivity {

    // 웹 서버에 오려둔 'MP3파일' 사용
    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaPlayer player;
    int position = 0 ; //현재 재생한 위치
    int count=0; //이벤트 횟수 감지용

    MediaRecorder recorder = null; //녹음 하려면 필요함
    String filename = null;
    Chronometer chrono;
    private long pauseOffset;
    private boolean running;
    ImageView confer, end, imgMeet;

    private OkHttpClient okHttpClient;  // 서버 연결을 위해
    private static String fileName = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean permissionToRecordAccepted = false;
    private static final String LOG_TAG = "AudioRecordTest";
    boolean mStartRecording = true;




    // 타이머
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddhhmmss_");

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    // 타이머


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        imgMeet = findViewById(R.id.imgMeet);

        okHttpClient = new OkHttpClient();

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);


        // 저장경로
        fileName = getExternalCacheDir().getAbsolutePath();  //파일이 저장되는 경로
        fileName +=  "/record.wav";                  //파일명
        Log.d("Test", fileName);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        chrono = findViewById(R.id.chrono);
        chrono.setFormat("%s");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mStartRecording){ //재생중이 아닐때
                    // 팝업 띄우기 -- 회의제목입력
                    Popup_Title popup_title = new Popup_Title(RecordActivity.this);
                    popup_title.setActivity(RecordActivity.this);
                    popup_title.show();

                    popup_title.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            recordAudio();

                            button.setImageResource(R.drawable.end);
                            chrono.setBase((SystemClock.elapsedRealtime() - pauseOffset));
                            chrono.start();
                            imgMeet.setImageResource(R.drawable.con_end);

                        }
                    });
                }
                else{ //재생중인 상태에서 클릭이벤트 발생


                    button.setImageResource(R.drawable.start);
                    chrono.setBase(SystemClock.elapsedRealtime());
                    pauseOffset = 0;
                    chrono.stop();
                    imgMeet.setImageResource(R.drawable.con_start);
                    stopRecording();
                }
                //=========================================

                mStartRecording = !mStartRecording;


            }
        });

    }

    // 권한허가
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

//    private void onRecord(boolean start) {
//        if (start) {
//            recordAudio();
//        } else {
//            stopRecording();
//        }
//    }

    // 녹음 시작
    public void recordAudio() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    // 녹음 정지
    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        connectServer("http://e2.project-jupyter.ddns.net:8862/sendFile" , new File(fileName));
    }

    //★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
    /////////////////////////////// 서버 커넥션 및 데이터 전송 ////////////////////////////////
    void connectServer(String url , File file ){

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files" , file.getName() , RequestBody.create(MultipartBody.FORM, file))
                .build();

        Request request = new Request.Builder()
                .url(url) // Server URL 은 본인 IP를 입력 .post(requestBody) .build();
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //서버 전송에 실패할 경우
                e.printStackTrace();
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                //서버 전송에 성공한경우 , 플라스크에서 return 값으로 넘기는 값을 출력한다.
                Log.d("TEST : ", response.body().string());
            }
        });
    }
}