package com.example.loginpage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);

        TextView username= (TextView) findViewById(R.id.email);
        TextView password= (TextView) findViewById(R.id.password);

        MaterialButton loginbtn=(MaterialButton) findViewById(R.id.loginbtn);

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document("a@a.com");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("YEHUDIT_GILAD", "DocumentSnapshot data: " + document.getData().toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admins") &&(password.getText().toString().equals("admins"))){
                    Intent i =new Intent(StudentSignIn.this, StudentHomePage.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(StudentSignIn.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}