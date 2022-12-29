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
import impl.Tutor;

public class PassedClassesStudent extends AppCompatActivity {

    public LinearLayout layoutlist;
    public FirebaseFirestore firestore;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public String UID;
    public ArrayList<Meeting> meetings;
    Date date;
    String today;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passed_classes_student);

        layoutlist=findViewById(R.id.layout_list);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();
        meetings= MeetingDB.getStudentMeetings(UID);

        Log.d("AUTH_DEBUG",meetings.toString());

        date = Calendar.getInstance().getTime();
        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        today = formatter.format(date);


        for (int i=0; i< meetings.size();i++){
            try {
                if(dateisgood(today, meetings.get(i).getDateStart())){
                    addView(meetings.get(i));
                }
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void addView(Meeting m){
        View myview = getLayoutInflater().inflate(R.layout.row_class_data_student,null,false);

        TextView classname= (TextView)myview.findViewById(R.id.ClassName_rcds);
        classname.setText(m.getLessonId());

        TextView tutorname= (TextView)myview.findViewById(R.id.TutorName_rcds);
        Tutor t= PersonDataDB.getTutorFromDB(m.getTutorId());
        tutorname.setText(t.getFirstName()+ " "+ t.getLastName());

        TextView date= (TextView)myview.findViewById(R.id.Date_rcds);
        date.setText(m.getDateStart());

        TextView starttime= (TextView)myview.findViewById(R.id.StartTime_rcds);
        starttime.setText(m.getTimeStart());

        TextView endtime= (TextView)myview.findViewById(R.id.EndTime_rcds);
        endtime.setText(m.getTimeEnd());

        Button moreinfo=(Button)myview.findViewById(R.id.moreinfo_rcds);

        layoutlist.addView(myview);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PassedClassesStudent.this, PastClassMoreInfoStudent.class);
                i.putExtra("mID",m.getMeetingId());
                startActivity(i);
            }
        });
    }

    public boolean dateisgood(String today, String other) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdf.parse(today);
        Date d2=sdf.parse(other);
        if (d1.before(d2)) {
            return false;
        } else if (d1.after(d2)) {
            return true;
        } else {
            return true;
        }
    }
}