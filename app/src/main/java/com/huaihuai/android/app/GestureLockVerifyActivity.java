package com.huaihuai.android.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.huaihuai.android.R;
import com.huaihuai.android.data.Config;
import com.huaihuai.android.data.Pref;
import com.huaihuai.android.view.GestureLockView;

/**
 * 手势锁验证
 * Created by wangduo on 15/12/8.
 */
public class GestureLockVerifyActivity extends BaseActivity {

    private TextView tv_tip;
    private TextView tv_forgetPsw;
    private GestureLockView lockView;
    private String gesturePsw; // 手势图案密码
    private int unlockCount; // 图案锁 剩余解锁验证次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock_verify);

        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_forgetPsw = (TextView) findViewById(R.id.tv_forgetPsw);
        lockView = (GestureLockView) findViewById(R.id.lockView);
        lockView.setOnPatterChangeListener(onPatterChangeListener);
        gesturePsw = Pref.getString(Pref.GESTUREPSW, getBaseContext(), null);
        unlockCount = Pref.getInt(Pref.UNLOCKCOUNT, getBaseContext(), 0);
        tv_forgetPsw.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logout();
        }
    };

    // 手势密码错误 文字抖动
    private void startShake() {
        TranslateAnimation animation = new TranslateAnimation(0, -5, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(50);
        animation.setRepeatCount(4);
        animation.setRepeatMode(Animation.REVERSE);
        tv_tip.startAnimation(animation);
    }

    private GestureLockView.OnPatterChangeListener onPatterChangeListener = new GestureLockView.OnPatterChangeListener() {
        @Override
        public void onPatterChanged(String password) {
            if (password.equals(gesturePsw)) {
                tv_tip.setText("验证成功");
                Pref.saveInt(Pref.UNLOCKCOUNT, Config.MAXCOUNT, getBaseContext());
                if (null != desClass) {
                    startActivity(new Intent(getBaseContext(), desClass));
                }
                finish();
            } else {
                lockView.showErrorState();
                unlockCount--;
                if (unlockCount <= 0) {
                    logout();
                    return;
                }
                Pref.saveInt(Pref.UNLOCKCOUNT, unlockCount, getBaseContext());
                StringBuilder sb = new StringBuilder();
                sb.append("密码错误，还可以再输入").append(unlockCount).append("次");
                tv_tip.setText(sb.toString());
                tv_tip.setTextColor(getResources().getColor(android.R.color.black));
                startShake();
            }
        }

        @Override
        public void onPatterStart() {
        }
    };

    // 验证失败 退出
    private void logout() {
        Toast.makeText(getBaseContext(), "已退出验证", Toast.LENGTH_LONG).show();
        Pref.saveString(Pref.GESTUREPSW, "", getBaseContext());
        finish();
    }

    @Override
    public void onBackPressed() {
    }

    private static Class<?> desClass; // 手势密码验证成功后跳转至页面

    public static void startActivity(Context context, Class cl) {
        desClass = cl;
        Intent intent = new Intent(context, GestureLockVerifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
