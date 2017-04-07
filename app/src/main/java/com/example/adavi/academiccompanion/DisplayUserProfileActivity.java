package com.example.adavi.academiccompanion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayUserProfileActivity extends AppCompatActivity {

    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);

        myDB = new DatabaseHelper(this);

        TextView username = (TextView) findViewById(R.id.display_username_tv);
        TextView useremail = (TextView) findViewById(R.id.display_useremail_tv);
        TextView userphone = (TextView) findViewById(R.id.display_userphone_tv);

        username.setText(myDB.getUserName());
        useremail.setText(myDB.getUserEmail());
        userphone.setText(myDB.getUserPhone());


    }




}
