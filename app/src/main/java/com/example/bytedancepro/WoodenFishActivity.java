package com.example.bytedancepro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;


public class WoodenFishActivity extends AppCompatActivity {

    private ImageView ivWoodenFish;
    private TextView tvFishCount;
    private Button btnSwitchFish,btnToWorkTime,btnBackMain;
    private SharedPreferences sp;
    private MediaPlayer mediaPlayer;

    private int[] fishRes={R.drawable.woodenfish,R.drawable.woodenfish2,R.drawable.woodenfish3,R.drawable.woodenfish4};
    private int currentFishIndex=0;
    private int todayCount=0;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wooden_fish);

        ivWoodenFish=findViewById(R.id.iv_wooden_fish);
        tvFishCount=findViewById(R.id.tv_fish_count);
        btnSwitchFish=findViewById(R.id.btn_switch_fish);
        btnToWorkTime=findViewById(R.id.btn_work_timer);
        btnBackMain=findViewById(R.id.btn_back_main);

        sp=getSharedPreferences("woodenFish", Context.MODE_PRIVATE);
        todayDate=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        todayCount=sp.getInt(todayDate,0);
        tvFishCount.setText("今日敲击："+todayCount+"次");

        mediaPlayer=MediaPlayer.create(this,R.raw.fish);

        ivWoodenFish.setOnClickListener(v ->{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.seekTo(0);
            }
            mediaPlayer.start();

            todayCount++;
            tvFishCount.setText("今日敲击"+todayCount+"次");
            SharedPreferences.Editor editor=sp.edit();
            editor.putInt(todayDate,todayCount);
            editor.apply();
        });
        btnSwitchFish.setOnClickListener(v->{
            currentFishIndex=(currentFishIndex+1)%fishRes.length;
            ivWoodenFish.setImageResource(fishRes[currentFishIndex]);
            Toast.makeText(this,"切换木鱼",Toast.LENGTH_SHORT).show();
        });
        btnToWorkTime.setOnClickListener(v ->
            startActivity(new Intent(WoodenFishActivity.this,WorkTimerActivity.class)));;

        btnBackMain.setOnClickListener(v -> finish());



    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (mediaPlayer !=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

}