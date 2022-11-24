package interfaces;

import impl.Meeting;

import java.util.ArrayList;

public interface ILesson
{
    static final String DOCK_NAME = "lessons";
    public String getLessonId();
    public ArrayList<Meeting> getMeetings();

}
