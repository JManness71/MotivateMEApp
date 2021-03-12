package com.example.motivateme;

public class PreferenceHelper {
    public static String getValue(String key){
        return MainActivity.preferences.getString(key, "");
    }

    public static void setValue(String key, String value){
        MainActivity.editor.putString(key, value);
        MainActivity.editor.apply();
    }
}
