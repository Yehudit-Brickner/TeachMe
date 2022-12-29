package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.LessonDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Tutor;


public class SearchResults extends AppCompatActivity {



    public LinearLayout layoutlist;
    public ArrayList<Lesson> lessons;
    public String pickedclass;
    public String pickeddate;
    public String myNumber;
    public View myview;
    public TextView classname;
    public TextView tutorname;
    public Tutor t;
    public Button moreinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Log.d("AUTH_DEBUG","in search results");

        Intent intent=getIntent();
        myNumber=intent.getStringExtra("num");
        Log.d("AUTH_DEBUG","myNumber= " +myNumber);
        if (myNumber.equals("1")){
            pickedclass = intent.getStringExtra("class");
            Log.d("AUTH_DEBUG","search results: mynumber= "+myNumber+" class = "+ pickedclass);
            lessons = LessonDB.getLessonsByName(pickedclass);
        }
        else if( myNumber.equals("2")){
            pickedclass=intent.getStringExtra("class");
            pickeddate=intent.getStringExtra("date");
            Log.d("AUTH_DEBUG","search results: mynumber= "+myNumber+" class = "+ pickedclass+", date = "+pickeddate);
            lessons = LessonDB.getLessonsByName(pickedclass);
        }
        else{
            Log.d("AUTH_DEBUG","myNumber= " +myNumber);
        }

        layoutlist=findViewById(R.id.layout_list_src);

        for(int i=0; i<lessons.size();i++){
            Log.d("AUTH_DEBUG",lessons.get(i).toString());
            addView(lessons.get(i));
        }

    }





    public void addView(Lesson l){

        myview = getLayoutInflater().inflate(R.layout.row_search_results,null,false);

        classname= (TextView)myview.findViewById(R.id.ClassName_sr);
        classname.setText(l.getLessonId());

        tutorname=(TextView)myview.findViewById(R.id.TutorName_sr);
        t= PersonDataDB.getTutorFromDB(l.getTutorId());
        tutorname.setText(t.getFirstName()+" "+t.getLastName());

        moreinfo=(Button) myview.findViewById(R.id.moreinfo_sr);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResults.this, MoreInfoAboutClassSearch.class);
                i.putExtra("LID", l.getLessonId());
                i.putExtra("TID",l.getTutorId());
                startActivity(i);
            }
        });
        layoutlist.addView(myview);
    }
}
