package com.example.adavi.academiccompanion;

/**
 * Created by HP on 07-Apr-17.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Button txtdate;

    public DateDialog(View view) {
        txtdate = (Button) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


// Use the current date as the default date in the dialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);


    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //show to the selected date in the text box
        String daystr = "" + day + "";
        if (day <= 9) {
            daystr = "0" + daystr;
        }

        if (month <= 9) {
            String date = year + "-0" + (month + 1) + "-" + daystr;
            txtdate.setText(date);
        } else {
            String date = year + "-" + (month + 1) + "-" + daystr;
            txtdate.setText(date);
        }
    }


}
