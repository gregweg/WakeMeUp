package com.roninapps.wakemeup;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by gregwegman on 6/23/15.
 */
public class WakeMeUp extends Application {
    public static DbHelper dbHelper;
    public static SQLiteDatabase db;
    public static SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public static String getRingtone() {
        return sp.getString(WakeMeUp.RINGTONE_PREF, DEFAULT_NOTIFICATION_URI.toString());
    }
}
