package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import controller.LessonMeetingController;
import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class PastClassMoreInfoTutor extends AppCompatActivity {


    private Intent intent;
    private String MID;
    private String LID;
    private String TID;
    private Meeting m;
    private Lesson l;
    private Tutor t;
    private Student s;
    private TextView classname;
    private TextView studentname;
    private TextView date;
    private TextView starttime;
    private TextView endtime;
    private TextView price;
    private TextView zoom;
    private TextView inperson;
    private TextView city;
    private EditText summary;
    private Button updateSummary;
    private ImageButton whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_class_more_info_tutor);

        intent=getIntent();
        MID = intent.getStringExtra("mID");
        TID = intent.getStringExtra("tID");
        LID = intent.getStringExtra("lID");
        m= LessonMeetingController.getMeeting(TID,LID,MID);
        t= LessonMeetingController.getTutor(TID);
        l= LessonMeetingController.getLesson(TID,LID);



        if (!m.getStudentId().equals("") ) {
            s= LessonMeetingController.getStudent(m.getStudentId());
        }


        classname =(TextView)findViewById(R.id.pcmi_classname);
        classname.setText(classname.getText().toString()+l.getLessonId() );

        studentname =(TextView)findViewById(R.id.pcmi_studentname);
        if (s!=null) {
            studentname.setText(studentname.getText().toString() + s.getFirstName() + " " + s.getLastName());
        }

        date =(TextView)findViewById(R.id.pcmi_date);
        date.setText(date.getText().toString()+m.getDateStart() );

        starttime =(TextView)findViewById(R.id.pcmi_starttime);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        endtime =(TextView)findViewById(R.id.pcmi_endtime);
        endtime.setText(endtime.getText().toString()+m.getTimeEnd() );

        price =(TextView)findViewById(R.id.pcmi_price_per_hour);
        price.setText(price.getText().toString()+l.getPrice() );

        zoom =(TextView)findViewById(R.id.pcmi_zoom);
        zoom.setText(zoom.getText().toString()+ String.valueOf(m.isZoom()));

        inperson =(TextView)findViewById(R.id.pcmi_inperson);
        inperson.setText(inperson.getText().toString()+ String.valueOf(m.isInPerson()));

        city =(TextView)findViewById(R.id.pcmi_city);
        city.setText(city.getText().toString()+ m.getCity());


        summary= (EditText) findViewById(R.id.pcmi_class_summaryE);
        summary.setText(m.getSummary());

        updateSummary=(Button) findViewById(R.id.updatesummmary);
        updateSummary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                m.setSummary(summary.getText().toString());
//                LessonDB.setLessonData(l);
               if(LessonMeetingController.updateMeeting(m)) {
                   Toast.makeText(getApplicationContext(), "summary updated", Toast.LENGTH_LONG).show();
               }
               else{
                   Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
               }
            }
        });

        whatsapp =(ImageButton)findViewById(R.id.whatsappbtn1);
        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
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
                Intent i =new Intent(PastClassMoreInfoTutor.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }
}