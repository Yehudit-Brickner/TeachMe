package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import db.LessonDB;
import db.MeetingDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class FutureClassMoreInfoTutor extends AppCompatActivity {


    private Intent intent;
    private String MID;
    private String sId;
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
    private ImageButton whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_class_more_info_tutor);

        intent=getIntent();
        MID = intent.getStringExtra("mID");

        m= MeetingDB.getMeeting(MID);
        sId=m.getStudentId();
        if(sId!="") {
            s = PersonDataDB.getStudentFromDB(m.getStudentId());
        }
        t = PersonDataDB.getTutorFromDB(m.getTutorId());
        l = LessonDB.getLessonFromDB(t.getUID(),m.getLessonId());

        classname =(TextView)findViewById(R.id.fcmi_classname);
        classname.setText(classname.getText().toString()+ m.getLessonId());

        studentname =(TextView)findViewById(R.id.fcmi_studentname);
        if (sId!="") {
            studentname.setText(studentname.getText().toString() + s.getFirstName() + " " + s.getLastName());
        }
        date =(TextView)findViewById(R.id.fcmi_date);
        date.setText(date.getText().toString()+ m.getDateStart());

        starttime =(TextView)findViewById(R.id.fcmi_starttime);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        endtime =(TextView)findViewById(R.id.fcmi_endtime);
        endtime.setText(endtime.getText().toString()+m.getTimeEnd() );

        price =(TextView)findViewById(R.id.fcmi_price_per_hour);
        price.setText(price.getText().toString()+ l.getPrice());

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
//                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
                boolean installed=isAppInstaled("com.whatsapp");

                if(installed){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    String num="+972549761411";
                    String text="hello world";
                    sendIntent.setData(Uri.parse("http://whatsapp.com/send?phone="+num+"&text="+text));
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "sorry, you don't have whatsapp", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public boolean isAppInstaled(String s){
        PackageManager packageManager = getPackageManager();
        boolean isInstalled;
        try{
            packageManager.getPackageInfo(s,packageManager.GET_ACTIVITIES);
            isInstalled=true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled=false;
            e.printStackTrace();
        }
    return isInstalled;
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
                Intent i =new Intent(FutureClassMoreInfoTutor.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }
}