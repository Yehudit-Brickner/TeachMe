package model;

import java.util.ArrayList;

import db.LessonDB;
import db.MeetingDB;
import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class lessonMeetingModel {

    public static Lesson getLesson( String Tid,String Lid){
        return LessonDB.getLessonFromDB(Tid,Lid);
    }

    public static Meeting getMeeting(String TID, String LID, String MID){
        return MeetingDB.getMeeting(TID,LID,MID);
    }

    public static Tutor getTutor(String Tid){
        return PersonDataDB.getTutorFromDB(Tid);
    }

    public static Student getStudent(String Sid){
        return PersonDataDB.getStudentFromDB(Sid);
    }

    public static boolean updateMeeting(Meeting m){
        return MeetingDB.setMeeting(m);
    }

    public static boolean setMeeting(Meeting m){
        return MeetingDB.setMeeting(m);
    }


    public static ArrayList<Lesson> getLessonbyTutor(String TID){
       return LessonDB.getLessonsByTutorId(TID);
    }

    public static void createLesson(Lesson l){
        LessonDB.setLessonData(l);
    }

}
