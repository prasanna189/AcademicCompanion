package com.example.adavi.academiccompanion;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AKHIL on 4/4/2017.
 */



public class IntroActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public IntroActivity(Context context)
    {
        this.context=context;
        pref = context.getSharedPreferences("first",0);
        editor = pref.edit();
    }

    public void setFirst(Boolean isfirst)
    {
        editor.putBoolean("check",isfirst);
        editor.commit();
    }

    public boolean Check()
    {
        return pref.getBoolean("check",true);
    }
}