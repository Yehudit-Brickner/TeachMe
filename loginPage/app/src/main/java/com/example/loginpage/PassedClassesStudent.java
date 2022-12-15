package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
        View myview = getLayoutInflater().inflate(R.layout.row_class_data2,null,false);

        TextView cn= (TextView)myview.findViewById(R.id.ClassName_rcd2);
        cn.setText(m.getMeetingId());
        TextView tn= (TextView)myview.findViewById(R.id.TutorName_rcd2);
        TextView date= (TextView)myview.findViewById(R.id.Date_rcd2);
        date.setText(m.getDateStart());

        TextView st= (TextView)myview.findViewById(R.id.StartTime_rcd2);
        st.setText(m.getTimeStart());

        TextView et= (TextView)myview.findViewById(R.id.EndTime_rcd2);
        et.setText(m.getTimeEnd());
        ImageButton moreinfo=(ImageButton)myview.findViewById(R.id.moreinfo_rcd2);
//        System.out.println(m.getDateStart()+"\n"+m.getDateEnd()+"\n"+m.getTimeStart()+
//                "\n"+m.getTimeEnd()+"\n\n");

        layoutlist.addView(myview);
    }
}