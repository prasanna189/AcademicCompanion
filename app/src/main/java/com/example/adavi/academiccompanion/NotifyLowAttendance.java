package com.example.adavi.academiccompanion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by pk on 4/18/2017.
 */

public class NotifyLowAttendance extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "hello low attendance pk", Toast.LENGTH_SHORT).show();

//        Toast.makeText(context,"debug", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Id = intent.getStringExtra("id");
        int id=19999;
        if(Id!=null)
        {
            id = Integer.parseInt(Id);
        }
        String rtime = intent.getStringExtra("Remainder_Time");

        if(rtime == null)
        {
            rtime = "20:00";
        }

        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String cdate = df.format(c.getTime());

        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        String ctime = tf.format(c.getTime());

//        Toast.makeText(context, rdate+rtime+" = "+cdate+ctime, Toast.LENGTH_LONG).show();

        if(rtime.equals(ctime))
        {

//            Toast.makeText(context, "hello low attendance pk", Toast.LENGTH_SHORT).show();
            DatabaseHelper myDB = new DatabaseHelper(context);

            Cursor res = myDB.getAllData("subject_details");
            String subjects[] = new String[50];
            int size=0;
            int flag=0;
            //***************************************************


            while(res.moveToNext())
            {
                if(res.getInt(0)==myDB.getcurrentsem()) {
                    int present = 0;
                    int absent = 0;
                    int min_percent = 0;
                    Cursor cur = myDB.getAllData("attendance");
                    int k = 0;
                    while (cur.moveToNext()) {
                        if (res.getInt(1) == cur.getInt(2))
                        {
                            k = 1;
                            min_percent = res.getInt(4);
                            if (cur.getString(4).equals("Present")) {
                                present++;
                            } else if (cur.getString(4).equals("Absent")) {
                                absent++;
                            }
                        }
                    }
                    if (k == 1)
                    {
                        if((present * 100 / (present + absent)) < min_percent)
                        {
                            flag=1;
                            subjects[size] =  myDB.getSubjectName(res.getInt(1))+"  "+ "Attendance : " +String.valueOf((present * 100 / (present + absent)))+"%"+"\n";
                            size++;
                        }
                    }

                }

            }





            //****************************************************

            if(flag==1)
            {

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.main_app_icon_big)
                                .setContentTitle("Low Attendance Alert.")
                                .setContentText("Click for details.").setPriority(2).setAutoCancel(true);


                String ringtonePreference = prefs.getString("testing", "DEFAULT_NOTIFICATION_URI");
                Uri uri = Uri.parse(ringtonePreference);
                mBuilder.setSound(uri);

                long[] pattern = {1000,1000,1000};

                if(prefs.getBoolean("events_notification_vibrate",false))
                {
                    mBuilder.setVibrate(pattern);
                }

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


//            inboxStyle.setBigContentTitle(intent.getStringExtra("Event_Type"));


//            if(intent.getStringExtra("Event_Type").equals(""))
//            {
//                inboxStyle.addLine(intent.getStringExtra("Subject"));
//            }
//            inboxStyle.addLine(intent.getStringExtra("Event_Name"));
//            inboxStyle.addLine(intent.getStringExtra("Event_Date"));
//            inboxStyle.addLine("Time : "+intent.getStringExtra("Start_Time")+" - "+intent.getStringExtra("End_Time"));
//            inboxStyle.addLine(intent.getStringExtra("Description"));

//                inboxStyle.addLine(subjects);

                for(int j=0;j<size;j++)
                {
                    inboxStyle.addLine(subjects[j]);
                }
                mBuilder.setStyle(inboxStyle);

// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(context, ViewFinalAttendance.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(ViewFinalAttendance.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                id,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

// mId allows you to update the notification later on.
                mNotificationManager.notify(id, mBuilder.build());
//            Toast.makeText(context, "Event Debug", Toast.LENGTH_SHORT).show();
//
            }
        }
        else
        {
//            Toast.makeText(context, "Debug low attendance.", Toast.LENGTH_SHORT).show();
        }

    }

}
