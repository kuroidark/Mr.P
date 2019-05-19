package io.github.ryuu.mrp.userinterface;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.github.ryuu.mrp.R;
import io.github.ryuu.mrp.authentication.LoginActivity;

public class Authentication extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     * 启动页，设置等待时间显示LOGO，然后打开指纹验证界面
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Integer time = 1000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Authentication.this, LoginActivity.class));
                Authentication.this.finish();
            }
        }, time);
    }
}
