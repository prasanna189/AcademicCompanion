package com.example.adavi.academiccompanion;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ViewResults extends AppCompatActivity {

    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
        displayResultsHelper();
        myDB=new DatabaseHelper(this);
    }

    public void displayResultsHelper()
    {
        Cursor res=myDB.getAllData("subject_details");
        int flag=0;

        while(res.moveToNext())
        {
            int g=Integer.parseInt(res.getString(7));
            if(res.getInt(1)==myDB.getcurrentsem() && res.getString(5).equals("completed") && g!=0)
            {
                //displayResults(res.getInt(0),res.getInt(1),res.getString(7));
            }
        }

    }

    public void displayResults(int semid,int sub){
        LinearLayout subjectLL = (LinearLayout) findViewById(R.id.View_Results);

        LinearLayout ll=new LinearLayout(this);



    }

}
