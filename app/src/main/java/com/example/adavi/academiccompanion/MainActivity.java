package com.example.adavi.academiccompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.input;

//just testing

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseHelper myDB;
    ArrayAdapter<Integer> adapter;
    private String m_Text = "";


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


        //Semester spinner

//        Spinner semester = (Spinner)findViewById(R.id.navbar_semester_spinner);

        final Cursor sem_res = myDB.getAllData("Semester");
        final String[] sem_array= new String[sem_res.getCount()+2];
        sem_array[0]=String.valueOf(myDB.getcurrentsem());
        int i=1;
        while(sem_res.moveToNext())
        {
            sem_array[i]=String.valueOf(sem_res.getInt(0));
            i=i+1;
        }
        sem_array[i]="+";

        Spinner spinner = (Spinner) navigationView.getMenu().findItem(R.id.navbar_semester).getActionView();
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sem_array));
        spinner.setDropDownWidth(-2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                //Add a new semester.
                if(position==sem_res.getCount()+1){



                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Enter Semester ");

                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT );


                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            myDB.setCurrentSem(m_Text);
                            myDB.insertDataSemester(m_Text);
                            sem_array[0]=String.valueOf(myDB.getcurrentsem());
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                    sem_array[0]=String.valueOf(myDB.getcurrentsem());
                }
                else{
                    myDB.setCurrentSem(sem_array[position]);
                    sem_array[0]=String.valueOf(myDB.getcurrentsem());
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(MainActivity.this,"hi",Toast.LENGTH_SHORT).show();
            }
        });
//        spinner.setSelection(adapter.getPosition(0));


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

        if (id == R.id.navbar_schedule)
        {
            Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.navbar_settings) {
//            Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.navbar_help)
        {
//            Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,HelpActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.navbar_notifications)
        {
            Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.navbar_about)
        {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }




}
