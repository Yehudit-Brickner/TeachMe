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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import controller.LessonMeetingController;
import impl.Lesson;
import impl.Meeting;

public class AddLesson extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button create;
    private boolean error=false;
    private boolean error_notified=false;
    private boolean createLesson=false;

    private Lesson l;
    private EditText classname;
    private EditText price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);


        classname = (EditText) findViewById(R.id.editclass);
        price = (EditText) findViewById(R.id.Eprice);

        create = (Button) findViewById(R.id.createbtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressedCreate();
            }
        });



    }


    public void pressedCreate() {
        Log.d("AUTH_DEBUG", "pressed button create");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String UID = user.getUid();
        error = false;
        error_notified=false;
        classname = (EditText) findViewById(R.id.editclass);
        price = (EditText) findViewById(R.id.Eprice);

        if (classname.getText().toString().equals("") || price.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "please fill in class name and price", Toast.LENGTH_LONG).show();
        }
        else {
            l= LessonMeetingController.getLesson(UID, classname.getText().toString());
            if (l==null || !l.getLessonId().equals(classname.getText().toString())) {
                l = new Lesson(classname.getText().toString(), UID, price.getText().toString(), "");
                LessonMeetingController.createLesson(l);
                Intent i =new Intent(AddLesson.this, TutorHomePage.class);
                startActivity(i);
            }
        }



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
                Intent i =new Intent(AddLesson.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }


}