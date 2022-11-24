package com.example.loginpage;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.loginpage", appContext.getPackageName());
    }

    @Test
    public void testDBTest()
    {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

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

        firestore.collection("DataCenter")
                .whereEqualTo("tutor", "Gilad")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("QUERY_TEST", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}