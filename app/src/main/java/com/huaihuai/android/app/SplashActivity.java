package com.huaihuai.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by wangduo on 16/3/11.
 */
public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 1000l);

    }
}
