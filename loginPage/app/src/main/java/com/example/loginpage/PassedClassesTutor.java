package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Date;

import controller.PastFutureClassesController;
import db.PersonDataDB;
import impl.Meeting;
import impl.Student;

public class PassedClassesTutor extends AppCompatActivity {



    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private LinearLayout layoutlist;
    private Switch myswitch;
    private FirebaseUser user;
    private String UID;
    private ArrayList<Meeting> meetings;
    private ArrayList<Meeting> passedMeetings;
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
        setContentView(R.layout.activity_passed_classes_tutor);

        layoutlist=findViewById(R.id.layout_list);
        myswitch=(Switch)findViewById(R.id.switch_pt);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();

        meetings= PastFutureClassesController.getTutorMeetings(UID);
        passedMeetings= PastFutureClassesController.getPastMeetings(meetings);

        myswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMeeting();
            }
        });
        showMeeting();

    }

    public void addView(Meeting m){

        myview = getLayoutInflater().inflate(R.layout.row_class_data_tutor,null,false);

        classname= (TextView)myview.findViewById(R.id.ClassName_rcdt);
        classname.setText(m.getLessonId());

        studentname= (TextView)myview.findViewById(R.id.StudentName_rcdt);
        if(m.getStudentId()!="" && m.getStudentId()!=null) {
            s = PersonDataDB.getStudentFromDB(m.getStudentId());
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
                Intent i =new Intent(PassedClassesTutor.this, PastClassMoreInfoTutor.class);
                i.putExtra("mID",m.getMeetingId());
                i.putExtra("lID",m.getLessonId());
                i.putExtra("tID",m.getTutorId());
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
                Intent i =new Intent(PassedClassesTutor.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }


    public void showMeeting(){
        PastFutureClassesController.switchclicked(myswitch,passedMeetings,-1);
        for (int i=layoutlist.getChildCount()-1; i>=0;i--) {
            layoutlist.removeView(layoutlist.getChildAt(i));
        }
        for (int i=0; i<passedMeetings.size(); i++){
            addView(passedMeetings.get(i));
        }
    }


}