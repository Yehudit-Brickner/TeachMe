package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.LessonDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Person;
import impl.Tutor;

public class MoreInfoAboutClassSearch extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public TextView classname;
    public TextView tutorname;
    public LinearLayout layoutlist;
    public Lesson mylesson;
    public String Lid;
    public String Tid;
    public  ArrayList<Meeting> myMeetings1;
    public Tutor mytutor;
    public View myview;
    public TextView date;
    public TextView starttime;
    public TextView endtime;
    public TextView iszoom;
    public TextView isinperson;
    public TextView price;
    public TextView acceptclass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_about_class_search);

        Log.d("AUTH_DEBUG","more info about class search\n\n");

        Intent intent=getIntent();
        Lid=intent.getStringExtra("LID");
        Tid=intent.getStringExtra("TID");
        Log.d("AUTH_DEBUG","LID= "+Lid+" TID= "+Tid);

        mylesson= LessonDB.getLessonFromDB(Tid,Lid);

        Log.d("AUTH_DEBUG","more info, "+mylesson.toString());
        mytutor= PersonDataDB.getTutorFromDB(Tid);

        classname=(TextView)findViewById(R.id.classname_moreinfo);;
        classname.setText(Lid);
        tutorname=(TextView)findViewById(R.id.tutorname_moreinfo);
        tutorname.setText(mytutor.getFirstName()+" "+mytutor.getLastName());

        mAuth = FirebaseAuth.getInstance();


        myMeetings1=mylesson.getMeetings();


        layoutlist=findViewById(R.id.moreinfo_linearlayout);
       if ( myMeetings1!=null) {
           for (int i = 0; i < myMeetings1.size(); i++) {
            if (myMeetings1.get(i).getStudentId()==null ||myMeetings1.get(i).getStudentId().equals("") )
               addView(myMeetings1.get(i));
           }
       }

    }

        public void addView(Meeting m){

            if (m.getStudentId()!="" || m.getStudentId()!=null) {

                myview = getLayoutInflater().inflate(R.layout.more_info_about_class_search_row, null, false);

                date = (TextView) myview.findViewById(R.id.date_cir);
                date.setText(m.getDateStart());

                starttime = (TextView) myview.findViewById(R.id.starttime_cir);
                starttime.setText(m.getTimeStart());

                endtime = (TextView) myview.findViewById(R.id.endtime_cir);
                endtime.setText(m.getTimeEnd());

                iszoom = (TextView) myview.findViewById(R.id.zoom_cir);
                String z = iszoom.getText().toString() + m.isZoom();
                iszoom.setText(z);

                isinperson = (TextView) myview.findViewById(R.id.inperson_cir);
                String p = isinperson.getText().toString() + m.isInPerson();
                isinperson.setText(p);

                price = (TextView) myview.findViewById(R.id.price_cir);
                price.setText(mylesson.getPrice());

                acceptclass = (TextView) myview.findViewById(R.id.signupToClass);

                acceptclass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        String UID = user.getUid();
                        Log.d("AUTH_DEBUG", "UID = " + UID);
                        m.setStudentId(UID);
                        LessonDB.setLessonData(mylesson);
                    }
                });
                layoutlist.addView(myview);
            }
    }

    private void updateUI(FirebaseUser user) {
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
                Intent i =new Intent(MoreInfoAboutClassSearch.this, StudentHomePage.class);
                startActivity(i);
        }
        return true;
    }
}