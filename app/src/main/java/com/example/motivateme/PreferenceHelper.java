package com.example.motivateme;

import android.content.SharedPreferences;

public class PreferenceHelper {
    public static String getValue(SharedPreferences p, String key){
        return p.getString(key, "");
    }

    public static void setValue(SharedPreferences p, String key, String value){
        SharedPreferences.Editor editor = p.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int numPreferences(SharedPreferences p){
        return p.getAll().size();
    }

    public static void clearData(SharedPreferences p){
        SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }

    public static void removeItem(SharedPreferences p, String key){
        SharedPreferences.Editor editor = p.edit();
        editor.remove(key);
        editor.commit();
    }

    public static boolean isEntry(SharedPreferences p, String key){
        return p.contains(key);
    }
}
