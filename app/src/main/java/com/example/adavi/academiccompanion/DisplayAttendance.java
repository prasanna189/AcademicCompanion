package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayAttendance extends AppCompatActivity {
    DatabaseHelper myDB;
    String s;
   // int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance);
        myDB=new DatabaseHelper(this);
       // Cursor c = myDB.getAllData("attendance");
        s=getIntent().getStringExtra("subj_id");

        //DisplayViewAttendance();

    }
    public void ViewAttendance(View view)
    {
        Intent intent = new Intent(DisplayAttendance.this, ViewAttendance.class);
        intent.putExtra("subj_id",s);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, DisplaySubjectDetails.class);
        intent.putExtra("sub_id",s);
        startActivity(intent);
        super.onBackPressed();
    }
}
