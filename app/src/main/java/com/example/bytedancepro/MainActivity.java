package com.example.bytedancepro;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnWoodenFish,btnWorkTimer,btnLeaveCountdown;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String username=getIntent().getStringExtra("username");
        tvWelcome=findViewById(R.id.tv_welocme);
        btnWoodenFish=findViewById(R.id.btn_wooden_fish);
        btnLeaveCountdown=findViewById(R.id.btn_leave_countdown);
        btnWorkTimer=findViewById(R.id.btn_work_time);

        tvWelcome.setText("欢迎"+username+"打工仔！");

        btnWoodenFish.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this,WoodenFishActivity.class));
        });
        btnWorkTimer.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this,WorkTimerActivity.class));
        });
        btnLeaveCountdown.setOnClickListener(v ->{
            Toast.makeText(MainActivity.this,"摸鱼倒计时功能开发中",Toast.LENGTH_SHORT).show();
        });

    }
}