package impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return lessonId.equals(lesson.lessonId) && tutorId.equals(lesson.tutorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, tutorId);
    }
}
