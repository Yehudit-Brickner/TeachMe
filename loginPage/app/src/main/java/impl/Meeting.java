package impl;

import com.google.firebase.Timestamp;

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

    public Meeting()
    {
    }

    public Meeting(String meetingId, String dateStart, String timeStart, String dateEnd, String timeEnd)
    {
        this.meetingId = meetingId;
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
        this.startDateTime = Timestamp.now();
        this.endDateTime = Timestamp.now();
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

    public Meeting(String meetingId, String date) {
        this.meetingId = meetingId;
        this.dateStart = date;
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
}

