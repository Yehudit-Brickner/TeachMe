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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_class_more_info_tutor);

        Intent intent=getIntent();
        String MID=intent.getStringExtra("mID");

        Meeting m= MeetingDB.getMeeting(MID);
        Tutor t = PersonDataDB.getTutorFromDB(m.getTutorId());
        Student s = PersonDataDB.getStudentFromDB(m.getStudentId());
        Lesson l= LessonDB.getLessonFromDB(t.getUID(),m.getLessonId());

        TextView classname =(TextView)findViewById(R.id.pcmi_classname);
        classname.setText(classname.getText().toString()+l.getLessonId() );

        TextView studentname =(TextView)findViewById(R.id.pcmi_studentname);
        studentname.setText(studentname.getText().toString()+s.getFirstName()+" "+s.getLastName() );

        TextView date =(TextView)findViewById(R.id.pcmi_date);
        date.setText(date.getText().toString()+m.getDateStart() );

        TextView starttime =(TextView)findViewById(R.id.pcmi_starttime);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        TextView endtime =(TextView)findViewById(R.id.pcmi_endtime);
        endtime.setText(endtime.getText().toString()+m.getTimeEnd() );

        TextView price =(TextView)findViewById(R.id.pcmi_price_per_hour);
        price.setText(price.getText().toString()+l.getPrice() );

        TextView zoom =(TextView)findViewById(R.id.pcmi_zoom);
        price.setText(price.getText().toString()+ String.valueOf(m.isZoom()));

        TextView inperson =(TextView)findViewById(R.id.pcmi_inperson);
        price.setText(price.getText().toString()+ String.valueOf(m.isInPerson()));

        TextView city =(TextView)findViewById(R.id.pcmi_city);
        price.setText(price.getText().toString()+ m.getCity());




        EditText summary= (EditText) findViewById(R.id.pcmi_class_summaryE);

        Button updateSummary=(Button) findViewById(R.id.updatesummmary);
        updateSummary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               // meeting.setsummary(summary.getText.toString());
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