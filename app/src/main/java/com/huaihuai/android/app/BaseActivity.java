package com.huaihuai.android.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.huaihuai.android.data.Pref;

/**
 * Created by wangduo on 16/3/11.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册监听home键被按下广播
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        //注册监听锁屏广播
        registerReceiver(mScreenOffReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mHomeKeyEventReceiver);
        unregisterReceiver(mScreenOffReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MyApplication.getInstance().isBackStage()) {
            MyApplication.getInstance().setIsBackStage(false);
            /*if (UserUtil.hasLogin(getBaseContext()) &&
                    Pref.getBoolean(Pref.GESTURESTATE, getBaseContext(), false) &&
                    !TextUtils.isEmpty(Pref.getString(Pref.GESTUREPSW, getBaseContext(), null))) {*/
            // 此处判断条件: 1.已登陆 2.有密码 3.密码不为空
            if (!TextUtils.isEmpty(Pref.getString(Pref.GESTUREPSW, getBaseContext(), null))) {
                long curTime = System.currentTimeMillis();
                if (curTime - MyApplication.getInstance().getEnterBackstageTime() > 20 * 1000) {
                    GestureLockVerifyActivity.startActivity(getBaseContext(), null);
                }
            }
        }
    }

    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {

        final String SYSTEM_REASON = "reason";
        final String SYSTEM_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    // 设置为在后台运行的标志
                    // 表示按了home键,程序到了后台
                    MyApplication.getInstance().setIsBackStage(true);
                }
            }
        }
    };

    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 设置为在后台运行的标志
            // 表示按了home键,程序到了后台
            MyApplication.getInstance().setIsBackStage(true);
        }
    };


}
