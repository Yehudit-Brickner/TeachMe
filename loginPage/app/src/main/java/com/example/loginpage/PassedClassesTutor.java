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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import db.MeetingDB;
import db.PersonDataDB;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class PassedClassesTutor extends AppCompatActivity {


    public LinearLayout layoutlist;
    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passed_classes_tutor);

        layoutlist=findViewById(R.id.layout_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID=user.getUid();
        ArrayList<Meeting> meetings= MeetingDB.getStudentMeetings(UID);

        Date date = Calendar.getInstance().getTime();
        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);


        for (int i=0; i< meetings.size();i++){
            try {
                if(dateisgood(today, meetings.get(i).getDateStart())){
                    addView(meetings.get(i));
                }
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void addView(Meeting m){
        View myview = getLayoutInflater().inflate(R.layout.row_class_data_tutor,null,false);

        TextView cn= (TextView)myview.findViewById(R.id.ClassName_rcdt);
        cn.setText(m.getMeetingId());

        TextView sn= (TextView)myview.findViewById(R.id.StudentName_rcdt);
        Student s= PersonDataDB.getStudentFromDB(m.getStudentId());
        sn.setText(s.getFirstName()+ " "+ s.getLastName());

        TextView date= (TextView)myview.findViewById(R.id.Date_rcdt);
        date.setText(m.getDateStart());

        TextView st= (TextView)myview.findViewById(R.id.StartTime_rcdt);
        st.setText(m.getTimeStart());

        TextView et= (TextView)myview.findViewById(R.id.EndTime_rcdt);
        et.setText(m.getTimeEnd());

        Button moreinfo=(Button)myview.findViewById(R.id.moreinfo_rcdt);


        layoutlist.addView(myview);

        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PassedClassesTutor.this, PastClassMoreInfoTutor.class);
                i.putExtra("mID",m.getMeetingId());
                startActivity(i);
            }
        });
    }


    public boolean dateisgood(String today, String other) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdf.parse(today);
        Date d2=sdf.parse(other);
        if (d1.before(d2)) {
            return false;
        } else if (d1.after(d2)) {
            return true;
        } else {
            return true;
        }
    }



}