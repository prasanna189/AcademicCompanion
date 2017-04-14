package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DateFormat;
////import android.icu.text.SimpleDateFormat;
//import android.icu.text.SimpleDateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.icu.util.Calendar.getInstance;



public class ViewAttendance extends AppCompatActivity {
    RadioGroup rg[] ;
    RadioButton rb[];
    DatabaseHelper myDB;
    String s;
    boolean j;
    int coun=0;

    int len;
    Spinner AttendanceList;
    ArrayAdapter<String> adapter;
    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        myDB=new DatabaseHelper(this);
        s=getIntent().getStringExtra("subj_id");

        AttendanceList=(Spinner) findViewById(R.id.attendance_spinner);

        String [] subject_array={"All","Present","Absent","Class Not Conducted"};

        adapter=new ArrayAdapter<String>(this,
                R.layout.attendance_filter,R.id.Attendance_list, subject_array);

        AttendanceList.setAdapter(adapter);
        filter="All";
       //AttendanceList.setSelection(adapter.getPosition(filter));
        AttendanceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView tv=(TextView)vg.findViewById(R.id.Attendance_list);


                filter=tv.getText().toString();
                Toast.makeText(ViewAttendance.this,"Gone", Toast.LENGTH_SHORT).show();
                display(filter);
                Toast.makeText(ViewAttendance.this,filter, Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

                filter="All";
                display(filter);
            }

        });


    }

    public void display(String filter) {
        String startdate = "";
        Cursor res = myDB.getAllData("semester");
        while (res.moveToNext()) {
            if (res.getInt(0) == myDB.getcurrentsem()) {
                startdate = res.getString(1);
            }
        }

        Calendar c = Calendar.getInstance();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayformat = new SimpleDateFormat("EEEE");
        String formattedDate = df.format(c.getTime());


        // java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(ViewAttendance.this);


        LinearLayout ll = (LinearLayout) findViewById(R.id.att);
        ll.removeAllViews();
        String sdate = null;
        Cursor cur = myDB.getAllData("attendance");
        while (cur.moveToNext()) {
            if (cur.getString(2).equals(s)) {
                sdate = cur.getString(3);
            }
        }
        if (sdate != null) {
            List<Date> dates = getDates(sdate, formattedDate);
            int count = 0;
            for (Date date : dates) {
                count = count + 1;
                if (count != 1) {
                    String day = dayformat.format(date);
                    Cursor tt = myDB.getAllData("timetable");

                    while (tt.moveToNext()) {

                        if (myDB.getcurrentsem() == tt.getInt(1) && tt.getString(2).equals(s) && tt.getString(3).equals(day)) {
                            boolean i = myDB.insertDataAttendance(tt.getInt(1), tt.getInt(2), df.format(date), "", -1);
                        }
                    }


                }

            }


        } else {
            Toast.makeText(ViewAttendance.this, startdate, Toast.LENGTH_SHORT).show();
            List<Date> dates = getDates(startdate, formattedDate);

            for (Date date : dates) {
                String day = dayformat.format(date);
                Cursor tt = myDB.getAllData("timetable");

                while (tt.moveToNext()) {

                    if (myDB.getcurrentsem() == tt.getInt(1) && tt.getString(2).equals(s) && tt.getString(3).equals(day)) {
                        boolean i = myDB.insertDataAttendance(tt.getInt(1), tt.getInt(2), df.format(date), "", -1);
                    }
                }

            }

        }


        List<Date> dates = getDates(startdate, formattedDate);
        int getcount = 0;
        for (Date date : dates) {
            String day = dayformat.format(date);
            Cursor tt = myDB.getAllData("timetable");

            while (tt.moveToNext()) {

                if (myDB.getcurrentsem() == tt.getInt(1) && tt.getString(2).equals(s) && tt.getString(3).equals(day)) {
                    getcount++;

                }
            }

        }


//        Button submit =new Button(this);
//                submit.setText("SAVE");
//                ll.addView(submit);
//                submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                        Intent intent = new Intent(ViewAttendance.this, DisplayAttendance.class);
//                        intent.putExtra("subj_id",s);
//
//                        startActivity(intent);
//
//                    }
//                });
        if (filter.equals("All")) {


        rg = new RadioGroup[getcount];
        rb = new RadioButton[getcount * 5];
            len=getcount*5;
    }
    else
        {
            Cursor b = myDB.getAllData("attendance");
            while(b.moveToNext())
            {
                if(b.getString(4).equals(filter))
                {
                    coun++;
                }
            }

            rg = new RadioGroup[coun];
            rb = new RadioButton[coun * 5];
            len=coun*5;


        }

        int p=0,q=0;
        for(Date date:dates)
        {
            String day=dayformat.format(date);
            Cursor tt=myDB.getAllData("timetable");
            while(tt.moveToNext())
            {

                if(myDB.getcurrentsem()==tt.getInt(1) && tt.getString(2).equals(s) && tt.getString(3).equals(day))
                {
                    int flag=0;
                    int id=myDB.getAttendanceId(tt.getInt(1),tt.getInt(2),df.format(date));
                    String status="";
                    Cursor check=myDB.getAllData("attendance");
                    //Toast.makeText(ViewAttendance.this,"Nfxknldb", Toast.LENGTH_SHORT).show();

                    while(check.moveToNext())
                    {
                        if(check.getInt(0)==id)
                        {
                            status=check.getString(4);
                            break;
                        }
                    }
                    if(filter.equals("All"))
                    {
                        TextView tv =new TextView(this);
                        tv.setText(df.format(date));
                        tv.setTextSize(30);


                        ll.addView(tv);

                        rg[p] = new RadioGroup(this);
                        rg[p].setOrientation(RadioGroup.VERTICAL);

                        rb[q] = new RadioButton(this);
                        rb[q].setText("Present");
                        rb[q].setId(Integer.parseInt("1"));
                        rg[p].addView(rb[q]);
                        if(status.equals("Present"))
                        {
                            rb[q].setChecked(true);
                            flag=1;
                        }

                        q++;
                        rb[q] = new RadioButton(this);
                        rb[q].setText("Absent");
                        rb[q].setId(Integer.parseInt("2"));
                        rg[p].addView(rb[q]);
                        if(status.equals("Absent"))
                        {
                            rb[q].setChecked(true);
                            flag=1;
                        }

                        q++;
                        rb[q] = new RadioButton(this);
                        rb[q].setText("Class Not Conducted");
                        rb[q].setId(Integer.parseInt("3"));
                        rg[p].addView(rb[q]);
                        if(status.equals("Class Not Conducted"))
                        {
                            rb[q].setChecked(true);
                            flag=1;
                        }

                        q++;
                        rb[q] = new RadioButton(this);
                        rb[q].setText("Not Approved");
                        rb[q].setId(Integer.parseInt("4"));
                        rg[p].addView(rb[q]);
                        if(flag==0)
                        {
                            rb[q].setChecked(true);
                        }

                        q++;
                        rb[q]=new RadioButton(this);
                        rb[q].setId(id);
                        q++;


                        ll.addView(rg[p]);
                        p++;

                    }
                    else if(status.equals(filter))
                    {
                        TextView tv =new TextView(this);
                        tv.setText(df.format(date));
                        tv.setTextSize(30);


                        ll.addView(tv);

                        rg[p] = new RadioGroup(this);
                        rg[p].setOrientation(RadioGroup.VERTICAL);

                        rb[q] = new RadioButton(this);
                        rb[q].setText("Present");
                        rb[q].setId(Integer.parseInt("1"));
                        rg[p].addView(rb[q]);
                        if(status.equals("Present"))
                        {
                            rb[q].setChecked(true);
                            flag=1;
                        }

                        q++;
                        rb[q] = new RadioButton(this);
                        rb[q].setText("Absent");
                        rb[q].setId(Integer.parseInt("2"));
                        rg[p].addView(rb[q]);
                        if(status.equals("Absent"))
                        {
                            rb[q].setChecked(true);
                            flag=1;
                        }

                        q++;
                        rb[q] = new RadioButton(this);
                        rb[q].setText("Class Not Conducted");
                        rb[q].setId(Integer.parseInt("3"));
                        rg[p].addView(rb[q]);
                        if(status.equals("Class Not Conducted"))
                        {
                            rb[q].setChecked(true);
                            flag=1;
                        }

                        q++;
                        rb[q] = new RadioButton(this);
                        rb[q].setText("Not Approved");
                        rb[q].setId(Integer.parseInt("4"));
                        rg[p].addView(rb[q]);
                        if(flag==0)
                        {
                            rb[q].setChecked(true);
                        }

                        q++;
                        rb[q]=new RadioButton(this);
                        rb[q].setId(id);
                        q++;


                        ll.addView(rg[p]);
                        p++;
                    }
                }
            }
        }

        Button submit =new Button(this);
        submit.setText("SAVE");
        ll.addView(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    int r;
                    String radioText="";




                for (r = 0; r < len; r++)
                    {
                        if (rb[r].isChecked()  && (r+1)%5!=0)
                        {
//                            Toast.makeText(ViewAttendance.this,"Nothing Is Checked", Toast.LENGTH_SHORT).show();

                            RadioButton id = (RadioButton) findViewById(rb[r].getId());
                             radioText = rb[r].getText().toString();

                        }
                        else if((r+1)%5==0)
                        {

                            int id=rb[r].getId();
//                            Toast.makeText(ViewAttendance.this,Integer.toString(id), Toast.LENGTH_SHORT).show();
                            String date="";
                            Cursor res=myDB.getAllData("attendance");
                            while(res.moveToNext())
                            {
                                if(res.getInt(0)==id)
                                {
                                    date=res.getString(3);
                                    break;
                                }
                            }


                             j=myDB.updatetDataAttendance(id,myDB.getcurrentsem(),Integer.parseInt(s),date,radioText,-1);
                            //Toast.makeText(ViewAttendance.this,"b", Toast.LENGTH_SHORT).show();


                        }
                    }
                            if(j)
                            {

                                Toast.makeText(ViewAttendance.this,"Update Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ViewAttendance.this, DisplayAttendance.class);
                                intent.putExtra("subj_id",s);
                                startActivity(intent);


                            }
                            else
                            {
                                Toast.makeText(ViewAttendance.this,"Updation Failed", Toast.LENGTH_SHORT).show();
                            }
            }
        });

    }
    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        }
        catch (Exception e) {
            return  null;
        }

        Calendar cal1 = getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, DisplayAttendance.class);
        intent.putExtra("subj_id",s);
        startActivity(intent);
        super.onBackPressed();
    }
}
