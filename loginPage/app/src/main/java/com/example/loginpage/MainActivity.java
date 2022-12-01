package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import db.DataCenterDB;
import db.LessonDB;
import impl.Lesson;
import interfaces.ILesson;
import interfaces.IMeeting;

public class MainActivity extends AppCompatActivity {

    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //getLessonsFromDB();
//        try {
//            Method method1 = MainActivity.class.getMethod("me");
//            myMethod(this, method1);
//        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        runDBTest();


        Button studentbtn=(Button) findViewById(R.id.student_button);
        Button tutorbutn=(Button) findViewById(R.id.tutor_button);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Log.d("DATETIME", formatter.format(date));

        Log.d("LESSON_FUNC", getLessonsFromDB().toString());

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

//        CollectionReference lessons = firestore.collection("lessons");
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("lessonId", "123456789");
//        ArrayList<String> mettings = new ArrayList<>(Arrays.asList("1a", "2b", "3c", "4d"));
//        data1.put("meetingIdList", mettings);
//        lessons.document("admint").set(data1);




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


//        Map<String, Object> users =new HashMap<>();
//        users.put(ILesson.DOCK_NAME,"11");
//        users.put(IMeeting.DOCK_NAME, "aa");
//        users.put("tutor", "Gilad");
//        users.put("students", new ArrayList<>(Arrays.asList("David", "Yehudit")));
//
//        firestore.collection("DataCenter").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                }}).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                    }
//        });

        new DataCenterDB("11", "aa", "gilad", "david").setRecordToDb();
        new DataCenterDB("11", "aa", "gilad", "yehudit").setRecordToDb();
        new DataCenterDB("22", "33", "gilad", "yosi").setRecordToDb();
        new DataCenterDB("22", "33", "gilad", "david").setRecordToDb();

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