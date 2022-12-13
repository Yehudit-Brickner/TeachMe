package db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

import impl.Lesson;
import impl.Meeting;
import interfaces.ILesson;

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
        updateMeeting();
    }

    public void copyFromOther(LessonDB other) {
        this.lessonId = other.lessonId;
        this.meetingIdList = other.meetingIdList;
        this.meetings = new ArrayList<>();
        updateMeeting();
    }

    public void updateMeeting()
    {
        meetings = new ArrayList<>();
        for (String str : meetingIdList)
        {
            meetings.add(strToMeeting(str));
        }
    }

    public static Lesson getLessonFromDB(String lessonId)
    {
        String tag = "LESSONS_DEBUG";
        // for changeing option
        final LessonDB[] lessonDB = new LessonDB[]{new LessonDB()};
        DocumentReference docRef = FirebaseFirestore.getInstance().collection(DOCK_NAME).document(lessonId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(tag, "DocumentSnapshot data: " + document.getData().toString());
                        lessonDB[0] = document.toObject(LessonDB.class);
                        if (lessonDB[0] == null)
                            lessonDB[0] = new LessonDB();
                        lessonDB[0].updateMeeting();
                        Log.d(tag, lessonDB[0].toString());
                    } else {
                        Log.d(tag, "No such document");
                    }
                } else {
                    Log.d(tag, "get failed with ", task.getException());
                }
            }
        });


        return new Lesson(lessonDB[0].lessonId, lessonDB[0].meetings);
    }

    public ArrayList<String> getMeetingIdList() {
        return meetingIdList;
    }

    public void setMeetingIdList(ArrayList<String> meetingIdList) {
        this.meetingIdList = meetingIdList;
    }

    private Meeting strToMeeting(String str)
    {
        return new Meeting(str, "");
    }

    public static ArrayList<Lesson> getLessonsByTutorId(String tutorId)
    {
        ArrayList<Lesson> lessons = new ArrayList<>();
        if (tutorId == null || tutorId.isEmpty())
            return lessons;

        CollectionReference lessonsCol = firestore.collection(ILesson.DOCK_NAME);
        Query query = lessonsCol.whereEqualTo("tutorId", tutorId);

        Task<QuerySnapshot> task = query.get();

        DataCenterDB.waitTaskComplete(task);

        if (task.isSuccessful()) {

            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                lessons.add(document.toObject(Lesson.class));
            }
        }
        return lessons;
    }
}
