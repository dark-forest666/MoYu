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

public class RegisterAcitivity extends AppCompatActivity {

    private EditText etUsername, etPassword,etConfirmPassword;
    private Button btnRegister;
    private TextView tvToRegister;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivity);

        etPassword=findViewById(R.id.et_password);
        etUsername=findViewById(R.id.et_username);
        etConfirmPassword=findViewById(R.id.et_confirm_password);
        btnRegister=findViewById(R.id.btn_register);
        tvToRegister=findViewById(R.id.tv_to_login);
        sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);

        btnRegister.setOnClickListener(v ->{
            String username=etUsername.getText().toString().trim();
            String password=etPassword.getText().toString().trim();
            String confirmPassword=etConfirmPassword.getText().toString().trim();

            //1.空值校验
            if(username.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
                Toast.makeText(this, "用户名/密码不能为空", Toast.LENGTH_LONG).show();
                return;
            }
            //2.密码一致性校验
            if(!password.equals(confirmPassword)){
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                etConfirmPassword.setText("");
                return;
            }
            //3.密码过短
            if(password.length()<6){
                Toast.makeText(this, "密码至少需要6位字符", Toast.LENGTH_LONG).show();
                return;
            }
            //4.用户名存在
            if(sp.contains(username)){
                Toast.makeText(this, "用户名已存在", Toast.LENGTH_LONG).show();
                return;
            }

            //5.存储信息
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(username,password);
            editor.apply();

            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterAcitivity.this,LoginActivity.class));
            finish();

        });
        tvToRegister.setOnClickListener(v ->{
            startActivity(new Intent(RegisterAcitivity.this,LoginActivity.class));
            finish();
        });

    }
}