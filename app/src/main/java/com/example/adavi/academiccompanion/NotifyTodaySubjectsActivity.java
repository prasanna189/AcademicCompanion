package com.example.adavi.academiccompanion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

/**
 * Created by pk on 4/13/2017.
 */

public class NotifyTodaySubjectsActivity extends BroadcastReceiver {

    DatabaseHelper myDB;

    @Override
    public void onReceive(Context context, Intent intent) {

        myDB = new DatabaseHelper(context);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String time = prefs.getString("notification_time",null);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String t1 = df.format(c.getTime());


        int hours = 7, minutes=0;
        String[] parts;
        String part1="", part2="";
        String t="";
        if(time != null)
        {
            parts = time.split(":");
            part1 = parts[0];
            part2 = parts[1];
            hours=Integer.parseInt(part1);
            minutes=Integer.parseInt(part2);
            if(hours<10)
            {
                part1 = "0"+String.valueOf(hours);
            }

            if(minutes<10)
            {
                part2 = "0"+String.valueOf(minutes);
            }
        }

        t = part1+":"+part2;

        if(prefs.getBoolean("notifications_new_message",false)  && t.equals(t1))
        {
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.main_app_icon_big)
                            .setContentTitle("Today's Classes")
                            .setContentText("Click to expand!").setPriority(2).setAutoCancel(true);


            String ringtonePreference = prefs.getString("notifications_new_message_ringtone", "DEFAULT_NOTIFICATION_URI");
            Uri uri = Uri.parse(ringtonePreference);
            mBuilder.setSound(uri);

            long[] pattern = {1000,1000,1000};

            if(prefs.getBoolean("notifications_new_message_vibrate",false))
            {
                mBuilder.setVibrate(pattern);
            }

            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();

            String[] events = myDB.getTodayClasses();

            if(events.length != 0)
            {
                inboxStyle.setBigContentTitle("Today's Classes are : ");

                for (int i=0; i < events.length; i++) {
                    inboxStyle.addLine(events[i]);
                }
            }
            else
            {
                inboxStyle.setBigContentTitle("No Classes Today.");
                inboxStyle.addLine("Take a break.");
            }


            mBuilder.setStyle(inboxStyle);

// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(context, TimeTableActivity.class);

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
                            1000,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

// mId allows you to update the notification later on.
            mNotificationManager.notify(1000, mBuilder.build());
//            Toast.makeText(context, "Debug: "+prefs.getString("notification_time", "what"), Toast.LENGTH_SHORT).show();

        }
        else
        {
//            Toast.makeText(context, "Debug: No notification", Toast.LENGTH_SHORT).show();
        }

    }

}
