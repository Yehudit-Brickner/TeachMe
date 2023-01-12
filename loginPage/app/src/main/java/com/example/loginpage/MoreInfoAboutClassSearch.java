package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import connection.HttpManager;
import db.LessonDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Person;
import impl.Tutor;

public class MoreInfoAboutClassSearch extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView classname;
    private TextView tutorname;
    private LinearLayout layoutlist;
    private Lesson mylesson;
    private String Lid;
    private String Tid;
    private  ArrayList<Meeting> myMeetings1;
    private Tutor mytutor;
    private View myview;
    private TextView date;
    private TextView starttime;
    private TextView endtime;
    private TextView iszoom;
    private TextView isinperson;
    private TextView price;
    private TextView acceptclass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_about_class_search);

        Log.d("AUTH_DEBUG","more info about class search\n\n");

        Intent intent=getIntent();
        Lid=intent.getStringExtra("LID");
        Tid=intent.getStringExtra("TID");
        Log.d("AUTH_DEBUG","LID= "+Lid+" TID= "+Tid);

        mylesson= LessonDB.getLessonFromDB(Tid,Lid);

        Log.d("AUTH_DEBUG","more info, "+mylesson.toString());
        mytutor= PersonDataDB.getTutorFromDB(Tid);

        classname=(TextView)findViewById(R.id.classname_moreinfo);;
        classname.setText(Lid);
        tutorname=(TextView)findViewById(R.id.tutorname_moreinfo);
        tutorname.setText(mytutor.getFirstName()+" "+mytutor.getLastName());

        mAuth = FirebaseAuth.getInstance();

        myMeetings1=mylesson.getMeetings();

//        Map<String,String> mymap=new HashMap<>();
//        mymap.put("lessonId",Lid);
//        mymap.put("tutorId",Tid);
//        HttpManager h=new HttpManager();
//        try {
//            h= HttpManager.GetRequest("http://10.0.0.42:9090/getMeetings",mymap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        myMeetings1=(ArrayList<Meeting>) h.getData();

        layoutlist=findViewById(R.id.moreinfo_linearlayout);
        showClasses();

    }

    public void addView(Meeting m){
        if (m.getStudentId()!="" || m.getStudentId()!=null) {

            myview = getLayoutInflater().inflate(R.layout.more_info_about_class_search_row, null, false);

            date = (TextView) myview.findViewById(R.id.date_cir);
            date.setText(m.getDateStart());

            starttime = (TextView) myview.findViewById(R.id.starttime_cir);
            starttime.setText(m.getTimeStart());

            endtime = (TextView) myview.findViewById(R.id.endtime_cir);
            endtime.setText(m.getTimeEnd());

            iszoom = (TextView) myview.findViewById(R.id.zoom_cir);
            String z = iszoom.getText().toString() + m.isZoom();
            iszoom.setText(z);

            isinperson = (TextView) myview.findViewById(R.id.inperson_cir);
            String p = isinperson.getText().toString() + m.isInPerson();
            isinperson.setText(p);

            price = (TextView) myview.findViewById(R.id.price_cir);

            price.setText(price.getText().toString()+ mylesson.getPrice());

            acceptclass = (TextView) myview.findViewById(R.id.signupToClass);

            acceptclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    String UID = user.getUid();
                    Log.d("AUTH_DEBUG", "UID = " + UID);
                    m.setStudentId(UID);
                    LessonDB.setLessonData(mylesson);
                    Toast.makeText(getApplicationContext(), "you are signed up!", Toast.LENGTH_LONG).show();
                    reshowClasses();
                }
            });
            layoutlist.addView(myview);
        }

    }

    private void updateUI(FirebaseUser user) {
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
                Intent i =new Intent(MoreInfoAboutClassSearch.this, StudentHomePage.class);
                startActivity(i);
        }
        return true;
    }

    public void showClasses(){
        Collections.sort(myMeetings1, new Comparator<Meeting>(){
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
        int count=0;
        if( myMeetings1!=null) {
            for (int i = 0; i < myMeetings1.size(); i++) {
                if (myMeetings1.get(i).getStudentId().equals("")) {
                    count++;
                    addView(myMeetings1.get(i));
                }
            }
        }
        if (count==0){
            Toast.makeText(getApplicationContext(), "no meetings to show", Toast.LENGTH_LONG).show();
        }
    }

    public void reshowClasses(){
        for (int i=layoutlist.getChildCount()-1; i>=0;i--) {
            layoutlist.removeView(layoutlist.getChildAt(i));
        }
        showClasses();
    }
}