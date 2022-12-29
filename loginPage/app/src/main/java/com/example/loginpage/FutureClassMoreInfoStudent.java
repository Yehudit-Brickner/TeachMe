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
import impl.Tutor;

public class FutureClassMoreInfoStudent extends AppCompatActivity {


    public Intent intent;
    public String MID;
    public Meeting m;
    public Tutor t;
    public Lesson l;
    public TextView classname;
    public TextView tutorname;
    public TextView date;
    public TextView starttime;
    public TextView endtime;
    public TextView price;
    public TextView zoom;
    public TextView inperson;
    public TextView city;
    public ImageButton whatsapp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_class_more_info_student);

        intent=getIntent();
        MID = intent.getStringExtra("MID");

        m= MeetingDB.getMeeting(MID);
        t = PersonDataDB.getTutorFromDB(m.getTutorId());
        l= LessonDB.getLessonFromDB(t.getUID(),m.getLessonId());


        classname =(TextView)findViewById(R.id.fcmi_classname);
        classname.setText(classname.getText().toString()+m.getLessonId());

        tutorname =(TextView)findViewById(R.id.fcmi_classname);
        tutorname.setText(tutorname.getText().toString()+t.getFirstName()+ " "+t.getLastName() );

        date =(TextView)findViewById(R.id.fcmi_classname);
        date.setText(date.getText().toString()+ m.getDateStart());

        starttime =(TextView)findViewById(R.id.fcmi_classname);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        endtime =(TextView)findViewById(R.id.fcmi_classname);
        endtime.setText(endtime.getText().toString()+ m.getTimeEnd() );

        price =(TextView)findViewById(R.id.fcmi_classname);
        price.setText(price.getText().toString()+ l.getPrice() );


        zoom =(TextView)findViewById(R.id.fcmi_zoom);
        zoom.setText(zoom.getText().toString()+ String.valueOf(m.isZoom()));

        inperson =(TextView)findViewById(R.id.fcmi_inperson);
        inperson.setText(inperson.getText().toString()+ String.valueOf(m.isInPerson()));

        city =(TextView)findViewById(R.id.fcmi_city);
        city.setText(city.getText().toString()+ m.getCity());


        whatsapp =(ImageButton)findViewById(R.id.whatsappbtn2);

        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
            }
        });

    }
}