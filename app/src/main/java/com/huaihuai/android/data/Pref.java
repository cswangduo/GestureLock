package com.huaihuai.android.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {

    final static String PREFS_NAME = "gestureLock_preferences";

    // 图案锁
    public final static String GESTUREPSW = "gesturePsw";
    // 图案锁 剩余解锁验证次数
    public final static String UNLOCKCOUNT = "unlockCount";

    private static SharedPreferences getSettings(final Context contex) {
        SharedPreferences mSharedPreferences = contex.getSharedPreferences(PREFS_NAME, 0);
        return mSharedPreferences;
    }

    public static String getString(final String key, final Context context, final String defaultValue) {
        return getSettings(context).getString(key, defaultValue);
    }

    public static boolean getBoolean(final String key, final Context context, final boolean defaultValue) {
        return getSettings(context).getBoolean(key, defaultValue);
    }

    public static int getInt(final String key, final Context context, final int defaultValue) {
        return getSettings(context).getInt(key, defaultValue);
    }

    public static long getLong(final String key, final Context context, final long defaultValue) {
        return getSettings(context).getLong(key, defaultValue);
    }

    public static boolean saveString(final String key, final String value, final Context context) {
        final SharedPreferences.Editor settingsEditor = getSettings(context).edit();
        settingsEditor.putString(key, value);
        return settingsEditor.commit();
    }

    public static boolean saveBoolean(final String key, final boolean value, final Context context) {
        final SharedPreferences.Editor settingsEditor = getSettings(context).edit();
        settingsEditor.putBoolean(key, value);
        return settingsEditor.commit();
    }

    public static boolean saveInt(final String key, final int value, final Context context) {
        final SharedPreferences.Editor settingsEditor = getSettings(context).edit();
        settingsEditor.putInt(key, value);
        return settingsEditor.commit();
    }

    public static boolean saveLong(final String key, final long value, final Context context) {
        final SharedPreferences.Editor settingsEditor = getSettings(context).edit();
        settingsEditor.putLong(key, value);
        return settingsEditor.commit();
    }
}
