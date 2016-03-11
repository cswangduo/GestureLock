package com.huaihuai.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.huaihuai.android.R;
import com.huaihuai.android.data.Pref;

public class MainActivity extends BaseActivity {

    private TextView btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_set = (TextView) findViewById(R.id.btn_set);

        boolean isOpen = !TextUtils.isEmpty(Pref.getString(Pref.GESTUREPSW, getBaseContext(), ""));
        // app每次重新打开都需打开手势密码界面－如果有手势密码
        if (isOpen) {
            GestureLockVerifyActivity.startActivity(getBaseContext(), MainActivity.class);
        }
        MyApplication.getInstance().setIsBackStage(false);

        btn_set.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_set:
                    startActivity(new Intent(getBaseContext(), SettingActivity.class));
                    break;
                default:
                    break;
            }
        }
    };


}
