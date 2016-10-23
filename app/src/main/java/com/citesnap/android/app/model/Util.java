package com.citesnap.android.app.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Kevin on 10/19/2016.
 */

public class Util {
    public static String USE_FLASH = "useFlash";
    public static String AUTO_FOCUS = "autoFocus";

    public static String NO_VALUE_STORES = "noValueStored";

    private SharedPreferences preferences;

    public Util(Context c) {
        preferences = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public String getStringProperty(String key) {
        return preferences.getString(key, NO_VALUE_STORES);
    }
    public boolean getBooleanProperty(String key) {
        return preferences.getBoolean(key, false);
    }
    public int getIntegerProperty(String key) {
        return preferences.getInt(key, 0);
    }
    public void setProperty(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public void setProperty(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void restoreDefault() {
        setProperty(USE_FLASH, true);
        setProperty(AUTO_FOCUS, true);
    }
}
