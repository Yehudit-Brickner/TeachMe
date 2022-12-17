package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreInfoAboutClassSearchOld extends AppCompatActivity {

    private TextView classname;
    private TextView tutorname;
    public LinearLayout layoutlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_about_class_search_old);

        Log.d("AUTH_DEBUG","got to here");
        classname=(TextView)findViewById(R.id.classname_moreinfo);
        classname.setText("myclass");
        tutorname=(TextView)findViewById(R.id.tutorname_moreinfo);
        tutorname.setText("mytutor");
//
//        Date now=new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
//        Date yesterday = cal.getTime();
//        cal.add(Calendar.DATE, -2);
//        Date twodaysago=cal.getTime();
//        Meeting meet1 = new Meeting("meetid1",String.valueOf(now),"12:00",String.valueOf(now),"14:00");
//        Meeting meet2 = new Meeting("meetid2",String.valueOf(now),"15:00",String.valueOf(now),"17:00");
//        Meeting meet3 = new Meeting("meetid3",String.valueOf(yesterday),"12:00",String.valueOf(now),"14:00");
//        Meeting meet4 = new Meeting("meetid4",String.valueOf(yesterday),"15:00",String.valueOf(now),"17:00");
//        Meeting meet5 = new Meeting("meetid5",String.valueOf(twodaysago),"12:00",String.valueOf(now),"14:00");
//        Meeting meet6 = new Meeting("meetid6",String.valueOf(twodaysago),"15:00",String.valueOf(now),"17:00");
//
//        ArrayList<Meeting> myMeetings=new ArrayList<Meeting>();
//        myMeetings.add(meet4);
//        myMeetings.add(meet5);
//        myMeetings.add(meet6);
//        myMeetings.add(meet1);
//        myMeetings.add(meet2);
//        myMeetings.add(meet3);
//
//        ArrayList<Lesson> results=new ArrayList<Lesson>();
//        results.add( new Lesson("l1",myMeetings));
//        results.add(  new Lesson("l2",myMeetings));
//        results.add(  new Lesson("l3",myMeetings));
//        results.add(  new Lesson("l4",myMeetings));
//        results.add(  new Lesson("l5",myMeetings));
//        results.add(  new Lesson("l6",myMeetings));
//        results.add(  new Lesson("l7",myMeetings));
//        results.add(  new Lesson("l8",myMeetings));

//        layoutlist=findViewById(R.id.moreinfo_linearlayout);
//        for (int i=0; i< results.get(0).getMeetings().size(); i++){
//            addView(results.get(0).getMeetings().get(i));
//        }






    }



//    public void addView(Meeting m){
//
//        View myview = getLayoutInflater().inflate(R.layout.more_info_about_class_search_row,null,false);
//
//        TextView date = (TextView)myview.findViewById(R.id.date_cir);
//        date.setText(m.getDateStart());
//
//        TextView starttime = (TextView)myview.findViewById(R.id.starttime_cir);
//        starttime.setText(m.getTimeStart());
//
//        TextView endtime = (TextView)myview.findViewById(R.id.endtime_cir);
//        date.setText(m.getTimeEnd());
//
//
//
//        TextView iszoom = (TextView)myview.findViewById(R.id.zoom_cir);
//        String z=iszoom.getText().toString()+"yes";
//        iszoom.setText(z);
//
//
//        TextView isinperson = (TextView)myview.findViewById(R.id.inperson_cir);
//        String p=iszoom.getText().toString()+"yes";
//        isinperson.setText(p);
//
//        TextView price = (TextView)myview.findViewById(R.id.price_cir);
//        price.setText("100");
//
//
//
//        Button acceptclass=(Button) myview.findViewById(R.id.signupToClass);
//
//        layoutlist.addView(myview);
//
//
//        acceptclass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }


}