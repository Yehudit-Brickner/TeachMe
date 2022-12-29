package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class FutureClassesTutor extends AppCompatActivity {

    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    public LinearLayout layoutlist;
    public FirebaseUser user;
    public String UID;
    public ArrayList<Meeting> meetings;
    public Date date;
    public Timestamp now;
    public View myview;
    public TextView classname;
    public TextView studentname;
    public TextView textdate;
    public TextView starttime;
    public TextView endtime;
    public Button moreinfo;
    public Student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_classes_tutor);

        layoutlist=findViewById(R.id.layout_list);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();
        meetings= MeetingDB.getTutorMeetings(UID);

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
        myview = getLayoutInflater().inflate(R.layout.row_class_data_tutor,null,false);

        classname= (TextView)myview.findViewById(R.id.ClassName_rcdt);
        classname.setText(m.getLessonId());

        studentname= (TextView)myview.findViewById(R.id.StudentName_rcdt);
        if(m.getStudentId()!="" && m.getStudentId()!=null) {
            s = PersonDataDB.getStudentFromDB((m.getStudentId()));
            if (s != null) {
                studentname.setText(s.getFirstName() + " " + s.getLastName());
            }
        }


        textdate= (TextView)myview.findViewById(R.id.Date_rcdt);
        textdate.setText(m.getDateStart());

        starttime= (TextView)myview.findViewById(R.id.StartTime_rcdt);
        starttime.setText(m.getTimeStart());

        endtime= (TextView)myview.findViewById(R.id.EndTime_rcdt);
        endtime.setText(m.getTimeEnd());

        moreinfo=(Button)myview.findViewById(R.id.moreinfo_rcdt);

        layoutlist.addView(myview);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(FutureClassesTutor.this, FutureClassMoreInfoTutor.class);
                i.putExtra("mID",m.getMeetingId());
                startActivity(i);
            }
        });
    }


}