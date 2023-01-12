package controller;

import db.MeetingDB;
import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;
import model.PastFutureClassMoreInfoModel;

public class PastFutureClassMoreInfoController {

    public static Lesson getLesson(String Tid,String Lid){
        return PastFutureClassMoreInfoModel.getLesson(Tid, Lid);
    }

    public static Meeting getMeeting(String Mid){
        return PastFutureClassMoreInfoModel.getMeeting(Mid);
    }
    public static Tutor getTutor(String Tid){
        return PastFutureClassMoreInfoModel.getTutor(Tid);
    }
    public static Student getStudent(String Sid){
        return PastFutureClassMoreInfoModel.getStudent(Sid);
    }

    public static boolean updateMeeting(Meeting m){
        return PastFutureClassMoreInfoModel.updateMeeting(m);
    }

}
