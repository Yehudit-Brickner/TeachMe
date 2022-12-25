package db;

import android.util.Log;

import com.google.android.gms.tasks.Task;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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

    public static void setMeeting(Meeting meeting) {

        DocumentReference docRef;

        if (meeting.getMeetingId() == null || meeting.getMeetingId().isEmpty())
        {
            docRef = firestore
                    .collection(PersonDataDB.COLL_NAME).document(meeting.getTutorId())
                    .collection(ILesson.DOCK_NAME).document(meeting.getLessonId())
                    .collection(IMeeting.DOCK_NAME).document();

            String meetingId = docRef.getId();
            meeting.setMeetingId(meetingId);
        }
        else
        {
            docRef = firestore
                    .collection(PersonDataDB.COLL_NAME).document(meeting.getTutorId())
                    .collection(ILesson.DOCK_NAME).document(meeting.getLessonId())
                    .collection(IMeeting.DOCK_NAME).document(meeting.getMeetingId());
        }
        docRef.set(meeting);
        firestore.collection(IMeeting.DOCK_NAME).document(meeting.getMeetingId()).set(meeting);
    }

    public static ArrayList<Meeting> getStudentMeetings(String StudentId) {
        ArrayList<Meeting> meetings = new ArrayList<>();
        if (StudentId == null || StudentId.isEmpty())
            return meetings;

        Query query = firestore.collection(IMeeting.DOCK_NAME).whereEqualTo("studentId", StudentId);
        Task<QuerySnapshot> task = query.get();

        DataCenterDB.waitTaskComplete(task);

        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                meetings.add(document.toObject(Meeting.class));
            }
        }
        return meetings;
    }




    public static ArrayList<Meeting> getTutorMeetings(String TutorId){
        ArrayList<Meeting> meetings = new ArrayList<>();
        if (TutorId == null || TutorId.isEmpty())
            return meetings;
        Query query = firestore.collection(IMeeting.DOCK_NAME).whereEqualTo("tutorId",TutorId);
        Task<QuerySnapshot> task = query.get();

        DataCenterDB.waitTaskComplete(task);
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("QUERY_TEST", document.getId() + " => " + document.getData());
                meetings.add(document.toObject(Meeting.class));
            }
        }
        return meetings;
    }


    public static Meeting getMeeting(String mID){
        DocumentReference docRef = firestore.collection("meetings").document(mID);
        Task<DocumentSnapshot> task = docRef.get();
        DataCenterDB.waitTaskComplete(task);
        DocumentSnapshot document = task.getResult();
        if(!document.exists())
            return null;
        return document.toObject(Meeting.class);

    }
}




