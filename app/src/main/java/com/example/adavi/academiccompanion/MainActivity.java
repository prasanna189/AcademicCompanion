package com.example.adavi.academiccompanion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//just testing

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseHelper myDB;
    ArrayAdapter<Integer> adapter;
    private String m_Text = "";
    public static PrefManager prefManager;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefManager = new PrefManager(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
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

        if(prefManager.isProfilePicSet())
        {
            try
            {
                imageView.setImageBitmap(DbBitmapUtility.getImage(myDB.getImage("profile_pic")));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else //profile pic not set
        {

        }




        //Semester spinner

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
                            myDB.insertDataSemester(m_Text,"","");
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


        //Notifications

        notifyTodaysClasses();

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


    public void subject(View view) {
        Intent intent = new Intent(this, DisplaySubjectsActivity.class);
        startActivity(intent);
    }

    public void openEvents(View view) {
        Intent intent = new Intent(this, DisplayEventActivity.class);
        startActivity(intent);
    }


    // Code for settings options menu in action bar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

    public void openTimeTable(View view)
    {
        Intent dbmanager = new Intent(this, TimeTableActivity.class);
        startActivity(dbmanager);
    }

    public void openExamStats(View view)
    {
        Intent intent = new Intent(this, View_Stats.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navbar_schedule)
        {
            Intent intent = new Intent(this,ScheduleActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.navbar_settings) {
//            Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,AppSettingsActivity.class);
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
//            Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,NotificationAttendance.class);
            startActivity(intent);
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


    ////**************** NOTIFICATIONS

    public void notifyTodaysClasses()
    {
        String time = prefs.getString("notification_time",null);
        int hours = 7, minutes=0;


        String[] parts = time.split(":");

        String part1 = parts[0];
        String part2 = parts[1];
        if(time != null)
        {
            hours=Integer.parseInt(part1);
            minutes=Integer.parseInt(part2);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, NotifyTodaySubjectsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        time = prefs.getString("todays_attendance_notification_time",null);
        hours = 19;
        minutes=0;
        parts = time.split(":");

        part1 = parts[0];
        part2 = parts[1];
        if(time != null)
        {
            hours=Integer.parseInt(part1);
            minutes=Integer.parseInt(part2);
        }

        Toast.makeText(this,prefs.getString("todays_attendance_notification_time",null), Toast.LENGTH_SHORT ).show();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, hours);
        calendar1.set(Calendar.MINUTE, minutes);
        calendar1.set(Calendar.SECOND, 0);
        Intent intent2 = new Intent(MainActivity.this, NotificationAttendance.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MainActivity.this, 1,intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am1 = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);

    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    switch (which) {
//                        case DialogInterface.BUTTON_POSITIVE:
//                            finishAffinity();
////                            System.exit(0);
//                            break;
//
//                        case DialogInterface.BUTTON_NEGATIVE:
//                            break;
//                    }
//                }
//            };
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Exit the app?").setPositiveButton("EXIT", dialogClickListener)
//                    .setNegativeButton("CANCEL", dialogClickListener).show();
//
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


}
