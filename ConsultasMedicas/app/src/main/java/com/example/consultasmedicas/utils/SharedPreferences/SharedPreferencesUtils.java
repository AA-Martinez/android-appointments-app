package com.example.consultasmedicas.utils.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

public class SharedPreferencesUtils {

    public static void SaveStringDataToSharedPreferences(String dataKey, String data, View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(dataKey,data);
        Log.e("SharedPreferencesNew", "Guardado con exito");
        editor.apply();
    }

    public static void SaveIntDataToSharedPreferences(String datakey, int data, View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(datakey, data);
        Log.e("SharedPreferencesNew", "Guardado con exito");
        editor.apply();
    }

    public static String RetrieveStringDataFromSharedPreferences(String token, View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return sharedPreferences.getString(token, "");
    }

    public static int RetrieveIntDataFromSharedPreferences(String token, View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(token, 0);
    }


}
