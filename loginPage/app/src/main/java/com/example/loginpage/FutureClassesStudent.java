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

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import db.MeetingDB;
import db.PersonDataDB;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class FutureClassesStudent extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private LinearLayout layoutlist;
    private FirebaseUser user;
    private String UID;
    private ArrayList<Meeting> meetings;
    private Date date;
    private Timestamp now;
    private View myview;
    private TextView classname;
    private TextView tutorname;
    private TextView textdate;
    private TextView starttime;
    private TextView endtime;
    private Button moreinfo;
    private Tutor t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_classes_student);

        layoutlist=findViewById(R.id.layout_list);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();
        meetings= MeetingDB.getStudentMeetings(UID);

        date = Calendar.getInstance().getTime();
        now= new Timestamp(date);


        for (int i=0; i< meetings.size();i++){
            Timestamp t = meetings.get(i).getStartDateTime();
            if(now.compareTo(t)<0) {
                addView(meetings.get(i));
            }
        }


    }


    public void addView(Meeting m){
        myview = getLayoutInflater().inflate(R.layout.row_class_data_student,null,false);

        classname= (TextView)myview.findViewById(R.id.ClassName_rcds);
        classname.setText(m.getLessonId());

        tutorname= (TextView)myview.findViewById(R.id.TutorName_rcds);
        t= PersonDataDB.getTutorFromDB(m.getTutorId());
        tutorname.setText(t.getFirstName()+ " "+ t.getLastName());


        textdate= (TextView)myview.findViewById(R.id.Date_rcds);
        textdate.setText(m.getDateStart());

        starttime= (TextView)myview.findViewById(R.id.StartTime_rcds);
        starttime.setText(m.getTimeStart());

        endtime= (TextView)myview.findViewById(R.id.EndTime_rcds);
        endtime.setText(m.getTimeEnd());

        moreinfo=(Button)myview.findViewById(R.id.moreinfo_rcds);

        layoutlist.addView(myview);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(FutureClassesStudent.this, FutureClassMoreInfoStudent.class);
                i.putExtra("MID",m.getMeetingId());
                startActivity(i);
            }
        });
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
                Intent i =new Intent(FutureClassesStudent.this, StudentHomePage.class);
                startActivity(i);
        }
        return true;
    }

}