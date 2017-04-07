package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewSubjectActivity extends AppCompatActivity {


    DatabaseHelper myDB;
    EditText editsname, editpname, editpemail,credits,minattendance,description;
    CheckBox lab;
    Button buttonSaveSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subject);
        myDB = new DatabaseHelper(this);

        editsname = (EditText) findViewById(R.id.sname_edittext);
        editpname = (EditText) findViewById(R.id.pname_edittext);
        editpemail = (EditText) findViewById(R.id.pemail_edittext);
        credits = (EditText) findViewById(R.id.credits_edittext);
        minattendance = (EditText) findViewById(R.id.MinimumAttendance_edittext);
        description = (EditText) findViewById(R.id.Description_edittext);
        buttonSaveSubject = (Button) findViewById(R.id.savesubject_button);
        lab=(CheckBox) findViewById(R.id.lab);

    }

    public void saveSubject(View view) {
     //   int sem=myDB.getcurrentsem();

//        boolean isInserted = myDB.insertData(editSname.getText().toString(), editTname.getText().toString(),
//                editTemail.getText().toString());

//        if (isInserted == true) {
//            Toast.makeText(AddNewSubjectActivity.this, "Subject Saved", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, DisplaySubjectsActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(AddNewSubjectActivity.this, "Subject not Saved", Toast.LENGTH_LONG).show();
//        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(this, DisplaySubjectsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void reset(View view) {
        Intent intent = new Intent(this, AddNewSubjectActivity.class);
        startActivity(intent);
    }


}
