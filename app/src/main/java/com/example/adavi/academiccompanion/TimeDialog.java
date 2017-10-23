package com.example.adavi.academiccompanion;

/**
 * Created by HP on 08-Apr-17.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

;

public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private static Button txt_time;

    public static TimeDialog newInstance(View view) {
        txt_time = (Button) view;
        return (new TimeDialog());
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default time in the dialog
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, false);

    }


    public void onTimeSet(TimePicker picker, int hour, int minute) {

        String hour_str = "" + hour;
        String minute_str = "" + minute;
        if (hour <= 9) {
            hour_str = "0" + hour_str;
        }
        if (minute <= 9) {
            minute_str = "0" + minute_str;
        }
        txt_time.setText(hour_str + ":" + minute_str);
    }
}