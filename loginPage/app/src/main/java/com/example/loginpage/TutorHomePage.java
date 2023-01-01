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

import db.PersonDataDB;
import impl.Tutor;

public class TutorHomePage extends AppCompatActivity {


    public GoogleSignInOptions gso;
    public GoogleSignInClient gsc;
    public GoogleSignInAccount acct;
    public FirebaseFirestore firestore;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public ImageButton profile;
    public Button add;
    public Button pastclasses;
    public Button futureclasses;
    public Button signout;
    public String UID;
    public Tutor t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home_page);




        TextView tutorname=(TextView)findViewById(R.id.name);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UID=user.getUid();
        t= PersonDataDB.getTutorFromDB(UID);

        tutorname.setText(tutorname.getText().toString()+ t.getFirstName()+ " "+ t.getLastName());


        profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button profile - tutor");
                Intent i =new Intent(TutorHomePage.this, TutorUpdateInfo.class);
                startActivity(i);
            }
        });

        add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button add");
                Intent i =new Intent(TutorHomePage.this, AddClass.class);
                startActivity(i);
            }
        });

        pastclasses=(Button) findViewById(R.id.passed);
        pastclasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button passed classes - tutor");
                Intent i =new Intent(TutorHomePage.this, PassedClassesTutor.class);
                startActivity(i);
            }
        });

        futureclasses=(Button) findViewById(R.id.upcoming);
        futureclasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG","pressed button future classes - tutor");
                Intent i =new Intent(TutorHomePage.this, FutureClassesTutor.class);
                startActivity(i);
            }
        });


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(TutorHomePage.this,gso);


        signout=(Button) findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AUTH_DEBUG","pressed button signout- tutor");
                signOut();
                Intent i =new Intent(TutorHomePage.this, Login.class);
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