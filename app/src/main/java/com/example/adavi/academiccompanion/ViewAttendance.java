package com.example.adavi.academiccompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ViewAttendance extends AppCompatActivity {

    Spinner AttendanceList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);


        AttendanceList=(Spinner) findViewById(R.id.attendance_spinner);

        String [] subject_array={"","Present","Absent","ExtraClass","All"};

        adapter=new ArrayAdapter<String>(this,
                R.layout.attendance_filter,R.id.Attendance_list, subject_array);

        AttendanceList.setAdapter(adapter);

    }
}
