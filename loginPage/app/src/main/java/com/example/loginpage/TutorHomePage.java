package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class TutorHomePage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home_page);

        Button add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TutorHomePage.this, CreateClass.class);
                startActivity(i);
            }
        });

        ImageButton profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TutorHomePage.this, MainActivity9.class);
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
        Intent i =new Intent(TutorHomePage.this, NewLogin.class);
        startActivity(i);
    }
}