package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class FutureClassMoreInfoTutor extends AppCompatActivity {


    private Meeting m;
    private Student s;
    private Tutor t;
    private Lesson l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_class_more_info_tutor);

        Intent intent=getIntent();
        String MID = intent.getStringExtra("mID");

        m= MeetingDB.getMeeting(MID);
        String sId=m.getStudentId();
        if(sId!="") {
            s = PersonDataDB.getStudentFromDB(m.getStudentId());
        }
        t = PersonDataDB.getTutorFromDB(m.getTutorId());
        l = LessonDB.getLessonFromDB(t.getUID(),m.getLessonId());

        TextView classname =(TextView)findViewById(R.id.fcmi_classname);
        classname.setText(classname.getText().toString()+ m.getLessonId());

        TextView studentname =(TextView)findViewById(R.id.fcmi_studentname);
        if (sId!="") {
            studentname.setText(studentname.getText().toString() + s.getFirstName() + " " + s.getLastName());
        }
        TextView date =(TextView)findViewById(R.id.fcmi_date);
        date.setText(date.getText().toString()+ m.getDateStart());

        TextView starttime =(TextView)findViewById(R.id.fcmi_starttime);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        TextView endtime =(TextView)findViewById(R.id.fcmi_endtime);
        endtime.setText(endtime.getText().toString()+m.getTimeEnd() );

        TextView price =(TextView)findViewById(R.id.fcmi_price_per_hour);
        price.setText(price.getText().toString()+ l.getPrice());

        TextView zoom =(TextView)findViewById(R.id.fcmi_zoom);
        zoom.setText(zoom.getText().toString()+ String.valueOf(m.isZoom()));

        TextView inperson =(TextView)findViewById(R.id.fcmi_inperson);
        inperson.setText(inperson.getText().toString()+ String.valueOf(m.isInPerson()));

        TextView city =(TextView)findViewById(R.id.fcmi_city);
        city.setText(city.getText().toString()+ m.getCity());





        ImageButton whatsapp =(ImageButton)findViewById(R.id.whatsappbtn2);
        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
            }
        });


    }
}