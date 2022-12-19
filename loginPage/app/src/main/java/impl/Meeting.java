package impl;

import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import interfaces.IMeeting;

public class Meeting implements IMeeting, Comparable<Meeting>
{
    protected String meetingId;
    protected String lessonId;
    protected String dateStart;
    protected String timeStart;
    protected String dateEnd;
    protected String timeEnd;
    protected Timestamp startDateTime;
    protected Timestamp endDateTime;
    protected String tutorId;
    protected String studentId;
    protected boolean zoom;
    protected boolean inPerson;


    public Meeting()
    {
    }

    public Meeting(String lessonId, String dateStart, String timeStart, String dateEnd,
                   String timeEnd,String tutorId, boolean zoom, boolean inPerson) {

        this.lessonId=lessonId;
        this.meetingId ="";
        setStart(dateStart, timeStart);
        setEnd(dateEnd, timeEnd);
        this.tutorId = tutorId;
        this.studentId = "";
        this.zoom = zoom;
        this.inPerson = inPerson;
    }

    @Override
    public String getMeetingId() {
        return meetingId;
    }

    @Override
    public String getDateStart() {
        return dateStart;
    }

    @Override
    public String getTimeStart() {
        return timeStart;
    }

    @Override
    public String getDateEnd() {
        return dateEnd;
    }

    @Override
    public String getTimeEnd() {
        return timeEnd;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
        this.dateStart = (startDateTime != null) ? Meeting.getStringDate(startDateTime.toDate()) : "";
        this.timeStart = (startDateTime != null) ? Meeting.getStringTime(startDateTime.toDate()) : "";
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
        this.dateEnd = (endDateTime != null) ? Meeting.getStringDate(endDateTime.toDate()) : "";
        this.timeEnd = (endDateTime != null) ? Meeting.getStringTime(endDateTime.toDate()) : "";
    }

    public String getLessonId() {
        return lessonId;
    }

    private void setStart(String date, String time)
    {
        startDateTime = new Timestamp(getDate(date + " " + time));
    }

    private void setEnd(String date, String time)
    {
        endDateTime = new Timestamp(getDate(date + " " + time));
    }

    public Meeting(String meetingId, String date) {
        this.meetingId = meetingId;
        this.dateStart = date;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isZoom() {
        return zoom;
    }

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId='" + meetingId + '\'' +
                ", lessonId='" + lessonId + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }

    @Override
    public int compareTo(Meeting other) {

        SimpleDateFormat sf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date dateTimeThis = new Date(0), dateTimeOther = new Date(0);
        try {
            dateTimeThis = sf.parse(dateStart + " " + timeStart);
            dateTimeOther = sf.parse(other.dateStart + " " + other.timeStart);
        } catch (ParseException e) {
        }

        return dateTimeThis.compareTo(dateTimeOther);
    }



    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("meetingId", meetingId);
        map.put("startDateTime", startDateTime);
        map.put("endDateTime", endDateTime);
        return map;
    }

    public static Date getDate(String date) {
        SimpleDateFormat format=new SimpleDateFormat(IMeeting.DATE_TIME_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

    public static String getStringDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat(IMeeting.DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String getStringTime(Date date){
        DateFormat dateFormat = new SimpleDateFormat(IMeeting.TIME_FORMAT);
        return dateFormat.format(date);
    }

}

