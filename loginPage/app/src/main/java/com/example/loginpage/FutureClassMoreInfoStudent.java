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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_class_more_info_student);

        Intent intent=getIntent();
        String MID = intent.getStringExtra("MID");

        Meeting m= MeetingDB.getMeeting(MID);
        Tutor t = PersonDataDB.getTutorFromDB(m.getTutorId());
        Lesson l= LessonDB.getLessonFromDB(t.getUID(),m.getLessonId());


        TextView classname =(TextView)findViewById(R.id.fcmi_classname);
        classname.setText(classname.getText().toString()+m.getLessonId());

        TextView tutorname =(TextView)findViewById(R.id.fcmi_classname);
                tutorname.setText(tutorname.getText().toString()+t.getFirstName()+ " "+t.getLastName() );

        TextView date =(TextView)findViewById(R.id.fcmi_classname);
                date.setText(date.getText().toString()+ m.getDateStart());

        TextView starttime =(TextView)findViewById(R.id.fcmi_classname);
                starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        TextView endtime =(TextView)findViewById(R.id.fcmi_classname);
                endtime.setText(endtime.getText().toString()+ m.getTimeEnd() );

        TextView price =(TextView)findViewById(R.id.fcmi_classname);
                price.setText(price.getText().toString()+ l.getPrice() );


        TextView zoom =(TextView)findViewById(R.id.fcmi_zoom);
        price.setText(zoom.getText().toString()+ String.valueOf(m.isZoom()));

        TextView inperson =(TextView)findViewById(R.id.fcmi_inperson);
        price.setText(inperson.getText().toString()+ String.valueOf(m.isInPerson()));

        TextView city =(TextView)findViewById(R.id.fcmi_city);
        price.setText(city.getText().toString()+ m.getCity());


        ImageButton whatsapp =(ImageButton)findViewById(R.id.whatsappbtn2);

        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
            }
        });

    }
}