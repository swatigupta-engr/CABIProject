package com.app.cabi;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.cabi.model.Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

import static android.content.Context.MODE_PRIVATE;


public class Utilities {
    private static final String MY_PREFS_NAME = "Data_Pref";


    public static void saveListOfData(Context context, ArrayList<Model> dataArrayList) {

        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        Gson gson = new Gson();
        String json = gson.toJson(dataArrayList);

        //editor = editor.edit();
        editor.remove(Constants.KEY_DOWNLOADED_DATA).commit();
        editor.putString(Constants.KEY_DOWNLOADED_DATA, json);
        editor.commit();
    }

    public static ArrayList<Model> getListOfData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String response = prefs.getString(Constants.KEY_DOWNLOADED_DATA, "");
        ArrayList<Model> medicationArrayList = gson.fromJson(response, new TypeToken<List<Model>>() {
        }.getType());
        return medicationArrayList;
    }

    public static void saveRunVersion(Context context, String val) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editor.putString(Constants.Key_RUN, val);
        editor.commit();
    }

    public static String getRunVersion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String data = prefs.getString(Constants.Key_RUN, "1");

        return data;
    }

}
