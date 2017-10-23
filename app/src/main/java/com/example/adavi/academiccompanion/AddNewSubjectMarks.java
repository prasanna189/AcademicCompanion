package com.example.adavi.academiccompanion;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewSubjectMarks extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText examtype, maxmarks, marks;
    Button buttonSaveMarks;
    String s, e_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subject_marks);
        myDB = new DatabaseHelper(this);

        setTitle("Add Exam");

        examtype = (EditText) findViewById(R.id.examtype_edittext);
        maxmarks = (EditText) findViewById(R.id.maxmarks_edittext);
        marks = (EditText) findViewById(R.id.marksobtained_edittext);
        s = getIntent().getStringExtra("subject_id");
        buttonSaveMarks = (Button) findViewById(R.id.save_marks_button);


        displayMarks();
    }

    public void displayMarks() {

        String s = getIntent().getStringExtra("subj_id");
        String e_type = getIntent().getStringExtra("exam_type");
        if (s != null) {
            Cursor res = myDB.getAllData("marks");
            while (res.moveToNext()) {
                if (res.getString(1).equals(s) && res.getString(2).equals(e_type)) {
                    examtype.setText(e_type);

                    int mark = res.getInt(3);
                    String str1 = Integer.toString(mark);
                    marks.setText(str1);
                    mark = res.getInt(4);
                    str1 = Integer.toString(mark);
                    maxmarks.setText(str1);
                    buttonSaveMarks.setText("Update");

                }
            }
        }
    }

    public void save(View view) {
        int flag = 0;
        //String s=getIntent().getStringExtra("subj_id");
        String sname = examtype.getText().toString();
        // Toast.makeText(AddNewSubjectMarks.this,s, Toast.LENGTH_SHORT).show();


        Cursor res = myDB.getAllData("marks");
        while (res.moveToNext() && flag == 0) {
            if (res.getString(2).equals(sname) && res.getInt(0) == myDB.getcurrentsem() && res.getString(1).equals(s)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            SaveMarks(view);
        } else {
            Toast.makeText(AddNewSubjectMarks.this, "Exam Already Exits", Toast.LENGTH_SHORT).show();
        }
    }

    public void SaveMarks(View view) {
        int checkerr = 0;
        if (examtype.getText().toString().equals("")) {
            checkerr = 1;
            examtype.setError("Please Enter Examtype");
        }
        if (maxmarks.getText().toString().equals("")) {
            checkerr = 1;
            maxmarks.setError("Please Enter Max Marks");
        }
        if (marks.getText().toString().equals("")) {
            checkerr = 1;
            marks.setError("Please Enter Marks");
        }
        if (checkerr == 0) {
            int a = Integer.parseInt(marks.getText().toString());
            int b = Integer.parseInt(maxmarks.getText().toString());
            a = a - b;
            if (a > 0) {
                checkerr = 1;
                Toast.makeText(AddNewSubjectMarks.this, "Entered Marks should be less than or equal to max marks", Toast.LENGTH_SHORT).show();

            }
        }
        //

        if (checkerr == 0) {
            String s = getIntent().getStringExtra("subj_id");
            String e_type = getIntent().getStringExtra("exam_type");
            //Toast.makeText(AddNewSubjectMarks.this, "hello", Toast.LENGTH_SHORT).show();

            if (s != null) {
                int flag = 0;
                Cursor res = myDB.getAllData("marks");
                String exam_type = examtype.getText().toString();
                String mm = maxmarks.getText().toString();
                String mar = marks.getText().toString();
                int maxmar = Integer.parseInt(mm);
                int mark = Integer.parseInt(mar);


                boolean j = false;

                while (res.moveToNext()) {

                    if (res.getInt(1) == Integer.parseInt(s) && res.getString(2).equals(e_type)) {

                        j = myDB.updateDataMarks(res.getInt(0), res.getInt(1), res.getString(2), mark, maxmar, exam_type);
                    }
                }

                if (j) {

                    Toast.makeText(AddNewSubjectMarks.this, "Marks Details Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, DisplayExamDetails.class);
                    intent.putExtra("subid", s);
                    intent.putExtra("examtype", exam_type);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddNewSubjectMarks.this, "Marks Updation Failed", Toast.LENGTH_SHORT).show();
                }

            } else {
                String sub = getIntent().getStringExtra("subject_id");
                int sem = myDB.getcurrentsem();

                boolean isInserted = myDB.insertDataMarks(sem, Integer.parseInt(sub), examtype.getText().toString(), Integer.parseInt(marks.getText().toString()), Integer.parseInt(maxmarks.getText().toString()));

                if (isInserted == true) {
                    Toast.makeText(AddNewSubjectMarks.this, "Marks Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, SubjectMarks.class);
                    intent.putExtra("sub_id", sub);

                    startActivity(intent);
                } else {
                    Toast.makeText(AddNewSubjectMarks.this, "Subject not Saved", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }


    public void resetmarks(View view) {
        Intent intent = new Intent(this, AddNewSubjectMarks.class);
        startActivity(intent);
    }
}
