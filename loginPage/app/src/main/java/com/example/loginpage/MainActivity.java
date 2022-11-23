package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import db.LessonDB;
import impl.Lesson;

public class MainActivity extends AppCompatActivity {

    public FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getLessonsFromDB();
        try {
            Method method1 = MainActivity.class.getMethod("me");
            myMethod(this, method1);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Log.d("LESSON_FUNC", getLessonsFromDB().toString());

        Button studentbtn=(Button) findViewById(R.id.student_button);
        Button tutorbutn=(Button) findViewById(R.id.tutor_button);


        studentbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });
        tutorbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(MainActivity.this, MainActivity3.class);
                startActivity(i);
            }
        });
    }




    private void runDBTest()
    {
        firestore = FirebaseFirestore.getInstance();

//        Map<String, Object> users =new HashMap<>();
//        users.put("firstName","Yehudit");
//        users.put("lastName", "Brickner");
//        users.put("description", "admin");

        CollectionReference lessons = firestore.collection("lessons");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("lessonId", "123456789");
        ArrayList<String> mettings = new ArrayList<>(Arrays.asList("1a", "2b", "3c", "4d"));
        data1.put("meetingIdList", mettings);
        lessons.document("admint").set(data1);




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
//        boolean isEmpty = true;
//        String s = "a@a.com";
//        firestore.collection("users").whereEqualTo("Document ID", s)
//                .limit(1).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            boolean isEmpty = task.getResult().isEmpty();
//                            Log.d("GILAD_DEBUG", (isEmpty ? "has " + s : "NOPE"));
//                        }
//                    }
//                });

    }

    public Lesson getLessonsFromDB()
    {
        return LessonDB.getLessonFromDB("admint");
    }

    public void me()
    {
        try {
            Log.d("try func pass", "good");
        } catch (Exception ex)
        {
            Log.d("try func pass", "BAD :(");
        }

    }

    public void myMethod(Object obj, Method method, Object... myArgs) throws InvocationTargetException, IllegalAccessException {
        if (myArgs == null)
            method.invoke(obj);

        else
            method.invoke(obj, myArgs);
    }

}