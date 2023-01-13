package impl;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import interfaces.ILesson;

public class Lesson implements ILesson
{

    protected String lessonId = "";
    protected String tutorId = "";
    protected String price = "";
    protected String freeText = "";
    protected transient ArrayList<Meeting> meetings = null;


    public Lesson()
    {
    }

    public Lesson(String lessonId, ArrayList<Meeting> meetings) {
        this.lessonId = lessonId;
//        this.meetings = meetings;
    }

    public Lesson(String lessonId, String tutorId, String price, String freeText) {
        this(lessonId, tutorId, price, freeText, null);
    }

    public Lesson(String lessonId, String tutorId, String price, String freeText, ArrayList<Meeting> meetings) {
        this.lessonId = lessonId;
        this.tutorId = tutorId;
        this.price = price;
        this.freeText = freeText;
//        this.meetings = meetings;
    }
    public Lesson(JsonObject json){
        this.lessonId=String.valueOf(json.get("lessonId"));
        this.tutorId=String.valueOf(json.get("tutorId"));
        this.price=String.valueOf(json.get("price"));
        this.freeText=String.valueOf(json.get("freeText"));
//        this.meetings=String.valueOf(json.get("meetings"));
        this.meetings=null;
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

    public String getPrice(){
        return this.price;
    }

    public void addMeeting(Meeting m){
        if (this.meetings==null){
            ArrayList<Meeting> meetings=new ArrayList<>();
            meetings.add(m);
            this.meetings=meetings;
        }
        else{
            this.meetings.add(m);
        }
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    @Override
    public String toString() {
        String retStr = "Lesson{";
        retStr += "lessonId='" + lessonId + '\'' + ", \n";
        retStr += "tutorId=" + tutorId + '\'' + ", \n";
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


//    public Map<String, Object> getMyMap(){
//        Map<String, Object> lessonMap= new HashMap<>();
//        lessonMap.put("lessonId", this.lessonId);
//        lessonMap.put("tutorId", this.tutorId);
//        lessonMap.put("price", this.price);
//        lessonMap.put("freeText", this.freeText);
//        return lessonMap;
//    }

    public String toJson(){
      return new Gson().toJson(this);
    }


    public static Lesson ObjectToLesson(Object o){
        if (o == null)
            return null;
        Gson gson = new Gson ();
        String json = gson.toJson(o);
        return gson.fromJson(json, Lesson.class);
    }
}
