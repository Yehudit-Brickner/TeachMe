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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import impl.Lesson;
import impl.Meeting;
import impl.Person;

public class MoreInfoAboutClassSearch extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private TextView classname;
    private TextView tutorname;
    public LinearLayout layoutlist;
    String Lid;
    String Tid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_about_class_search);

        Log.d("AUTH_DEBUG","more info about class search");

        Intent intent=getIntent();
        Lid=intent.getStringExtra("LID");
        Tid=intent.getStringExtra("TID");
        Log.d("AUTH_DEBUG","LID= "+Lid+" TID= "+Tid);

//        Lesson mylesson=getlesson(Lid,Tid)
//        Person mytutor=getTutor(Tid);

        classname=(TextView)findViewById(R.id.classname_moreinfo);
        classname.setText("myclass");
        //classname.setText(Lid)
        tutorname=(TextView)findViewById(R.id.tutorname_moreinfo);
        tutorname.setText("mytutor");
//        tutorname.setText(mytutor.getFirstName()+" "+mytutor.getLastName());
        mAuth = FirebaseAuth.getInstance();


        Date now=new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        cal.add(Calendar.DATE, -2);
        Date twodaysago=cal.getTime();
        Meeting meet1 = new Meeting("meetid1",String.valueOf(now),"12:00",String.valueOf(now),"14:00");
        Meeting meet2 = new Meeting("meetid2",String.valueOf(now),"15:00",String.valueOf(now),"17:00");
        Meeting meet3 = new Meeting("meetid3",String.valueOf(yesterday),"12:00",String.valueOf(now),"14:00");
        Meeting meet4 = new Meeting("meetid4",String.valueOf(yesterday),"15:00",String.valueOf(now),"17:00");
        Meeting meet5 = new Meeting("meetid5",String.valueOf(twodaysago),"12:00",String.valueOf(now),"14:00");
        Meeting meet6 = new Meeting("meetid6",String.valueOf(twodaysago),"15:00",String.valueOf(now),"17:00");


        ArrayList<Meeting> myMeetings=new ArrayList<Meeting>();
        myMeetings.add(meet4);
        myMeetings.add(meet5);
        myMeetings.add(meet6);
        myMeetings.add(meet1);
        myMeetings.add(meet2);
        myMeetings.add(meet3);



//        ArrayList<Meeting> myMeetings1=new ArrayList<Meeting>();
//        myMeetings1=mylesson.getMeetings();


        layoutlist=findViewById(R.id.moreinfo_linearlayout);
        for (int i=0; i< myMeetings.size(); i++){
//            if (myMeetings.get(i).getstudentid==null ||myMeetings.get(i).getstudentid.equals("") )
            addView(myMeetings.get(i));
        }

    }

        public void addView(Meeting m){

            View myview = getLayoutInflater().inflate(R.layout.more_info_about_class_search_row,null,false);

            TextView date = (TextView)myview.findViewById(R.id.date_cir);
            date.setText(m.getDateStart());

            TextView starttime = (TextView)myview.findViewById(R.id.starttime_cir);
            starttime.setText(m.getTimeStart());

            TextView endtime = (TextView)myview.findViewById(R.id.endtime_cir);
            endtime.setText(m.getTimeEnd());

            TextView iszoom = (TextView)myview.findViewById(R.id.zoom_cir);
            String z=iszoom.getText().toString()+"yes";
            iszoom.setText(z);

            TextView isinperson = (TextView)myview.findViewById(R.id.inperson_cir);
            String p=isinperson.getText().toString()+"yes";
            isinperson.setText(p);

            TextView price = (TextView)myview.findViewById(R.id.price_cir);
            price.setText("100");

            TextView acceptclass=(TextView) myview.findViewById(R.id.signupToClass);

            acceptclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    String UID=user.getUid();
                    Log.d("AUTH_DEBUG","UID = "+ UID);
//                    m.setstudentID(UID);
                }
            });
            layoutlist.addView(myview);
    }

    private void updateUI(FirebaseUser user) {
    }
}