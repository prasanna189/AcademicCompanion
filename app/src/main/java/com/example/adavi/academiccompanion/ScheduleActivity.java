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
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
    static int button_id;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

//        setupActionBar();
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar c = Calendar.getInstance();
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

//
//    private void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Show the Up button in the action bar.
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            startActivity(new Intent(this, MainActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

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
            CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.schedule_fragment_cv);
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.schedule_fragment_ll);


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) // TODAY
            {
//                Toast.makeText(getContext(), "TODAY", Toast.LENGTH_SHORT).show();
                calendarView.setVisibility(View.GONE);
                displayEventHelper(ll);
            } else // this MONTH
            {
//                Toast.makeText(getContext(), "THIS MONTH", Toast.LENGTH_SHORT).show();

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                        String day;
                        String month;
                        month = String.valueOf(i1 + 1);
                        day = String.valueOf(i2);
                        if (i1 < 10) {
                            month = "0" + month;
                        }
                        if (i2 < 10) {
                            day = "0" + day;
                        }
                        String date = String.valueOf(i) + "-" + month + "-" + day;
//                        Toast.makeText(getContext(), date, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), DisplayEventsOnADateActivity.class);
                        intent.putExtra("date", date);
                        startActivity(intent);

                    }
                });


                //testing with caldroid

//                CaldroidFragment caldroidFragment = new CaldroidFragment();
//                Bundle args = new Bundle();
////                args.putString(CaldroidFragment.DIALOG_TITLE,"Click on a Date");
//                args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CaldroidDefaultDark);
//                args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.TUESDAY);
//                Calendar cal = Calendar.getInstance();
//                args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
//                args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
//                caldroidFragment.setArguments(args);
//
//                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
//                t.replace(R.id.schedule_fragment_cv, caldroidFragment);
//                t.commit();

            }

            return rootView;
        }


        //*******************************

        public void displayEventHelper(LinearLayout ll) {
            Calendar c = Calendar.getInstance();
            formattedDate = df.format(c.getTime());

            Cursor res = myDB.getTodayEvents();
            if (res.getCount() == 0) {
                Toast.makeText(getContext(), "No Events Today!", Toast.LENGTH_LONG).show();
                return;
            }

            while (res.moveToNext()) {
                displayEvent(ll, res.getInt(0), res.getString(1), res.getString(2), res.getString(3));
            }
        }


        public void displayEvent(LinearLayout eventLL, int eventid, String ename, String date, String StartTime) {

            //layout to which children are added
//        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);


            //child layouts
            Button rowButton = new Button(getContext());
            TextView tv = new TextView(getContext());
            LinearLayout ll = new LinearLayout(getContext());


            //layout params for each view

            LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            ll_params.setMargins(24, 24, 24, 24);


            LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

//        rb_params.setMargins(8, 8, 8, 8);

            LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    4.0f
            );

            tv_params.setMargins(24, 8, 8, 8);

            tv.setLayoutParams(tv_params);
            ll.setLayoutParams(ll_params);
            rowButton.setLayoutParams(rb_params);


            rowButton.setId(eventid);
            rowButton.setText(ename);
            rowButton.setTextSize(20);
            rowButton.setBackgroundColor(Color.rgb(224, 242, 241));

            rowButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Button pressed;
                    pressed = ((Button) v);
                    button_id = pressed.getId();
                    viewEventDetails(v);
                }
            });

// Add id to buttons and also on click listner to these buttons
            tv.setText(StartTime);
            tv.setTextSize(16);


            ll.setBackgroundColor(Color.rgb(224, 242, 241));
            ll.addView(rowButton);
            ll.addView(tv);
//            ll.setBackgroundColor(Color.LTGRAY);
            rowButton.setBackgroundColor(Color.LTGRAY);
            eventLL.addView(ll);
        }

        public void viewEventDetails(View v) {
            Intent intent = new Intent(getContext(), DisplayEventOnScheduleActivity.class);
            String s = Integer.toString(button_id);
            intent.putExtra("button_event_id", s);
            intent.putExtra("date", formattedDate);
            startActivity(intent);

        }

        //**********************************

    }

    //On clicking back button, it takes back to main acitivity..

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


}
