package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import db.DataCenterDB;

public class MainActivity extends AppCompatActivity {

    public static FirebaseFirestore firestore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();


        Intent i =new Intent(MainActivity.this, NewLogin.class);
        startActivity(i);



//        Map<String, Object> users =new HashMap<>();
//        users.put("firstName","Yehudit");
//        users.put("lastName", "Brickner");
//        users.put("description", "admin");
//
//        firestore.collection("users").add(users).addOnSuccessListener(
//                new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                    }}).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Button studentbtn=(Button) findViewById(R.id.student_button);
//        Button tutorbutn=(Button) findViewById(R.id.tutor_button);
//
//
//        studentbtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
////                Intent i =new Intent(MainActivity.this, StudentSignIn.class);
//                Intent i =new Intent(MainActivity.this, NewLogin.class);
//                startActivity(i);
//            }
//        });
//        tutorbutn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i =new Intent(MainActivity.this, TutorSignIn.class);
//                Intent i =new Intent(MainActivity.this, NewLogin.class);
//                startActivity(i);
//            }
//        });
    }
}