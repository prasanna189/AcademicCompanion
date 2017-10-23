package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class SettingsActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void backupNow(View view) {
        exportDB();
        Toast.makeText(getApplicationContext(), "Backup Successful",
                Toast.LENGTH_SHORT).show();
    }

    public void restoreNow(View view) {
        importDB();
        Toast.makeText(getApplicationContext(), "Restore Successful",
                Toast.LENGTH_SHORT).show();
    }


    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.example.adavi.academiccompanion"
                        + "//databases//" + "AcademicCompanion.db";
                String backupDBPath = "AcademicCompanion_backup.db"; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Import Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Import Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.example.adavi.academiccompanion"
                        + "//databases//" + myDB.getDBName();
                String backupDBPath = "AcademicCompanion_backup.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Backup Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

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
