package com.example.adavi.academiccompanion;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewResults extends AppCompatActivity {

    DatabaseHelper myDB;
    int flag=0;
    LinearLayout subjectLL1;
    LinearLayout subjectLL2;
    LinearLayout subjectLL3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
        myDB=new DatabaseHelper(this);

        subjectLL1 = (LinearLayout) findViewById(R.id.View_Results_1);
        subjectLL2 = (LinearLayout) findViewById(R.id.View_Results_2);
        subjectLL3 = (LinearLayout) findViewById(R.id.View_Results_3);
        displayResultsHelper();

    }

    public void displayResultsHelper()
    {
        Cursor res = myDB.getAllData("subject_details");
        int flag=0;

        while(res.moveToNext() && flag==0)
        {
            String  g=res.getString(7);
            if(res.getInt(0)==myDB.getcurrentsem() &&  g.equals("0"))
            {
                flag=1;
            }

        }
        res = myDB.getAllData("subject_details");
        int c=0;
        while(res.moveToNext() && flag==0)
        {
            String  g=res.getString(7);
            if(res.getInt(0)==myDB.getcurrentsem())
            {
                c+=1;
            }

        }


        if(flag==1 || c==0)
        {
            noresultAlert("No Result","Results Yet to be finalised");
        }
        else
        {
           displayResults(myDB.getcurrentsem());
        }

    }
    public void noresultAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void displayResults(int semid){

        TextView t1 = new TextView(this);
        t1.setText("SUBJECT");
        TextView t2 = new TextView(this);
        t2.setText("MARKS");
        TextView t3 = new TextView(this);
        t3.setText("GRADE");

        subjectLL1.addView(t1);
        subjectLL2.addView(t2);
        subjectLL3.addView(t3);

        Cursor res=myDB.getAllData("subject_details");
        while(res.moveToNext())
        {
            if(myDB.getcurrentsem()==res.getInt(0))
            {
                display(myDB.getSubjectName(res.getInt(1)),myDB.gettotalmarks(res.getInt(0),res.getInt(1)),res.getString(7));

            }
        }

    }

    public void display(String sname,int marks,String grade)
    {

        TextView t1 = new TextView(this);
        t1.setText(sname);
        TextView t2 = new TextView(this);
        t2.setText(Integer.toString(marks));
        TextView t3 = new TextView(this);
        t3.setText(grade);


        t1.setPadding(10,10,50,20);

        t2.setPadding(10,10,50,20);

        t2.setPadding(10,10,50,20);

        subjectLL1.addView(t1);
        subjectLL2.addView(t2);
        subjectLL3.addView(t3);


    }

}
