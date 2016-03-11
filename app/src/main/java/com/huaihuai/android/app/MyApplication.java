package com.huaihuai.android.app;

import android.app.Application;

/**
 * Created by wangduo on 16/3/11.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    private boolean isBackStage; // 在后台运行
    private long enterBackStageTime; // 进入后台的时间

    public synchronized static MyApplication getInstance() {
        if (null == myApplication) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    public boolean isBackStage() {
        return isBackStage;
    }

    public void setIsBackStage(boolean isBackStage) {
        // 程序已在后台运行则不触发
        if (!isBackStage() && isBackStage) {
            enterBackStageTime = System.currentTimeMillis();
        }
        this.isBackStage = isBackStage;
    }

    public long getEnterBackstageTime() {
        return enterBackStageTime;
    }


}
