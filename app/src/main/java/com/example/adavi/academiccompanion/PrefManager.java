package com.example.adavi.academiccompanion;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pk on 4/5/2017.
 */

public class PrefManager {
    // Shared preferences file name
    private static final String PREF_NAME = "adavi";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_PROFILE_PIC_SET = "IsProfilePicSet";
    SharedPreferences pref;
    SharedPreferences profile_pic;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setProfilePic(boolean isPicSet) {
        editor.putBoolean(IS_PROFILE_PIC_SET, isPicSet);
        editor.commit();
    }

    public boolean isProfilePicSet() {
        return pref.getBoolean(IS_PROFILE_PIC_SET, false);
    }

}
