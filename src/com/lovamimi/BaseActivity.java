package com.lovamimi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.facebook.SessionState;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class BaseActivity extends Activity {

    protected MixpanelAPI mixpanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mixpanel = MixpanelAPI.getInstance(this, "e516b8643dc2d4d9b1779d243b7db7e5");
    }

    protected void track(String eventName) {
        mixpanel.track("Android:" + eventName, null);
    }

    protected void error(String s) {
        Log.e(this.getClass().toString(), s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mixpanel.flush();
    }

    public String getSessionId() {
        LovamimiApplication app = (LovamimiApplication) getApplication();
        return app.getSessionId();
    }

    public void setSessionId(String sessionId) {
        LovamimiApplication app = (LovamimiApplication) getApplication();
        app.setSessionId(sessionId);
    }

    private void lovamimiLogin(final Context context,
                                 final Class nextActivityClass,
                                 String fbSessionId) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String sessionId) {
                super.onPostExecute(sessionId);
                setSessionId(sessionId);
                Intent intent = new Intent(context, nextActivityClass);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... strings) {
                String fbSessionId = strings[0];
                return com.lovamimi.Session.login(fbSessionId);
            }
        }.execute(fbSessionId);
    }

    protected void tryLogin(final Context context, final Class nextActivityClass) {
        // Facebook login
        com.facebook.Session.openActiveSession(this, true, new com.facebook.Session.StatusCallback() {
            @Override
            public void call(com.facebook.Session session, SessionState state, Exception exception) {
                lovamimiLogin(context, nextActivityClass, session.getAccessToken());
            }
        });
    }

}
