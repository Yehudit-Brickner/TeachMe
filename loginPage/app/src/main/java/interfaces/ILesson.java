package interfaces;

import impl.Meeting;

import java.util.ArrayList;

public interface ILesson
{
    public String getLessonId();
    public ArrayList<Meeting> getMeetings();
}
