package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//just testing

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseHelper myDB;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //code for setting user's name, profile pic and email in the nav bar

        TextView username;
        TextView useremail;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        ImageView imageView = (ImageView)header.findViewById(R.id.userImageView);
        username = (TextView)header.findViewById(R.id.navbar_username_tv);
        useremail = (TextView)header.findViewById(R.id.navbar_useremail_tv);
        username.setText(myDB.getUserName());
        useremail.setText(myDB.getUserEmail());

     //   imageView.setImageBitmap(DbBitmapUtility.getImage(myDB.getImage("profile_pic")));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void subject(View view) {
        Intent intent = new Intent(this, DisplaySubjectsActivity.class);
        startActivity(intent);
    }

    public void openEvents(View view) {
        Intent intent = new Intent(this, DisplayEventActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void databasemanager(View view)
    {
        Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    public void openUserProfile(View view)
    {
        Intent dbmanager = new Intent(this,DisplayUserProfileActivity.class);
        startActivity(dbmanager);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navbar_schedule) {
            Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.navbar_settings) {
            Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.navbar_help) {
            Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.navbar_notifications) {
            Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.navbar_semester) {

            //Semester Spinner

            Spinner semester = (Spinner)findViewById(R.id.navbar_semester_spinner);

            Cursor sem_res = myDB.getAllData("Semester");
            String[] sem_array= new String[sem_res.getCount()+1];
            sem_array[0]="";
            int i=1;
            while(sem_res.moveToNext())
            {
                sem_array[i]=String.valueOf(sem_res.getInt(0));
                i=i+1;
            }

            adapter=new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.textview, sem_array);

            semester.setAdapter(adapter);

            semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {

                    ViewGroup vg=(ViewGroup)view;

//                    TextView tv=(TextView)vg.findViewById(R.id.textview);
//                    subject_name=tv.getText().toString();
                    Toast.makeText(MainActivity.this, "fair enough", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
//                    subject_name=null;
                }

            });
            semester.setSelection(adapter.getPosition(String.valueOf(myDB.getcurrentsem())));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }




}
