package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
//import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.icu.util.Calendar.getInstance;


public class NotificationAttendance extends AppCompatActivity {

    DatabaseHelper myDB;
    RadioButton[] rb;
    RadioGroup[] rg;
    int len;
    boolean j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_attendance);
        myDB=new DatabaseHelper(this);

         viewtodaysubjects();

    }

    public void viewtodaysubjects()
    {
        LinearLayout pll=(LinearLayout) findViewById(R.id.notific_atten_layout);
        Button btn=new Button(this);

        LinearLayout cll=new LinearLayout(this);
        cll.setOrientation(LinearLayout.VERTICAL);

        String sdate=null;
        Cursor ssdate=myDB.getAllData("semester");
        while(ssdate.moveToNext())
        {
            if(ssdate.getInt(0)==myDB.getcurrentsem())
            {
                sdate=ssdate.getString(1);
            }
        }

        if(sdate==null)
        {
            Toast.makeText(NotificationAttendance.this,"No Start Date is added in time table", Toast.LENGTH_SHORT).show();

        }
        else
        {




            int co=0;
            Cursor time=myDB.getAllData("timetable");
            while(time.moveToNext() )
            {
                if(time.getInt(1)==myDB.getcurrentsem() )
                {
                    co++;
                }

            }
            if(co==0)
            {
                Toast.makeText(NotificationAttendance.this,"No subject added time table", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Calendar c = Calendar.getInstance();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dayformat = new SimpleDateFormat("EEEE");

                String currenDate = df.format(c.getTime());
                String d;
                Date x=null;
                try {
                    x=df.parse(currenDate);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }





                String currenDay=dayformat.format(x);


                Cursor res=myDB.getAllData("timetable");
                while(res.moveToNext())
                {
                    if(res.getString(3).equals(currenDay) && res.getInt(1)==myDB.getcurrentsem())
                    {
                        Cursor k=myDB.getAllData("attendance");
                        int fflag=0;
                        while(k.moveToNext()&& fflag==0)
                        {
                            if(res.getInt(2)==k.getInt(2) && k.getString(3).equals(currenDate) && !(k.getString(4).equals("Not Approved")))
                            {
                                fflag=1;

                            }

                        }
                        if(fflag==0)
                        {
                            int flag1=0;
                            k=myDB.getAllData("attendance");
                            while(k.moveToNext()&&flag1==0)
                            {
                                if(k.getInt(2)==res.getInt(2)  && k.getString(3).equals(currenDate))
                                {
                                    flag1=1;
                                }
                            }
                            if(flag1==0) {
                                k = myDB.getAllData("attendance");
                                String lastaddeddate = null;
                                while (k.moveToNext()) {
                                    if (k.getInt(2) == res.getInt(2)) {
                                        lastaddeddate = k.getString(3);
                                    }
                                }

                                if (lastaddeddate == null) {
                                    lastaddeddate = sdate;
                                }

                                List<Date> dates = getDates(lastaddeddate, currenDate);
                                int count1 = 0;
                                for (Date date : dates) {
                                    count1 = count1 + 1;
                                    if (count1 != 1) {
                                        String day = dayformat.format(date);
                                        Cursor tt = myDB.getAllData("timetable");

                                        while (tt.moveToNext()) {

                                            if (myDB.getcurrentsem() == tt.getInt(1) && tt.getString(2).equals(res.getString(2)) && tt.getString(3).equals(day)) {
                                                boolean i = myDB.insertDataAttendance(tt.getInt(1), tt.getInt(2), df.format(date), "Not Approved", -1);
                                            }
                                        }


                                    }

                                }


                                // boolean i= myDB.insertDataAttendance(res.getInt(1),res.getInt(2),currenDate,"Not Approved",-1);
                            }
                        }


                    }
                }




                 res=myDB.getAllData("timetable");
                int count=0;
                while(res.moveToNext())
                {
                    if(res.getString(3).equals(currenDay) && res.getInt(1)==myDB.getcurrentsem() )
                    {
                        Cursor k=myDB.getAllData("attendance");

                        while(k.moveToNext())
                        {
                            if(res.getInt(2)==k.getInt(2) && k.getString(3).equals(currenDate) && (k.getString(4).equals("Not Approved")))
                            {
                               // Toast.makeText(NotificationAttendance.this,Integer.toString(count), Toast.LENGTH_SHORT).show();

                                count++;

                            }

                        }

                    }
                }
              //   Toast.makeText(NotificationAttendance.this,Integer.toString(count), Toast.LENGTH_SHORT).show();
                rg = new RadioGroup[count];
                rb = new RadioButton[count*5];
                len=count*5;

               // Toast.makeText(NotificationAttendance.this,Integer.toString(len), Toast.LENGTH_SHORT).show();


                int p=0,q=0;
                int flag=0;
                res=myDB.getAllData("timetable");
                while(res.moveToNext())
                {
                    if(res.getString(3).equals(currenDay) && res.getInt(1)==myDB.getcurrentsem())
                    {
                        Cursor k=myDB.getAllData("attendance");
                        flag=0;
                        while(k.moveToNext()&& flag==0)
                        {
                            if(res.getInt(2)==k.getInt(2) && k.getString(3).equals(currenDate) && !(k.getString(4).equals("Not Approved")))
                            {
                                flag=1;

                            }

                        }
                        if(flag==0)
                        {
                            int flag1=0;
                            k=myDB.getAllData("attendance");
//                            while(k.moveToNext()&&flag1==0)
//                            {
//                                if(k.getInt(2)==res.getInt(2)  && k.getString(3).equals(currenDate))
//                                {
//                                    flag1=1;
//                                }
//                            }
//
                            String sname=myDB.getSubjectName(res.getInt(2));

                            int id=myDB.getAttendanceId(myDB.getcurrentsem(),res.getInt(2),currenDate);

                            TextView tv=new TextView(this);
                            tv.setText(sname);
                            rg[p] = new RadioGroup(this);
                            rg[p].setOrientation(RadioGroup.VERTICAL);
                            rb[q] = new RadioButton(this);
                            rb[q].setText("Present");

                            rb[q].setId(Integer.parseInt("1"));
                            rg[p].addView(rb[q]);
                            q++;
                            rb[q] = new RadioButton(this);
                            rb[q].setText("Absent");
                            rb[q].setId(Integer.parseInt("2"));
                            rg[p].addView(rb[q]);

                            q++;
                            rb[q] = new RadioButton(this);
                            rb[q].setText("Class Not Conducted");
                            rb[q].setId(Integer.parseInt("3"));
                            rg[p].addView(rb[q]);

                            q++;
                            rb[q] = new RadioButton(this);
                            rb[q].setText("Not Approved");
                            rb[q].setId(Integer.parseInt("4"));
                            rb[q].setChecked(true);
                            rg[p].addView(rb[q]);

                            q++;
                            rb[q]=new RadioButton(this);
                            rb[q].setId(id);
                            q++;

                            cll.addView(tv);
                            cll.addView(rg[p]);
                            p++;

                        }


                    }
                }

                pll.addView(cll);

                if(flag==1)
                {
                    Toast.makeText(this,"Today's attendance already added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    btn.setText("Submit");
                    pll.addView(btn);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int r;
                            String radioText="";
//                            Toast.makeText(NotificationAttendance.this,Integer.toString(len), Toast.LENGTH_SHORT).show();
                            for (r = 0; r < len; r++)
                            {
                                if (rb[r].isChecked()  && (r+1)%5!=0)
                                {
                                    RadioButton id = (RadioButton) findViewById(rb[r].getId());
                                    radioText = rb[r].getText().toString();

                                }
                                else if((r+1)%5==0)
                                {
                                    int id=rb[r].getId();
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
                                    //  Toast.makeText(ViewAttendance.this,date, Toast.LENGTH_SHORT).show();

                                    j=myDB.updatetDataAttendance(id,myDB.getcurrentsem(),res.getInt(2),date,radioText,-1);

                                }
                            }
                            if(j)
                            {
                                Toast.makeText(NotificationAttendance.this,"Task Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NotificationAttendance.this, MainActivity.class);
                                //finish();
                                startActivity(intent);


                            }
                            else
                            {
                                Toast.makeText(NotificationAttendance.this,"Task UnSuccessful", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }
        }

        }


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

    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


}
