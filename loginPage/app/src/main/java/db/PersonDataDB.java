package db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import impl.Person;
import impl.Student;
import impl.Tutor;

public class PersonDataDB {

    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    final static String COLL_NAME = "users";

    public static Tutor getTutorFromDB(String uID) {
        DocumentReference docRef = firestore.collection(COLL_NAME).document(uID);
        Task<DocumentSnapshot> task = docRef.get();
        while (!task.isComplete()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DocumentSnapshot document = task.getResult();
        if(!document.exists() || !document.contains("is_tutor") || !((Boolean)document.get("is_tutor")))
            return null;
        return document.toObject(Tutor.class);
    }

    public static Student getStudentFromDB(String uID) {
        DocumentReference docRef = firestore.collection(COLL_NAME).document(uID);
        Task<DocumentSnapshot> task = docRef.get();
        while (!task.isComplete()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DocumentSnapshot document = task.getResult();
        if(!document.exists() || !document.contains("is_student") ||!((Boolean)document.get("is_student")))
            return null;
        return document.toObject(Student.class);
    }
}