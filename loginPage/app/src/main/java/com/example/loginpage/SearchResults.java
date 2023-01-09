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



    private LinearLayout layoutlist;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Log.d("AUTH_DEBUG","in search results");


        Intent intent=getIntent();
        info = intent.getStringArrayListExtra("info");
        pickedclass=info.get(0);
        lessons = LessonDB.getLessonsByName(pickedclass);


//        myNumber=intent.getStringExtra("num");
//        Log.d("AUTH_DEBUG","myNumber= " +myNumber);
//        if (myNumber.equals("1")){
//            pickedclass = intent.getStringExtra("class");
//            Log.d("AUTH_DEBUG","search results: mynumber= "+myNumber+" class = "+ pickedclass);
//            lessons = LessonDB.getLessonsByName(pickedclass);
//        }
//        else if( myNumber.equals("2")){
//            pickedclass=intent.getStringExtra("class");
//            pickeddate=intent.getStringExtra("date");
//            Log.d("AUTH_DEBUG","search results: mynumber= "+myNumber+" class = "+ pickedclass+", date = "+pickeddate);
//            lessons = LessonDB.getLessonsByName(pickedclass);
//        }
//        else{
//            Log.d("AUTH_DEBUG","myNumber= " +myNumber);
//        }

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

        price=(TextView)myview.findViewById(R.id.price);
        price.setText(l.getPrice());

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
}
