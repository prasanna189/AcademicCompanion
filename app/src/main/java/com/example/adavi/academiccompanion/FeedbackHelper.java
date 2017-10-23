package com.example.adavi.academiccompanion;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


//Created by pk on 8/1/2017.

class FeedbackHelper {

    private static final String EMAIL_ADDRESS = "academic.companion.adavi@gmail.com";

    public static void sendFeedback(Context context) {

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("application/image");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, getFeedbackEmailAddress());
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getFeedbackEmailSubject(context));
        emailIntent.putExtra(Intent.EXTRA_TEXT, getEmailText(context));

        //code for attaching a debug file
        File debugFile = null;
        String filename = "debug.txt";
        String filepath = "";//stores files in app directory without creating additional directories

        if (!isExternalStorageWritable()) {
            Toast.makeText(context, "Unable to attach Debug Info", Toast.LENGTH_SHORT).show();
        } else {

            debugFile = new File(context.getExternalFilesDir(filepath), filename);

            try {
                FileOutputStream fos = new FileOutputStream(debugFile);
                fos.write(getDeviceInfo(context).getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(debugFile));
        context.startActivity(Intent.createChooser(emailIntent, "Please share your feedback through GMAIL"));
//        debugFile.delete();
    }

    /* Checks if external storage is available for read and write */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static String[] getFeedbackEmailAddress() {
        return new String[]{EMAIL_ADDRESS};
    }

    private static String getFeedbackEmailSubject(Context context) {
        return getApplicationName(context) + " Feedback v" + getAppVersion(context);
    }

    private static String getApplicationName(Context context) {
        return context.getString(context.getApplicationInfo().labelRes);
    }

    private static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "vX.XX";
        }
    }

    private static String getEmailText(Context context) {
        String emailMessage = "";
        emailMessage += "Your feedback or issue here : \n\n\n _____________ \n\n Debug file helps with app issues.\n";
        return emailMessage;
    }

    //returns info about the device as a string
    private static String getDeviceInfo(Context context) {
        String deviceInfo = "";
        deviceInfo += "Bootloader: " + Build.BOOTLOADER
                + "\nBrand: " + Build.BRAND
                + "\nDevice: " + Build.DEVICE
                + "\nBuild: " + Build.DISPLAY
                + "\nHardware: " + Build.HARDWARE
                + "\nManufacturer, Model: " + Build.MANUFACTURER + " " + Build.MODEL
                + "\nScreen Density: " + getDeviceDensity(context)
                + "\nVersion, SDK:" + Build.VERSION.RELEASE + " , " + Build.VERSION.SDK_INT
                + "\nCodename, Incremental: " + Build.VERSION.CODENAME + " , " + Build.VERSION.INCREMENTAL
                + "\nProduct: " + Build.PRODUCT
                + "\nBoard: " + Build.BOARD
                + "\nRadio Version: " + Build.getRadioVersion();
        return deviceInfo;
    }

    private static float getDeviceDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
