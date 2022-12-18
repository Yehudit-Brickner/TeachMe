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

import impl.Lesson;
import impl.Meeting;
import impl.Tutor;


public class SearchResults extends AppCompatActivity {



    public LinearLayout layoutlist;
    public ArrayList<Lesson> lessons;
    public String pickedclass;
    public String pickeddate;
    public String myNumber;
    @SuppressLint("MissingInflatedId")
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
//            lessons = searchForClassesDate(pickedclass);
        }
        else if( myNumber.equals("2")){
            pickedclass=intent.getStringExtra("class");
           pickeddate=intent.getStringExtra("date");
            Log.d("AUTH_DEBUG","search results: mynumber= "+myNumber+" class = "+ pickedclass+", date = "+pickeddate);
//            lessons = searchForClassesDate(pickedclass,pickeddate);
        }
        else{
            Log.d("AUTH_DEBUG","myNumber= " +myNumber);
        }


        Date now=new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        cal.add(Calendar.DATE, -2);
        Date twodaysago=cal.getTime();
        Meeting meet1 = new Meeting("meetid1",String.valueOf(now),"12:00",String.valueOf(now),"14:00");
        Meeting meet2 = new Meeting("meetid2",String.valueOf(now),"15:00",String.valueOf(now),"17:00");
        Meeting meet3 = new Meeting("meetid3",String.valueOf(yesterday),"12:00",String.valueOf(now),"14:00");
        Meeting meet4 = new Meeting("meetid4",String.valueOf(yesterday),"15:00",String.valueOf(now),"17:00");
        Meeting meet5 = new Meeting("meetid5",String.valueOf(twodaysago),"12:00",String.valueOf(now),"14:00");
        Meeting meet6 = new Meeting("meetid6",String.valueOf(twodaysago),"15:00",String.valueOf(now),"17:00");


        ArrayList<Meeting> myMeetings=new ArrayList<Meeting>();
        myMeetings.add(meet4);
        myMeetings.add(meet5);
        myMeetings.add(meet6);
        myMeetings.add(meet1);
        myMeetings.add(meet2);
        myMeetings.add(meet3);



        ArrayList<Lesson> results=new ArrayList<Lesson>();
        results.add( new Lesson("l1","tid1","100","",myMeetings));
        results.add(  new Lesson("l2","tid2","80","",myMeetings));
        results.add(  new Lesson("l3","tid3","60","",myMeetings));
        results.add(  new Lesson("l4","tid4","110","",myMeetings));
        results.add(  new Lesson("l5","tid5","120","",myMeetings));
        results.add(  new Lesson("l6","tid6","90","",myMeetings));
        results.add(  new Lesson("l7","tid7","70","",myMeetings));
        results.add(  new Lesson("l8","tid8","50","",myMeetings));

        layoutlist=findViewById(R.id.layout_list_src);
        for (int i=0; i< results.size(); i++){
            addView(results.get(i));
        }

//        for(int i=0; i<lessons.size();i++){
//            addView(lessons.get(i));
//        }

    }





    public void addView(Lesson l){
        View myview = getLayoutInflater().inflate(R.layout.row_search_results,null,false);
        TextView cn= (TextView)myview.findViewById(R.id.ClassName_sr);
        cn.setText(l.getLessonId());
        TextView tn=(TextView)myview.findViewById(R.id.TutorName_sr);
//        Tutor t=getTutor(tn);
//        tn.setText(t.getFirstName()+" "+t.getLastName());
        Button moreinfo=(Button) myview.findViewById(R.id.moreinfo_sr);
        layoutlist.addView(myview);


        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResults.this, MoreInfoAboutClassSearch.class);
                i.putExtra("LID", l.getLessonId());
                i.putExtra("TID",l.getTutorId());
                startActivity(i);
            }
        });

    }
}
