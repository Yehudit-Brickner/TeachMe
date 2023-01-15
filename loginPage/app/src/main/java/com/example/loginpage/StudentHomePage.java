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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import controller.PersonController;
import db.PersonDataDB;
import impl.Student;

public class StudentHomePage extends AppCompatActivity {

    private FirebaseFirestore firestore;
    public GoogleSignInOptions gso;
    public GoogleSignInClient gsc;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public ImageButton profile;
    public Button search;
    public Button passed;
    public Button upcoming;
    public Button signout;
    public String UID;
    public Student s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        TextView studentName = (TextView) findViewById(R.id.name);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();
//        s= PersonDataDB.getStudentFromDB(UID);
        s= PersonController.getStudent(UID);

        studentName.setText(studentName.getText().toString()+ s.getFirstName()+ " "+ s.getLastName());


        profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, StudentUpdateInfo.class);
                startActivity(i);
            }
        });


        search=(Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, Search.class);
                startActivity(i);
            }
        });

        upcoming=(Button) findViewById(R.id.upcoming);
        upcoming.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, FutureClassesStudent.class);
                startActivity(i);
            }
        });

        passed=(Button) findViewById(R.id.passed);
        passed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, PassedClassesStudent.class);
                startActivity(i);
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(StudentHomePage.this,gso);

        signout=(Button) findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i =new Intent(StudentHomePage.this, Login.class);
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