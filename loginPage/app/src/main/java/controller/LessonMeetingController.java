package controller;

import java.util.ArrayList;

import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;
import model.lessonMeetingModel;

public class LessonMeetingController {

    public static Lesson getLesson(String Tid,String Lid){
        return lessonMeetingModel.getLesson(Tid, Lid);
    }

    public static Meeting getMeeting(String TID, String LID, String MID){
        return lessonMeetingModel.getMeeting(TID,LID,MID);
    }

    public static Tutor getTutor(String Tid){
        return lessonMeetingModel.getTutor(Tid);
    }

    public static Student getStudent(String Sid){
        return lessonMeetingModel.getStudent(Sid);
    }

    public static boolean updateMeeting(Meeting m){
        return lessonMeetingModel.updateMeeting(m);
    }

    public static boolean setMeeting(Meeting m){
        return lessonMeetingModel.setMeeting(m);
    }

    public static ArrayList<Lesson> getLessonbyTutor(String TID){
        return lessonMeetingModel.getLessonbyTutor(TID);
    }

    public static void createLesson(Lesson l){
        lessonMeetingModel.createLesson(l);
    }

}
