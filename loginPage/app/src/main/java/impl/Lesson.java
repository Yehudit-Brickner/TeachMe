package impl;

import java.util.ArrayList;
import java.util.Arrays;

import interfaces.ILesson;

public class Lesson implements ILesson
{

    protected String lessonId = "";
    protected String tutorId = "";
    protected String price = "";
    protected String freeText = "";
    protected ArrayList<Meeting> meetings = null;


    public Lesson()
    {
    }

    public Lesson(String lessonId, ArrayList<Meeting> meetings) {
        this.lessonId = lessonId;
        this.meetings = meetings;
    }

    public Lesson(String lessonId, String tutorId, String price, String freeText) {
        this(lessonId, tutorId, price, freeText, null);
    }

    public Lesson(String lessonId, String tutorId, String price, String freeText, ArrayList<Meeting> meetings) {
        this.lessonId = lessonId;
        this.tutorId = tutorId;
        this.price = price;
        this.freeText = freeText;
        this.meetings = meetings;
    }





    @Override
    public String getLessonId() {
        return lessonId;
    }

    @Override
    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setMeetings(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    @Override
    public String toString() {
        String retStr = "Lesson{";
        retStr += "lessonId='" + lessonId + '\'' + ", \n";
        //retStr += "meetingIdList=" + meetingIdList + ", \n";
        retStr += "meetings=" + meetings + '}';

        return retStr;
    }
}
