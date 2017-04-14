package com.example.adavi.academiccompanion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by pk on 4/14/2017.
 */

public class NotifyEventActivity extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, intent.getStringExtra("Event_Name"), Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Id = intent.getStringExtra("id");
        int id=9999;
        if(Id!=null)
        {
            id = Integer.parseInt(Id);
        }


        String rdate = intent.getStringExtra("Remainder_Date");
        String rtime = intent.getStringExtra("Remainder_Time");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        String cdate = df.format(c.getTime());
        String ctime = tf.format(c.getTime());



        if(prefs.getBoolean("events_notification",false)  && rdate.equals(cdate) && rtime.equals(ctime))
        {
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.main_app_icon_big)
                            .setContentTitle(intent.getStringExtra("Event_Type"))
                            .setContentText("Click for Event Details").setPriority(2);


            String ringtonePreference = prefs.getString("events_notification_ringtone", "DEFAULT_NOTIFICATION_URI");
            Uri uri = Uri.parse(ringtonePreference);
            mBuilder.setSound(uri);

            long[] pattern = {1000,1000,1000};

            if(prefs.getBoolean("events_notification_vibrate",false))
            {
                mBuilder.setVibrate(pattern);
            }

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


            inboxStyle.setBigContentTitle(intent.getStringExtra("Event_Type"));
            if(intent.getStringExtra("Event_Type").equals(""))
            {
                inboxStyle.addLine(intent.getStringExtra("Subject"));
            }
            inboxStyle.addLine(intent.getStringExtra("Event_Name"));
            inboxStyle.addLine(intent.getStringExtra("Event_Date"));
            inboxStyle.addLine("Time : "+intent.getStringExtra("Start_Time")+" - "+intent.getStringExtra("End_Time"));
            inboxStyle.addLine(intent.getStringExtra("Description"));


            mBuilder.setStyle(inboxStyle);

// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(context, TimeTableActivity.class);
            resultIntent.putExtra("button_event_id",intent.getStringExtra("id"));

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(TimeTableActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            id,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

// mId allows you to update the notification later on.
            mNotificationManager.notify(id, mBuilder.build());
            Toast.makeText(context, "Event Debug", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(context, intent.getStringExtra("Event_Name"), Toast.LENGTH_SHORT).show();
        }

    }

}
