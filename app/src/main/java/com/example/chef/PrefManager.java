package com.example.chef;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;
    String Filename = "login";
    String data = "b";

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Filename, mode);
        editor = sharedPreferences.edit();
    }
    public void firstTime(){
        if (!this.login()){
            Log.d("gilog","First Time : "+data);
            Intent intent = new Intent(context,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    private boolean login() {
        return sharedPreferences.getBoolean(data,false);
    }

    public void secondTime(){
        editor.putBoolean(data,true);
        Log.d("gilog","Second Time : "+data);
        editor.commit();
    }
}