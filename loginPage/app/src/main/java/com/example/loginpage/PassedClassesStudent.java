package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import impl.Meeting;

public class PassedClassesStudent extends AppCompatActivity {




    public LinearLayout layoutlist;
//    public TextView cn;
//    public TextView tn;
//    public TextView date;
//    public TextView st;
//    public TextView et;
//    public ImageButton moreinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passed_classes_student);



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


        Collections.sort(myMeetings);





        layoutlist=findViewById(R.id.layout_list);
        for (int i=0; i<myMeetings.size();i++){
            Log.d("AUTH_DEBUG", myMeetings.get(i).getMeetingId()+myMeetings.get(i).getDateStart()+myMeetings.get(i).getTimeStart()+myMeetings.get(i).getDateEnd()+myMeetings.get(i).getTimeEnd());
            addView(myMeetings.get(i));
        }


    }

    public void addView(Meeting m){
        View myview = getLayoutInflater().inflate(R.layout.row_class_data_student,null,false);

        TextView cn= (TextView)myview.findViewById(R.id.ClassName_rcds);
        cn.setText(m.getMeetingId());
        TextView tn= (TextView)myview.findViewById(R.id.TutorName_rcds);
        TextView date= (TextView)myview.findViewById(R.id.Date_rcds);
        date.setText(m.getDateStart());

        TextView st= (TextView)myview.findViewById(R.id.StartTime_rcds);
        st.setText(m.getTimeStart());

        TextView et= (TextView)myview.findViewById(R.id.EndTime_rcds);
        et.setText(m.getTimeEnd());
        Button moreinfo=(Button)myview.findViewById(R.id.moreinfo_rcds);


        layoutlist.addView(myview);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PassedClassesStudent.this, PastClassMoreInfo.class);
//                i.putExtra("mID",MID);
//                i.putExtra("Lid",LID);
                startActivity(i);
            }
        });
    }
}