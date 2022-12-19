package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomePage extends AppCompatActivity {

    private FirebaseFirestore firestore;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(StudentHomePage.this,gso);

        TextView studentName = (TextView) findViewById(R.id.name);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String s=studentName.getText().toString()+personName;
            studentName.setText(s);
        }



        ImageButton profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button profile - student");
                Intent i =new Intent(StudentHomePage.this, StudentUpdateInfo.class);

                startActivity(i);
            }
        });


        Button search=(Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button search");

                Intent i =new Intent(StudentHomePage.this, Search.class);
                startActivity(i);
            }
        });

        Button upcoming=(Button) findViewById(R.id.upcoming);
        upcoming.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button upcoming classes - student");
                Intent i =new Intent(StudentHomePage.this, FutureClassesStudent.class);

                startActivity(i);
            }
        });
        
        Button passed=(Button) findViewById(R.id.passed);
        passed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button passed classes - student");
                Intent i =new Intent(StudentHomePage.this, PassedClassesStudent.class);
                startActivity(i);
            }
        });




        Button signout=(Button) findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AUTH_DEBUG","pressed button signout- student");
                signOut();
                Intent i =new Intent(StudentHomePage.this, NewLogin.class);
                startActivity(i);

            }
        });
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
            }
        });

    }
}