package com.example.adavi.academiccompanion;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pk on 4/5/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences profile_pic;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "adavi";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_PROFILE_PIC_SET = "IsProfilePicSet";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor = pref.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setProfilePic(boolean isPicSet){
        editor = profile_pic.edit();
        editor.putBoolean(IS_PROFILE_PIC_SET, isPicSet);
        editor.commit();
    }

    public boolean isProfilePicSet() {
        return pref.getBoolean(IS_PROFILE_PIC_SET, false);
    }

}
