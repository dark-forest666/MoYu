package com.example.bytedancepro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkTimerActivity extends AppCompatActivity {

    private TextView tvCurrentTime,tvTotalTime;
    private Button btnStart,btnPause,btnReset,btnBackMain;
    private SharedPreferences sp;

    private Handler handler=new Handler(Looper.getMainLooper());
    private boolean isTiming = false;
    private long currentMills=0;
    private long totalTodayMills=0;
    private String todayDate;

    private void updateCurrentTimeText(){
        long hours = currentMills/3600000;
        long minutes = (currentMills%36000000)/60000;
        long seconds = (currentMills%60000)/1000;
        tvCurrentTime.setText(String.format(Locale.CHINA,"%02d:%02d:%02d",hours,minutes,seconds));
    }
    private void updateTotalTimeText(){
        long hours = totalTodayMills/3600000;
        long minutes = (currentMills%36000000)/60000;
        long seconds = (currentMills%60000)/1000;
        tvTotalTime.setText(String.format(Locale.CHINA,"今日总专注时长：%02d:%02d:%02d",hours,minutes,seconds));
    }
    private void checkWorkTime(){
        long targetMills=3600000;
        if (totalTodayMills<targetMills){
            long lackMills=targetMills-totalTodayMills;
            long lackHours=lackMills/3600000;
            long lackMinute=(lackMills%3600000)/60000;
            Toast.makeText(this,
                    "卷不动？还缺"+lackHours+"小时"+lackMinute+"分钟才能下班",
                    Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"今日目标达成！可以摸鱼了~~",Toast.LENGTH_LONG).show();
        }
    }
    private Runnable timerRunnable=new Runnable() {
        @Override
        public void run() {
            currentMills+=1000;
            updateCurrentTimeText();
            handler.postDelayed(this,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_timer);


        tvCurrentTime=findViewById(R.id.tv_current_time);
        tvTotalTime=findViewById(R.id.tv_total_time);
        btnStart=findViewById(R.id.btn_start);
        btnPause=findViewById(R.id.btn_pause);
        btnReset=findViewById(R.id.btn_reset);
        btnBackMain=findViewById(R.id.btn_back_main);
        sp=getSharedPreferences("work_timer", Context.MODE_PRIVATE);
        todayDate=new SimpleDateFormat("yyyyMMdd",Locale.CHINA).format(new Date());
        totalTodayMills=sp.getLong(todayDate,0);
        updateTotalTimeText();

        btnStart.setOnClickListener(v ->{
            if (!isTiming){
                isTiming=true;
                handler.post(timerRunnable);
                Toast.makeText(this,"卷王模式开启！",Toast.LENGTH_SHORT);
            }
        });
        btnPause.setOnClickListener(v->{
            if (isTiming){
                isTiming=false;
                handler.removeCallbacks(timerRunnable);
                totalTodayMills+=currentMills;
                SharedPreferences.Editor editor=sp.edit();
                editor.putLong("todayDate",totalTodayMills);
                editor.apply();
                updateTotalTimeText();
                checkWorkTime();
                Toast.makeText(this,"摸鱼时间到？",Toast.LENGTH_SHORT);
            }
        });
        btnReset.setOnClickListener(v ->{
            isTiming=false;
            handler.removeCallbacks(timerRunnable);
            currentMills=0;
            updateCurrentTimeText();
            Toast.makeText(this,"重置成功",Toast.LENGTH_SHORT).show();
        });

        btnBackMain.setOnClickListener(v ->finish());
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(timerRunnable);
    }
}