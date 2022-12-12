package impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import interfaces.IMeeting;

public class Meeting implements IMeeting, Comparable<Meeting>
{
    protected String meetingId;
    protected String dateStart;
    protected String timeStart;
    protected String dateEnd;
    protected String timeEnd;

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
        return "Meeting{" + meetingId + '}';
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
}