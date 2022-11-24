package com.example.loginpage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import impl.Lesson;
import interfaces.ILesson;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView username= (TextView) findViewById(R.id.username);
        TextView password= (TextView) findViewById(R.id.password);

        MaterialButton loginbtn=(MaterialButton) findViewById(R.id.loginbtn);



        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.runDBTest();
                if(username.getText().toString().equals("admins") &&(password.getText().toString().equals("admins"))){
                    Intent i =new Intent(MainActivity2.this, MainActivity4.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity2.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void dbGetData()
    {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document("a@a.com");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("YUDIT_GILAD", "DocumentSnapshot data: " + document.getData().toString());
                        UserData userData = document.toObject(UserData.class);
                        Log.d("YUDIT_GILAD", "uname: " + userData.username + "\tpass: " + userData.password);
                    } else {
                        Log.d("YUDIT_GILAD", "No such document");
                    }
                } else {
                    Log.d("YUDIT_GILAD", "get failed with ", task.getException());
                }
            }
        });


        //////////////// REALTIMEEEEEE ///////////////
        ///   https://firebase.google.com/docs/firestore/query-data/listen

        DocumentReference docRefoo = FirebaseFirestore.getInstance().collection("users").document("a@a.com");
        UserData userData = new UserData();
        ListenerRegistration listiner =  docRefoo.addSnapshotListener(new UpdateUser(userData));
//        CollectionReference cities = FirebaseFirestore.getInstance().collection("users");
//        cities.document("a@a.com").update("me", "hello");

//        listiner.remove();
    }
}

class UpdateUser implements EventListener<DocumentSnapshot> {
    public UserData me;

    public UpdateUser(UserData userData)
    {
        me = userData;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot,
                        @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.w("GILAD_DEBUG", "Listen failed.", e);
            return;
        }

        if (snapshot != null && snapshot.exists()) {
            Log.d("GILAD_DEBUG", "Current data: " + snapshot.getData());
            me.copy(Objects.requireNonNull(snapshot.toObject(UserData.class)));
        } else {
            Log.d("GILAD_DEBUG", "Current data: null");
        }
    }
}