package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayAttendance extends AppCompatActivity {
    DatabaseHelper myDB;
    String s;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance);
        myDB=new DatabaseHelper(this);
        Cursor c = myDB.getAllData("attendance");
        s=getIntent().getStringExtra("subj_id");

        DisplayViewAttendance();

    }
    public void DisplayViewAttendance()
    {
        Intent intent = new Intent(this, ViewAttendance.class);
        startActivity(intent);
    }
}
