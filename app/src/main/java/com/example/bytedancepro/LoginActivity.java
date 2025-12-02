package com.example.bytedancepro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private TextView tvToRegister;
    private EditText etUsername, etLoginPassword;
    private Button btnLogin;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername=findViewById(R.id.ed_login_username);
        etLoginPassword=findViewById(R.id.ed_login_password);
        btnLogin=findViewById(R.id.btn_login);
        tvToRegister=findViewById(R.id.tv_to_register);
        sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(v ->{
            String username = etUsername.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"用户名/密码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }

            String savedPwd =sp.getString(username,"");
            if(savedPwd.equals(password)){
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }
        });
        tvToRegister.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, RegisterAcitivity.class));
            finish();
        });
    }
}