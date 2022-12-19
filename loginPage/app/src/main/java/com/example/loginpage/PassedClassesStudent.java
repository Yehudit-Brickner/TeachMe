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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import db.MeetingDB;
import db.PersonDataDB;
import impl.Meeting;
import impl.Tutor;

public class PassedClassesStudent extends AppCompatActivity {

    public LinearLayout layoutlist;
    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passed_classes_student);

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
//            if (meetings.get(i).getDateStart() ,today)
            addView(meetings.get(i));
        }

    }

    public void addView(Meeting m){
        View myview = getLayoutInflater().inflate(R.layout.row_class_data_student,null,false);

        TextView cn= (TextView)myview.findViewById(R.id.ClassName_rcds);
        cn.setText(m.getLessonId());

        TextView tn= (TextView)myview.findViewById(R.id.TutorName_rcds);
        Tutor t= PersonDataDB.getTutorFromDB(m.getTutorId());
        tn.setText(t.getFirstName()+ " "+ t.getLastName());


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
                Intent i =new Intent(PassedClassesStudent.this, PastClassMoreInfoStudent.class);
                i.putExtra("mID",m.getMeetingId());
                startActivity(i);
            }
        });
    }
}