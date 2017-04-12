package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.adavi.academiccompanion.R.id.container;

public class ScheduleActivity extends AppCompatActivity {


    static DatabaseHelper myDB;
    static String formattedDate;
    static String formattedTime;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    public void eventAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

        formattedDate = df.format(c.getTime());
        formattedTime = tf.format(c.getTime());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
            CalendarView calendarView = (CalendarView)rootView.findViewById(R.id.schedule_fragment_cv);


            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) // TODAY
            {
                Toast.makeText(getContext(), "TODAY", Toast.LENGTH_SHORT).show();
                calendarView.setVisibility(View.GONE);

                Cursor res = myDB.getRecentEvents();
                if (res.getCount() == 0) {
                    eventAlert("No Events Today.","Have a Nice Day!");
//                    Toast.makeText(getContext(), "No events today.", Toast.LENGTH_SHORT).show();
                    return rootView;
                }

                while (res.moveToNext()) {
                    if(res.getString(2).compareTo(formattedDate)>0)
                    {
                        displayEvent(res.getInt(0), res.getString(1), res.getString(2),1);
                    }
                    else if(res.getString(2).compareTo(formattedDate)==0)
                    {
                        if(res.getString(4).compareTo(formattedTime)>0)
                        {
                            displayEvent(res.getInt(0), res.getString(1), res.getString(2),1);
                        }
                        else
                        {
                            displayEvent(res.getInt(0), res.getString(1), res.getString(2),0);
                        }
                    }
                    else
                    {
                        displayEvent(res.getInt(0), res.getString(1), res.getString(2),0);
                    }

                }

            }
            else // this MONTH
            {
                Toast.makeText(getContext(), "THIS MONTH", Toast.LENGTH_SHORT).show();



                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
//                        dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);

                        Toast.makeText(getContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
                    }
                });


            }

            return rootView;
        }

    }


    //*******************************



    public void displayEvent(int eventid, String ename, String date,int status) {

//        //layout to which children are added
////        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
//        LinearLayout eventLL = (LinearLayout) findViewById(R.id.event_display_ll);
//
//
//        //child layouts
//        Button rowButton = new Button(this);
//        TextView tv = new TextView(this);
//        LinearLayout ll = new LinearLayout(this);
//
//
//        //layout params for each view
//
//        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//
//        ll_params.setMargins(24, 24, 24, 24);
//
//
//        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                1.0f
//        );
//
////        rb_params.setMargins(8, 8, 8, 8);
//
//        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                4.0f
//        );
//
//        tv_params.setMargins(24, 8, 8, 8);
//
//        tv.setLayoutParams(tv_params);
//        ll.setLayoutParams(ll_params);
//        rowButton.setLayoutParams(rb_params);
//
//
//        rowButton.setId(eventid);
//        rowButton.setText(ename);
//        rowButton.setTextSize(20);
//        rowButton.setBackgroundColor(Color.rgb(224, 242, 241));
//
//        rowButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v)
//            {
//
//                Button pressed;
//                pressed=((Button)v);
//                button_id=pressed.getId();
//                viewEventDetails(v);
//
//
//            }
//        });
//// Add id to buttons and also on click listner to these buttons
//        tv.setText(date);
//        tv.setTextSize(12);
//
//
//        ll.setBackgroundColor(Color.rgb(224, 242, 241));
//        ll.addView(rowButton);
//        ll.addView(tv);
//        if(status==0)
//        {
//            ll.setBackgroundColor(Color.LTGRAY);
//            rowButton.setBackgroundColor(Color.LTGRAY);
//        }
//        eventLL.addView(ll);
    }








    //**********************************





    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            //number of tabs
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODAY";
                case 1:
                    return "THIS MONTH";
            }
            return null;
        }
    }

    //On clicking back button, it takes back to main acitivity..

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}
