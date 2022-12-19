package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private TextView classname;
    private TextView tutorname;
    public LinearLayout layoutlist;
    public Lesson mylesson;
    String Lid;
    String Tid;
    public  ArrayList<Meeting> myMeetings1;

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
        Tutor mytutor= PersonDataDB.getTutorFromDB(Tid);

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

                View myview = getLayoutInflater().inflate(R.layout.more_info_about_class_search_row, null, false);

                TextView date = (TextView) myview.findViewById(R.id.date_cir);
                date.setText(m.getDateStart());

                TextView starttime = (TextView) myview.findViewById(R.id.starttime_cir);
                starttime.setText(m.getTimeStart());

                TextView endtime = (TextView) myview.findViewById(R.id.endtime_cir);
                endtime.setText(m.getTimeEnd());

                TextView iszoom = (TextView) myview.findViewById(R.id.zoom_cir);
                String z = iszoom.getText().toString() + m.getZoom();
                iszoom.setText(z);

                TextView isinperson = (TextView) myview.findViewById(R.id.inperson_cir);
                String p = isinperson.getText().toString() + m.getInperson();
                isinperson.setText(p);

                TextView price = (TextView) myview.findViewById(R.id.price_cir);
                price.setText(mylesson.getPrice());

                TextView acceptclass = (TextView) myview.findViewById(R.id.signupToClass);

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
}