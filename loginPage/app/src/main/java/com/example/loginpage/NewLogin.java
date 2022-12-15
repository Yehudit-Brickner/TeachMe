package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

import db.DataCenterDB;
import db.LessonDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;

public class NewLogin extends AppCompatActivity {

    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private Button login;
    private Button forgotpassword;
    private Button signup;


    private int type=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);


        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        radiobtnS=(RadioButton)findViewById(R.id.radioButtonS) ;
        radiobtnT=(RadioButton)findViewById(R.id.radioButtonT) ;
        login=(Button)findViewById(R.id.loginbtn);
        forgotpassword=(Button)findViewById(R.id.forgotpassbtn);
        signup=(Button)findViewById(R.id.signupbtn);


        TextView email= (TextView) findViewById(R.id.email);
        TextView password= (TextView) findViewById(R.id.password);

        // try place
        try_things();



        radiobtnS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (type!=0) {
                    radiobtnT.setChecked(false);
                    type = 0;
                    Log.d("AUTH_DEBUG", "not student to student");
                }
                else{
                    radiobtnS.setChecked(false);
                    type=-1;
                    Log.d("AUTH_DEBUG", "student to not student");
                }
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (type!=1) {
                    radiobtnS.setChecked(false);
                    type = 1;
                    Log.d("AUTH_DEBUG", "not tutor to tutor");
                }
                else{
                    radiobtnT.setChecked(false);
                    type=-1;
                    Log.d("AUTH_DEBUG", "tutor to not tutor");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed on login");
                if (type>=0) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("AUTH_DEBUG", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (!user.isEmailVerified())
                                        {
                                            Toast.makeText(getApplicationContext(), "Please vertify your email.", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            return;
                                        }
                                        updateUI(user);
                                        if (type==0){
                                            Intent i = new Intent(NewLogin.this, StudentHomePage.class);
                                            startActivity(i);
                                        }
                                        else{
                                            Intent i = new Intent(NewLogin.this, TutorHomePage.class);
                                            startActivity(i);
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("AUTH_DEBUG", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }

                            });

                }
                else{
                    Toast.makeText(getApplicationContext(), "you need to choose student or tutor", Toast.LENGTH_SHORT).show();
                }

//                System.out.println("type="+type);
//                if (type==0) {
//                    System.out.println(type);
//                    Task<QuerySnapshot> u = firestore.collection("users").get(Source.valueOf(username.getText().toString()));
//
//
//                    if (username.getText().toString().equals("admins") && password.getText().toString().equals("admins")) {
//                        Intent i = new Intent(NewLogin.this, StudentHomePage.class);
//                        startActivity(i);
//                    }
//                }
//                else if( type==1){
//                    if (username.getText().toString().equals("admint") && password.getText().toString().equals("admint")) {
//                        Intent i = new Intent(NewLogin.this, TutorHomePage.class);
//                        startActivity(i);
//                    }
//                }
//                else{
//                    System.out.println("something went wrong");
//                }
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed on fotgotPassword");
                Intent i = new Intent(NewLogin.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed on signup");
                Intent i = new Intent(NewLogin.this, SignUp.class);
                startActivity(i);
            }
        });


    }

    private void updateUI(FirebaseUser user) {
    }

    private void try_things()
    {
//        Lesson lesson = new Lesson("infi", "3FfLFX2iIY0rk68kGJk7", "50", "");
//        LessonDB.setLessonData(lesson);

         firestore.collectionGroup("lessons").whereEqualTo("lessonId", "infi")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("DOCS_FIND", document.getId() + " => " + document.getData());
                        System.out.println(document.getReference().getPath());
                    }
                }
            }
        });

        //        DataCenterDB dataCenter = new DataCenterDB("a", "b", "c", "d");
////        for (int j = 0; j < 5; j++)
////        {
////            dataCenter.setRecordToDb();
////        }
//        dataCenter = new DataCenterDB("a", "", "", "");
//        ArrayList<DataCenterDB> dbRequests = DataCenterDB.queryGetRecords(dataCenter);
//        System.out.println(dbRequests);.
        //"VHD5JzHfxydvjZfnMBveS0YE3X33"
//        System.out.println(PersonDataDB.getTutorFromDB("VHD5JzHfxydvjZfnMBveS0YE3X33"));
//        System.out.println(PersonDataDB.getStudentFromDB("VHD5JzHfxydvjZfnMBveS0YE3X33"));

//        Meeting[] me = {new Meeting("1", "a", "a", "a", "a"),
//                new Meeting("2", "b", "b", "b", "b")};
//
//        ArrayList<Meeting> meetings = new ArrayList<>(Arrays.asList(me));
//        Lesson lesson = new Lesson("111", meetings);
//        CollectionReference cities = firestore.collection("cities");
//        cities.document("22").set(lesson);
//        DocumentReference docRef = cities.document("22");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        Lesson lesson = document.toObject(Lesson.class);
//                        System.out.println(lesson);
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
        //CollectionReference cities = firestore.collection("cities");
//        firestore.collection("cities").whereEqualTo("a", "b").getFirestore().collectionGroup("")
//        firestore.collection("cities").whereEqualTo("lessonName", "11").getFirestore()
//                .collectionGroup("hello").whereEqualTo("start", "2").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful())
//                        {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("COLL_ME", document.getId() + " => " + document.getData());
//                                System.out.println(document.getReference().getPath());
//                            }
//                        }
//                        else
//                        {
//                            System.out.println("failed to find query");
//                        }
//                    }
//                });
    }
}