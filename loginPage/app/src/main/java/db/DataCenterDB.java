package db;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataCenterDB
{
    public String lessonId = "";
    public String meetingId = "";
    public String tutorId = "";
    public String studentId = "";

    private static final String DOCK_NAME = "DataCenter";

    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public DataCenterDB()
    {

    }

    public DataCenterDB(String lessonId, String meetingId, String tutorId, String studentId) {
        this.lessonId = lessonId;
        this.meetingId = meetingId;
        this.tutorId = tutorId;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "DataCenterDB{" +
                "lessonId='" + lessonId + '\'' +
                ", meetingId='" + meetingId + '\'' +
                ", tutorId='" + tutorId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }

    public static ArrayList<DataCenterDB> queryGetRecords(DataCenterDB record)
    {
        Query query = null;
        CollectionReference dbCenterColl = firestore.collection(DOCK_NAME);
        Map<String, Object> map = record.getDBasMap();
        for (String field: map.keySet())
        {
            // if the field of record is null then continue to thr next
            if (Objects.equals(map.get(field), ""))
                continue;

            if (query == null)
                query = dbCenterColl.whereEqualTo(field, map.get(field));
            else
                query.whereEqualTo(field, map.get(field));
        }

        ArrayList<DataCenterDB> dbRecords = new ArrayList<>();

        if (query == null)
            return dbRecords;

        Log.d("QUERY_DC", "try");
        Task<QuerySnapshot> task = query.get();

        while (!task.isComplete()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d("QUERY_DC", "finished");

        if (task.isSuccessful()) {

            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                dbRecords.add(document.toObject(DataCenterDB.class));

            }
        }

//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
//                        dbRecords.add(document.toObject(DataCenterDB.class));
//
//                    }
//                } else {
//                    Log.d("QUERY_TEST", "Error getting documents: ", task.getException());
//                }
//            }});
        return dbRecords;
    }

    public void setRecordToDb()
    {
        Map<String, Object> recordMap = this.getDBasMap();

        firestore.collection(DOCK_NAME).add(recordMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // TODO:

                }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // TODO:
                    }
        });
    }

    public Map<String, Object> getDBasMap() {
        Map<String, Object> map = new HashMap<>();
        // Use MyObject.class.getFields() instead of getDeclaredFields()
        // If you are interested in public fields only
        map.put("lessonId", (this.lessonId == null) ? "" : this.lessonId);
        map.put("meetingId", (this.meetingId == null) ? "" : this.meetingId);
        map.put("tutorId", (this.tutorId == null) ? "" : this.tutorId);
        map.put("studentId", (this.studentId == null) ? "" : this.studentId);
        return (map);
    }
}
