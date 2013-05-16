package com.lovamimi;

import android.app.Application;
import android.content.SharedPreferences;

public class LovamimiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Config.forceLogin) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
        }
    }

    private String sessionId = null;
    private static final String PREFS_NAME = "lovamiomi_settings";

    public String getSessionId() {
        if (sessionId != null) {
            return sessionId;
        }
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("sessionId", null);
    }

    public void setSessionId(String sessionId) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("sessionId", sessionId);
        editor.commit();
        this.sessionId = sessionId;
    }

    public String getDeviceToken() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("deviceToken", null);
    }

    public void setDeviceToken(String deviceToken) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("deviceToken", deviceToken);
        editor.commit();
    }
}
