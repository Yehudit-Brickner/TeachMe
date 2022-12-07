package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class StudentHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        ImageButton profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, MainActivity6.class);
                startActivity(i);
            }
        });


        Button search=(Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, Search.class);
                startActivity(i);
            }
        });

        Button upcoming=(Button) findViewById(R.id.upcoming);
        upcoming.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, MainActivity10.class);
                startActivity(i);
            }
        });


        Button signout=(Button) findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i =new Intent(StudentHomePage.this, NewLogin.class);
        startActivity(i);
    }
}