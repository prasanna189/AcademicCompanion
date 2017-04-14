package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
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
import java.util.Date;


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

        Toast.makeText(NotificationAttendance.this,currenDate, Toast.LENGTH_SHORT).show();
        Toast.makeText(NotificationAttendance.this,currenDay, Toast.LENGTH_SHORT).show();
        Cursor res=myDB.getAllData("timetable");
        int count=0;
        while(res.moveToNext())
        {
            if(res.getString(3).equals(currenDay) && res.getInt(1)==myDB.getcurrentsem())
            {
                count++;
            }
        }
        Toast.makeText(NotificationAttendance.this,Integer.toString(count), Toast.LENGTH_SHORT).show();
        rg = new RadioGroup[count];
        rb = new RadioButton[count*5];
        len=count*5;

        int p=0,q=0;

        res=myDB.getAllData("timetable");
        while(res.moveToNext())
        {
            if(res.getString(3).equals(currenDay) && res.getInt(1)==myDB.getcurrentsem())
            {
                Cursor k=myDB.getAllData("attendance");
                int flag=0;
                while(k.moveToNext()&&flag==0)
                {
                    if(res.getInt(2)==k.getInt(2) && k.getString(3).equals(currenDate) && !(k.getString(4).equals("Not Approved")))
                    {
                        flag=1;
                    }
                }
                if(flag==0)
                {
                    boolean i= myDB.insertDataAttendance(res.getInt(1),res.getInt(2),currenDate,"Not Approved",-1);
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
        btn.setText("Submit");
        pll.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
