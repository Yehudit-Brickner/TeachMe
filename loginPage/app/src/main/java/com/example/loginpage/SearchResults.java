package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import controller.SearchController;
import db.LessonDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Tutor;


public class SearchResults extends AppCompatActivity {



    private LinearLayout layoutlist;
    private Switch myswitch;
    private ArrayList<Lesson> lessons;
    private String pickedclass;
    private String pickeddate;
    private String myNumber;
    private View myview;
    private TextView classname;
    private TextView tutorname;
    private TextView price;
    private Tutor t;
    private Button moreinfo;
    private ArrayList<String> info;

    private String startDate ;
    private String endDate ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Log.d("AUTH_DEBUG","in search results");


        Intent intent=getIntent();
        info = intent.getStringArrayListExtra("info");
        pickedclass=info.get(0);
        startDate = info.get(1);
        endDate = info.get(2);
        lessons = LessonDB.getLessonsByName(pickedclass, null, null);


//        lessons = LessonDB.getLessonsByName(pickedclass, startDate, endDate);


        layoutlist=findViewById(R.id.layout_list_src);
        myswitch=(Switch)findViewById(R.id.switch_sc);

        myswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLesson();
            }
        });
        showLesson();

    }





    public void addView(Lesson l){

        myview = getLayoutInflater().inflate(R.layout.row_search_results,null,false);

        classname= (TextView)myview.findViewById(R.id.ClassName_sr);
        classname.setText(l.getLessonId());

        tutorname=(TextView)myview.findViewById(R.id.TutorName_sr);
        if(l.getTutorId()!="") {
            t = PersonDataDB.getTutorFromDB(l.getTutorId());
            if (t != null) {
                tutorname.setText(t.getFirstName() + " " + t.getLastName());
            }
        }

        price=(TextView)myview.findViewById(R.id.price);
        price.setText(price.getText().toString()+l.getPrice());

        moreinfo=(Button) myview.findViewById(R.id.moreinfo_sr);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResults.this, MoreInfoAboutClassSearch.class);
                i.putExtra("LID", l.getLessonId());
                i.putExtra("TID",l.getTutorId());
                i.putExtra("startdate", startDate);
                i.putExtra("enddate",endDate);
                startActivity(i);
            }
        });
        layoutlist.addView(myview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.topmenu:
                Intent i =new Intent(SearchResults.this, StudentHomePage.class);
                startActivity(i);
        }
        return true;
    }


    public void showLesson(){
        SearchController.switchClicked(myswitch,lessons);
        for (int i=layoutlist.getChildCount()-1; i>=0;i--) {
            layoutlist.removeView(layoutlist.getChildAt(i));
        }
        for(int i=0; i<lessons.size();i++){
            addView(lessons.get(i));
        }
    }

}
