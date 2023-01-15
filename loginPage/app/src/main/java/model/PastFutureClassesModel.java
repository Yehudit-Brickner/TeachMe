package model;

import java.util.ArrayList;

import db.MeetingDB;
import impl.Meeting;

public class PastFutureClassesModel {


    public static ArrayList<Meeting> getTutorMeetings(String UID){
        return MeetingDB.getTutorMeetings(UID);
    }

    public static ArrayList<Meeting> getStudentMeetings(String UID){
        return MeetingDB.getStudentMeetings(UID);
    }
}
