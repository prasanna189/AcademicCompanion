package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class View_Stats extends AppCompatActivity {

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__stats);
        myDb=new DatabaseHelper(this);
    }

    public void subjects()
    {
        Intent intent=new Intent(this,ViewSubjects.class);
        startActivity(intent);
    }

    public void results()
    {
        Intent intent=new Intent(this,ViewResults.class);
        startActivity(intent);
    }

    public void attendance()
    {
        Intent intent=new Intent(this,ViewFinalAttendance.class);
        startActivity(intent);
    }


}
