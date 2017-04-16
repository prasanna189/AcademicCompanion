package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class View_Stats extends AppCompatActivity {

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__stats);
        myDb=new DatabaseHelper(this);
        setTitle("Semester Statistics");
    }

    public void subjects(View view)
    {
        Intent intent=new Intent(this,ViewSubjects.class);
        //intent.putExtra("check_id","xyz");
        startActivity(intent);
    }

    public void results(View view)
    {
        Intent intent=new Intent(this,ViewResults.class);
        startActivity(intent);
    }

    public void attendance(View view)
    {
        Intent intent=new Intent(this,ViewFinalAttendance.class);
        startActivity(intent);
    }

    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("sub_id",str);
        startActivity(intent);
        super.onBackPressed();
    }

}
