package db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.HashSet;

import impl.Lesson;
import impl.Meeting;
import interfaces.ILesson;
import interfaces.IMeeting;

public class LessonDB extends Lesson
{
    protected ArrayList<String> meetingIdList = new ArrayList<>();
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LessonDB()
    {

    }

    public LessonDB(String lessonId, ArrayList<String> meetingIdList) {
        this.lessonId = lessonId;
        this.meetingIdList = new ArrayList<>(meetingIdList);
    }

    public void copyFromOther(LessonDB other) {
        this.lessonId = other.lessonId;
        this.meetingIdList = other.meetingIdList;
        this.meetings = new ArrayList<>();
    }

    public static boolean setLessonData(Lesson lesson)
    {
        // TODO: check if the tutor exists
        if (lesson.getTutorId() == null || lesson.getTutorId().isEmpty())
            return false;

        // TODO: check if the lesson exists
        if (lesson.getLessonId() == null || lesson.getLessonId().isEmpty())
            return false;

        CollectionReference lessonsColl = firestore.collection(PersonDataDB.COLL_NAME).document(lesson.getTutorId()).collection(Lesson.DOCK_NAME);
        lessonsColl.document(lesson.getLessonId()).set(lesson);

        // TODO: add if document added successfully

        return true;
    }
    
    public static Lesson getLessonFromDB(String Uid, String lessonId)
    {
        String tag = "LESSONS_DEBUG";
        // for changeing option
        Lesson lesson = new Lesson();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection(PersonDataDB.COLL_NAME).document(Uid).collection(DOCK_NAME).document(lessonId);
        Task<DocumentSnapshot> task = docRef.get();

        DataCenterDB.waitTaskComplete(task);

        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {

                Log.d("AUTH_DEBUG", "DocumentSnapshot data: " + document.getData().toString());
                lesson = document.toObject(Lesson.class);
            } else {
                Log.d(tag, "No such document");
            }

        }
        else {
            Log.d(tag, "get failed with ", task.getException());
        }
        
        return lesson;
    }

    public ArrayList<String> getMeetingIdList() {
        return meetingIdList;
    }

    public void setMeetingIdList(ArrayList<String> meetingIdList) {
        this.meetingIdList = meetingIdList;
    }


    public static ArrayList<Lesson> getLessonsByTutorId(String tutorId)
    {
        ArrayList<Lesson> lessons = new ArrayList<>();
        if (tutorId == null || tutorId.isEmpty())
            return lessons;

        CollectionReference lessonsCol = firestore.collection(PersonDataDB.COLL_NAME).document(tutorId).collection(ILesson.DOCK_NAME);

        Task<QuerySnapshot> task = lessonsCol.get();

        DataCenterDB.waitTaskComplete(task);

        if (task.isSuccessful()) {

            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                lessons.add(document.toObject(Lesson.class));
            }
        }
        return lessons;
    }


    public static ArrayList<Lesson> getLessonsByName(String lessonName)
    {
        ArrayList<Lesson> lessons = new ArrayList<>();
        if (lessonName == null || lessonName.isEmpty())
            return lessons;

        Query lessonsCol = firestore.collectionGroup(IMeeting.DOCK_NAME).whereGreaterThan("startDateTime", Timestamp.now());

        Task<QuerySnapshot> task = lessonsCol.get();

        DataCenterDB.waitTaskComplete(task);

        HashSet<Lesson> lessonHashSet = new HashSet<>();
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                PathParse parser = new PathParse(document.getReference().getPath());
                if (parser.getDataFromParsed(ILesson.DOCK_NAME) == null || parser.getDataFromParsed(PersonDataDB.COLL_NAME) == null)
                    continue;


                if (!parser.getDataFromParsed(ILesson.DOCK_NAME).equals(lessonName))
                    continue;

                lessonHashSet.add(LessonDB.getLessonFromDB(parser.getDataFromParsed(PersonDataDB.COLL_NAME), parser.getDataFromParsed(ILesson.DOCK_NAME)));
            }
        }
        return new ArrayList<>(lessonHashSet);
    }
    
    public static ArrayList<String> getLessonsNames(){


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        // Get the current date
//        Date currentDate = new Date();
//        // Format the date using the SimpleDateFormat object
//        String formattedDate = dateFormat.format(currentDate);

        Query lessonsCol = firestore.collectionGroup(IMeeting.DOCK_NAME).whereGreaterThan("startDateTime", Timestamp.now());
//        Query lessonsCol = firestore.collectionGroup(IMeeting.DOCK_NAME).whereGreaterThan("startDate", Timestamp.now());
        Task<QuerySnapshot> task = lessonsCol.get();
        DataCenterDB.waitTaskComplete(task);
        HashSet<String> lessonHashSet = new HashSet<>();
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                PathParse parser = new PathParse(document.getReference().getPath());
                if (parser.getDataFromParsed(ILesson.DOCK_NAME) == null || parser.getDataFromParsed(PersonDataDB.COLL_NAME) == null)
                    continue;
                lessonHashSet.add(parser.getDataFromParsed(ILesson.DOCK_NAME));
            }
        }
        return new ArrayList<>(lessonHashSet);
    }

    public static boolean addMeetingsToLessonDB(Lesson lesson){
        //update lesson?

        if (lesson.getTutorId() == null || lesson.getTutorId().isEmpty())
            return false;
        if (lesson.getLessonId() == null || lesson.getLessonId().isEmpty())
            return false;
        CollectionReference meets = firestore.collection(PersonDataDB.COLL_NAME).document(lesson.getTutorId()).collection(Lesson.DOCK_NAME).document(lesson.getLessonId()).collection("meetings");

        for (int i=0; i<lesson.getMeetings().size();i++){
            // if doesn't exist
            meets.document(lesson.getMeetings().get(i).getMeetingId()).set(lesson.getMeetings().get(i));
        }
        return true;

    }
}
