package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import db.LessonDB;
import db.MeetingDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class PastClassMoreInfoTutor extends AppCompatActivity {

    public Meeting m;
    public Lesson l;
    public Tutor t;
    public Student s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_class_more_info_tutor);

        Intent intent=getIntent();
        String MID=intent.getStringExtra("mID");

         m= MeetingDB.getMeeting(MID);
         t = PersonDataDB.getTutorFromDB(m.getTutorId());
         l= LessonDB.getLessonFromDB(t.getUID(),m.getLessonId());

        if (m.getStudentId()!="" && m.getStudentId()!=null) {
            s = PersonDataDB.getStudentFromDB(m.getStudentId());
        }


        TextView classname =(TextView)findViewById(R.id.pcmi_classname);
        classname.setText(classname.getText().toString()+l.getLessonId() );

        TextView studentname =(TextView)findViewById(R.id.pcmi_studentname);
        if (s!=null) {
            studentname.setText(studentname.getText().toString() + s.getFirstName() + " " + s.getLastName());
        }

        TextView date =(TextView)findViewById(R.id.pcmi_date);
        date.setText(date.getText().toString()+m.getDateStart() );

        TextView starttime =(TextView)findViewById(R.id.pcmi_starttime);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        TextView endtime =(TextView)findViewById(R.id.pcmi_endtime);
        endtime.setText(endtime.getText().toString()+m.getTimeEnd() );

        TextView price =(TextView)findViewById(R.id.pcmi_price_per_hour);
        price.setText(price.getText().toString()+l.getPrice() );

        TextView zoom =(TextView)findViewById(R.id.pcmi_zoom);
        zoom.setText(zoom.getText().toString()+ String.valueOf(m.isZoom()));

        TextView inperson =(TextView)findViewById(R.id.pcmi_inperson);
        inperson.setText(inperson.getText().toString()+ String.valueOf(m.isInPerson()));

        TextView city =(TextView)findViewById(R.id.pcmi_city);
        city.setText(city.getText().toString()+ m.getCity());


        EditText summary= (EditText) findViewById(R.id.pcmi_class_summaryE);
        summary.setText(m.getSummary());

        Button updateSummary=(Button) findViewById(R.id.updatesummmary);
        updateSummary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                m.setSummary(summary.getText().toString());
                LessonDB.setLessonData(l);
                Toast.makeText(getApplicationContext(), "summary updated", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton whatsapp =(ImageButton)findViewById(R.id.whatsappbtn1);
        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
            }
        });

    }
}