package impl;

import java.util.ArrayList;
import java.util.Arrays;

import interfaces.ILesson;

public class Lesson implements ILesson
{
    protected String lessonId = "";
    protected ArrayList<Meeting> meetings = new ArrayList<>();

    public Lesson()
    {
    }

    public Lesson(String lessonId, ArrayList<Meeting> meetings) {
        this.lessonId = lessonId;
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

    @Override
    public String toString() {
        String retStr = "Lesson{";
        retStr += "lessonId='" + lessonId + '\'' + ", \n";
        //retStr += "meetingIdList=" + meetingIdList + ", \n";
        retStr += "meetings=" + meetings + '}';

        return retStr;
    }
}
