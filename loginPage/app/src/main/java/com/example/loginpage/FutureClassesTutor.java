package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import db.MeetingDB;
import db.PersonDataDB;
import impl.Meeting;
import impl.Student;

public class FutureClassesTutor extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private LinearLayout layoutlist;
    private Switch myswitch;
    private FirebaseUser user;
    private String UID;
    private ArrayList<Meeting> meetings;
    private ArrayList<Meeting> futureMeetings;
    private Date date;
    private Timestamp now;
    private View myview;
    private TextView classname;
    private TextView studentname;
    private TextView textdate;
    private TextView starttime;
    private TextView endtime;
    private Button moreinfo;
    private Student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_classes_tutor);

        layoutlist=findViewById(R.id.layout_list);
        myswitch=(Switch)findViewById(R.id.switch_ft);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();
        meetings= MeetingDB.getTutorMeetings(UID);


        myswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myswitch.isChecked()){
                    showMeetingsByClass();
                    Log.d("AUTH_DEBUG","switch is on");
                }
                else{
                    showMeetingsByDate();
                    Log.d("AUTH_DEBUG","switch is off");
                }
            }
        });

        futureMeetings=new ArrayList<>();
        date = Calendar.getInstance().getTime();
        now= new Timestamp(date);
        for (int i=0; i< meetings.size();i++){
            Timestamp t = meetings.get(i).getStartDateTime();
            if(now.compareTo(t)<=0) {
                futureMeetings.add(meetings.get(i));
            }
        }
        showMeetingsByDate();

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
                Intent i =new Intent(FutureClassesTutor.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }

    public void showMeetingsByDate(){
        for (int i=layoutlist.getChildCount()-1; i>=0;i--) {
            layoutlist.removeView(layoutlist.getChildAt(i));
        }
        Collections.sort(futureMeetings, new Comparator<Meeting>(){
            public int compare(Meeting m1, Meeting m2){
                Timestamp t1=m1.getStartDateTime();
                Timestamp t2=m2.getStartDateTime();
                if(t1.compareTo(t2)<0)
                    return -1;
                else{
                    return 1;
                }
            }
        });
        for (int i=0; i<futureMeetings.size(); i++){
            addView(futureMeetings.get(i));
        }
    }

    public void showMeetingsByClass(){
        for (int i=layoutlist.getChildCount()-1; i>=0;i--) {
            layoutlist.removeView(layoutlist.getChildAt(i));
        }
        Collections.sort(futureMeetings, new Comparator<Meeting>(){
            public int compare(Meeting m1, Meeting m2){
                if(m1.getLessonId().compareTo(m2.getLessonId())<0)
                    return -1;
                else{
                    return 1;
                }
            }
        });
        for (int i=0; i<futureMeetings.size(); i++){
            addView(futureMeetings.get(i));
        }
    }
}