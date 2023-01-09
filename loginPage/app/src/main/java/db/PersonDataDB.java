package db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import impl.Person;
import impl.Student;
import impl.Tutor;
import interfaces.IPerson;


public class PersonDataDB
{
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


    public static void setPersonData(IPerson person) {
        if (person.getUID() == null || person.getUID().isEmpty())
            return;

        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection(COLL_NAME);
        usersCollection.document(person.getUID()).set(person.getPersonMap());
    }

    public static void setPersonData(IPerson person, boolean is_tutor, boolean is_student) {
        if (person.getUID() == null || person.getUID().isEmpty())
            return;

        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection(COLL_NAME);
        Map<String, Object> map = person.getPersonMap();
        map.put("is_tutor", is_tutor);
        map.put("is_student", is_student);
        usersCollection.document(person.getUID()).set(map);
    }


    public static void updatePersonDataDB(IPerson person, boolean is_tutor, boolean is_student) {
        if (person.getUID() == null || person.getUID().isEmpty())
            return;

        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection(COLL_NAME);
        Map<String, Object> map = person.getPersonMap();
        map.put("is_tutor", is_tutor);
        map.put("is_student", is_student);
        usersCollection.document(person.getUID()).update(map);
    }

    public static boolean isGoodPersonData(IPerson person) {
        if (person == null)
            return true;

        return (!person.getFirstName().isEmpty() && !person.getLastName().isEmpty()
                && !person.getEmail().isEmpty() && !person.getPhoneNumber().isEmpty());
    }


    public static void updatePersonData(String UID, String fname, String lname, String phone, String email, boolean isS, boolean isT){
        Person person =new Person( UID,fname,lname,phone,email);
        updatePersonDataDB(person,isS,isT);
    }
}