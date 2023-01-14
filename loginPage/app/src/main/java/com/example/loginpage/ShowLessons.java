package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import controller.LessonMeetingController;
import db.LessonDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;

public class ShowLessons extends AppCompatActivity {



    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UID;

    private LinearLayout layoutlist;

    private ArrayList<Lesson> mylessons;
    private Button addMeetings;
    private TextView lessonName;
    private TextView price;
    private View myview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lessons);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID = user.getUid();

        layoutlist = findViewById(R.id.layout_list);
//        mylessons = LessonDB.getLessonsByTutorId(UID);
        mylessons= LessonMeetingController.getLessonbyTutor(UID);
        showlessons();



    }


    public void showlessons(){
        for (int i=layoutlist.getChildCount()-1; i>=0;i--) {
            layoutlist.removeView(layoutlist.getChildAt(i));
        }
        for (int i=0; i<mylessons.size(); i++){
            addView(mylessons.get(i));
        }
    }
    public void addView(Lesson l){
        myview = getLayoutInflater().inflate(R.layout.lesssons_names,null,false);
        price= (TextView)myview.findViewById(R.id.price);
        price.setText(price.getText().toString()+ " "+ l.getPrice());
        lessonName= (TextView)myview.findViewById(R.id.ClassName_sr);
        lessonName.setText(l.getLessonId());
        addMeetings=(Button)myview.findViewById(R.id.addMeetings);

        layoutlist.addView(myview);
        addMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ShowLessons.this, AddMeetings.class);
                i.putExtra("lID",l.getLessonId());
                i.putExtra("TID",UID);
                startActivity(i);
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
                Intent i =new Intent(ShowLessons.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }
}