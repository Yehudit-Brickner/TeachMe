package db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import connection.HttpManager;
import impl.Lesson;
import impl.Meeting;
import interfaces.ILesson;
import interfaces.IMeeting;

public class LessonDB extends Lesson {
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

    public static boolean setLessonData(Lesson lesson) {
        HttpManager httpResponse = null;
        try {
            httpResponse = HttpManager.PostRequest("/set/lesson", lesson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (httpResponse==null){
            return false;
        }
        String s = String.valueOf(httpResponse.getData());

        return (httpResponse.getCode() == HttpManager.OK);
    }
    
    public static Lesson getLessonFromDB(String Uid, String lessonId) {

        try {
            HttpManager httpResponse = HttpManager.GetRequest("/get_tutor_lesson",
                    Map.of("UID", Uid, "LID", lessonId));

            if (httpResponse.getCode() == HttpManager.ERR)
                return null;

            if (httpResponse.getCode() != HttpManager.OK)
                return null;

            Lesson lesson = Lesson.ObjectToLesson(httpResponse.getData());
            lesson.setMeetings(MeetingDB.getMeetingsByTutorAndLessonId(Uid, lessonId));

            return lesson;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<String> getMeetingIdList() {
        return meetingIdList;
    }

    public void setMeetingIdList(ArrayList<String> meetingIdList) {
        this.meetingIdList = meetingIdList;
    }

    public static ArrayList<Lesson> getLessonsByTutorId(String tutorId) {
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

    public static ArrayList<Lesson> getLessonsByName(String lessonName, String startDate, String endDate) {
        Log.d("RUNNING", "RUNNING getLessonsByName");

        try {
            System.out.println(lessonName);
            System.out.println(startDate);
            System.out.println(endDate);
            HttpManager httpResponse = HttpManager.GetRequest("/get/lessons/by_name",
                    Map.of("LID", lessonName + "", "start", startDate + "", "end", endDate + ""));

            if (httpResponse.getCode() == HttpManager.ERR)
                return new ArrayList<>();

            if (httpResponse.getCode() != HttpManager.OK)
                return new ArrayList<>();

//            ArrayList<Object> objects = (ArrayList<Object>) httpResponse.getData();
//            ArrayList<Lesson> lessons = new ArrayList<>();
//            for (Object o: objects)
//                lessons.add(Lesson.ObjectToLesson(o));
            return toArrayLesson(httpResponse.getData());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public static ArrayList<String> getLessonsNames(){


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        // Get the current date
//        Date currentDate = new Date();
//        // Format the date using the SimpleDateFormat object
//        String formattedDate = dateFormat.format(currentDate);

//        Query lessonsCol = firestore.collectionGroup(IMeeting.DOCK_NAME).whereGreaterThan("startDateTime", Timestamp.now());
////        Query lessonsCol = firestore.collectionGroup(IMeeting.DOCK_NAME).whereGreaterThan("startDate", Timestamp.now());
//        Task<QuerySnapshot> task = lessonsCol.get();
//        DataCenterDB.waitTaskComplete(task);
//        HashSet<String> lessonHashSet = new HashSet<>();
//        if (task.isSuccessful()) {
//            for (QueryDocumentSnapshot document : task.getResult()) {
//                Log.d("getLessonsNames", document.getId() + " => " + document.getData());
//                PathParse parser = new PathParse(document.getReference().getPath());
//                if (parser.getDataFromParsed(ILesson.DOCK_NAME) == null || parser.getDataFromParsed(PersonDataDB.COLL_NAME) == null)
//                    continue;
//                lessonHashSet.add(parser.getDataFromParsed(ILesson.DOCK_NAME));
//            }
//        }
//        return new ArrayList<>(lessonHashSet);
        Log.d("RUNNING", "RUNNING getLessonsNames");
        try {
            HttpManager httpResponse = HttpManager.GetRequest("/get/lessons/names");

            if (httpResponse.getCode() == HttpManager.ERR)
                return new ArrayList<>();

            if (httpResponse.getCode() != HttpManager.OK)
                return new ArrayList<>();

            return (ArrayList<String>)(httpResponse.getData());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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

    public static ArrayList<Lesson> toArrayLesson(Object o){
        if (o == null)
            return null;

        Gson gson = new Gson ();

        String json = gson.toJson(o);
        return gson.fromJson(json, new TypeToken<ArrayList<Lesson>>(){}.getType());
    }

}
