package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditUserProfileActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        myDB = new DatabaseHelper(this);

        EditText username = (EditText) findViewById(R.id.edit_username_et);
        EditText useremail = (EditText) findViewById(R.id.edit_useremail_et);
        EditText userphone = (EditText) findViewById(R.id.edit_userphone_et);

        username.setText(myDB.getUserName());
        useremail.setText(myDB.getUserEmail());
        userphone.setText(myDB.getUserPhone());

    }

    public void saveUserProfile(View view)
    {
        boolean flag;
        EditText username = (EditText) findViewById(R.id.edit_username_et);
        EditText useremail = (EditText) findViewById(R.id.edit_useremail_et);
        EditText userphone = (EditText) findViewById(R.id.edit_userphone_et);

        flag = myDB.updateDataUserDetails( username.getText().toString(), useremail.getText().toString(), userphone.getText().toString() );

        if(flag == true)//insertion successful
        {

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header=navigationView.getHeaderView(0);

            TextView uname = (TextView)header.findViewById(R.id.navbar_username_tv);
            TextView uemail = (TextView)header.findViewById(R.id.navbar_useremail_tv);
            uname.setText(myDB.getUserName());
            uemail.setText(myDB.getUserEmail());

            Intent intent = new Intent(this,DisplayUserProfileActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(EditUserProfileActivity.this, "Update not successful.", Toast.LENGTH_SHORT).show();
        }
    }
}
