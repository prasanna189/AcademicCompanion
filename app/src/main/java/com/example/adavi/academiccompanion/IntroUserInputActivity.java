package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class IntroUserInputActivity extends AppCompatActivity {


    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_user_input);
        myDB = new DatabaseHelper(this);

    }

    void submit(View view) {
        boolean flag = true;
        EditText nameId = (EditText) findViewById(R.id.name_id);
        EditText semId = (EditText) findViewById(R.id.sem_id);
        EditText emailId = (EditText) findViewById(R.id.email_id);

        /*
        if (nameId.getText().toString().length() == 0) {
            nameId.setError("Please Enter Your Name");
            flag = false;
        }
        if (semId.getText().toString().length() == 0) {
            semId.setError("Please Enter Your Semester");
            flag = false;
        }
        if (emailId.getText().toString().length() == 0) {
            emailId.setError("Please Enter Your Email ID");
            flag = false;
        }
        */

        if (flag)
        {
            boolean a,b,c;

            myDB.setCurrentSem( semId.getText().toString() );

            a = myDB.insertDataUserDetails(nameId.getText().toString(), emailId.getText().toString(), "", semId.getText().toString());
            c = myDB.insertDataSemester( semId.getText().toString() );

            com.example.adavi.academiccompanion.WelcomeActivity.prefManager.setFirstTimeLaunch(false);
            Intent intent = new Intent(IntroUserInputActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            //setting firsttimelaunch as false, so from next time directly home screen is opened.

        }
    }
}