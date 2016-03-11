package com.huaihuai.android.app;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.huaihuai.android.R;
import com.huaihuai.android.data.Pref;

public class SettingActivity extends BaseActivity {

    private ImageView iv_switch;

    private boolean isOpen; // 手势密码开关是否打开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        iv_switch = (ImageView) findViewById(R.id.iv_switch);
        iv_switch.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // app每次重新打开都需打开手势密码界面－如果有手势密码
        isOpen = !TextUtils.isEmpty(Pref.getString(Pref.GESTUREPSW, getBaseContext(), ""));
        iv_switch.setImageResource(isOpen ? R.mipmap.gesture_swith_open : R.mipmap.gesture_switch_close);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_switch:
                    if (isOpen) {
                        iv_switch.setImageResource(R.mipmap.gesture_switch_close);
                        Pref.saveString(Pref.GESTUREPSW, "", getBaseContext());
                        isOpen = false;
                    } else {
                        iv_switch.setImageResource(R.mipmap.gesture_swith_open);
                        GestureLockSetActivity.startActivity(getBaseContext(), null);
                    }
                    break;
                default:
                    break;
            }
        }
    };

}
