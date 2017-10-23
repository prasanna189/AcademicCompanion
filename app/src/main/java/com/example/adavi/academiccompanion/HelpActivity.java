package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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

    public void sendQuery(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] strTo = {"academic.companion.adavi@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, strTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Help for Academic Companion App");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        startActivity(intent);
    }
}
