package db;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

public class DataCenterDB
{
    String lessonId;
    String meetingId;
    String tutorId;
    String studentId;

    static final String DOCK_NAME = "DataCenter";

    public DataCenterDB()
    {

    }

    public DataCenterDB(String lessonId, String meetingId, String tutorId, String studentId) {
        this.lessonId = lessonId;
        this.meetingId = meetingId;
        this.tutorId = tutorId;
        this.studentId = studentId;
    }

    public ArrayList<DataCenterDB> queryGetRecords(DataCenterDB record)
    {
        Query query = null;
        CollectionReference dbCenterColl = FirebaseFirestore.getInstance().collection(DOCK_NAME);
        Map<String, Object> map = this.getDBasMap();
        for (String field: map.keySet())
        {
            // if the field of record is null then continue to thr next
            if (map.get(field) == null)
                continue;

            if (query == null)
                query = dbCenterColl.whereEqualTo(field, map.get(field));
            else
                query.whereEqualTo(field, map.get(field));
        }

        ArrayList<DataCenterDB> dbRecords = new ArrayList<>();

        if (query == null)
            return dbRecords;

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                        dbRecords.add(document.toObject(DataCenterDB.class));

                    }
                } else {
                    Log.d("QUERY_TEST", "Error getting documents: ", task.getException());
                }
            }});
        return dbRecords;
    }

    public void setRecordToDb()
    {
        Map<String, Object> recordMap = this.getDBasMap();

        FirebaseFirestore.getInstance().collection(DOCK_NAME).add(recordMap)
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
        for (Field field : DataCenterDB.class.getDeclaredFields()) {
            // Skip this if you intend to access to public fields only
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL)
                continue;
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
            }
        }
        return (map);
    }
}
