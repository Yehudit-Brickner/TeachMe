package db;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import impl.Lesson;
import impl.Meeting;
import interfaces.ILesson;
import interfaces.IMeeting;

public class MeetingDB
{
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static Meeting setMeeting(String studentId, Meeting meeting)
    {
        if (meeting == null || meeting.getLessonId() == null)
            return null;

        String lessonId = meeting.getLessonId();

        if (meeting.getMeetingId() == null || meeting.getMeetingId().isEmpty())
        {
//            DocumentSnapshot doc = firestore.collection(PersonDataDB.COLL_NAME).document(studentId)
//                    .collection(ILesson.DOCK_NAME).document(lessonId).collection(IMeeting.DOCK_NAME);
        }
        return null;
    }

    public static ArrayList<Meeting> getMeetingsByTutorAndLessonId(String tutorId, String lessonId)
    {
        ArrayList<Meeting> meetings = new ArrayList<>();
        if (tutorId == null || tutorId.isEmpty())
            return meetings;

        CollectionReference lessonsCol = firestore.collection(PersonDataDB.COLL_NAME).document(tutorId)
                .collection(ILesson.DOCK_NAME).document(lessonId).collection(IMeeting.DOCK_NAME);

        Task<QuerySnapshot> task = lessonsCol.get();

        DataCenterDB.waitTaskComplete(task);

        if (task.isSuccessful()) {

            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());

                meetings.add(document.toObject(Meeting.class));
            }
        }
        return meetings;
    }
}
