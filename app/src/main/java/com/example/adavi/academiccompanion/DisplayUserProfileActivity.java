package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayUserProfileActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);
        myDB = new DatabaseHelper(this);

        TextView username = (TextView) findViewById(R.id.display_username_tv);
        TextView useremail = (TextView) findViewById(R.id.display_useremail_tv);
        TextView userphone = (TextView) findViewById(R.id.display_userphone_tv);
        ImageView imageView = (ImageView)findViewById(R.id.display_profile_icon);
        imageView.setImageBitmap(DbBitmapUtility.getImage(myDB.getImage("profile_pic")));
        username.setText(myDB.getUserName());
        useremail.setText(myDB.getUserEmail());
        userphone.setText(myDB.getUserPhone());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_user_profile)
        {


            Intent intent = new Intent(this,EditUserProfileActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
