package impl;

public class Meeting
{
    protected String meetingId;
    protected String date_start;
    protected String time_start;
    protected String date_end;



    public Meeting(String meetingId, String date) {
        this.meetingId = meetingId;
        this.date_start = date;
    }

    @Override
    public String toString() {
        return "Meeting{" + meetingId + '}';
    }
}

