package interfaces;

public interface IMeeting
{
    static final String DOCK_NAME = "meetings";
    static final String DATE_FORMAT = "dd/MM/yyyy";
    static final String TIME_FORMAT = "HH:mm";   // HH:mm:ss
    static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    public String getMeetingId();
    public String getDateStart();
    public String getTimeStart();
    public String getDateEnd();
    public String getTimeEnd();

}
