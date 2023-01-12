package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static FirebaseFirestore firestore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();


        Intent i =new Intent(MainActivity.this, Login.class);
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