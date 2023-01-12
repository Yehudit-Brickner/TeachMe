package controller;

import android.widget.Switch;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import impl.Meeting;
import model.PastFutureClassesModel;

public class PastFutureClassesController {

    public static void switchclicked(Switch myswitch, ArrayList<Meeting> meetings, int flag){
        // flag -1 is past
        // flag 1 is futre
        // this is so the meetings can be ordered by date closest to farthest
        if (flag==-1) {
           if (myswitch.isChecked()) {
               Collections.sort(meetings, new Comparator<Meeting>() {
                   public int compare(Meeting m1, Meeting m2) {
                       if (m1.getLessonId().compareTo(m2.getLessonId()) < 0)
                           return -1;
                       else {
                           return 1;
                       }
                   }
               });
           }
           else {
               Collections.sort(meetings, new Comparator<Meeting>() {
                   public int compare(Meeting m1, Meeting m2) {
                       Timestamp t1 = m1.getStartDateTime();
                       Timestamp t2 = m2.getStartDateTime();
                       if (t1.compareTo(t2) < 0)
                           return 1;
                       else {
                           return -1;
                       }
                   }
               });
           }
       }
       else{
           if (myswitch.isChecked()) {
               Collections.sort(meetings, new Comparator<Meeting>(){
                    public int compare(Meeting m1, Meeting m2){
                        if(m1.getLessonId().compareTo(m2.getLessonId())<0)
                            return -1;
                        else{
                            return 1;
                        }
                    }
                });
           }
           else {
                Collections.sort(meetings, new Comparator<Meeting>(){
                    public int compare(Meeting m1, Meeting m2){
                        Timestamp t1=m1.getStartDateTime();
                        Timestamp t2=m2.getStartDateTime();
                        if(t1.compareTo(t2)<0)
                            return -1;
                        else{
                            return 1;
                        }
                    }
                });
           }
       }

    }

    public static ArrayList<Meeting> getPastMeetings(ArrayList<Meeting> meetings){
        ArrayList<Meeting> passedMeetings=new ArrayList<>();
        Date date = Calendar.getInstance().getTime();
        Timestamp now= new Timestamp(date);
        for (int i=0; i< meetings.size();i++){
            Timestamp t = meetings.get(i).getStartDateTime();
            if(now.compareTo(t)>=0) {
                passedMeetings.add(meetings.get(i));
            }
        }

        return passedMeetings;
    }

    public static ArrayList<Meeting> getFutureMeetings(ArrayList<Meeting> meetings){
        ArrayList futureMeetings=new ArrayList<>();
         Date date = Calendar.getInstance().getTime();
        Timestamp now= new Timestamp(date);
        for (int i=0; i< meetings.size();i++){
            Timestamp t = meetings.get(i).getStartDateTime();
            if(now.compareTo(t)<=0) {
                futureMeetings.add(meetings.get(i));
            }
        }
        return futureMeetings;
    }

    public static ArrayList<Meeting> getTutorMeetings(String UID){
        return PastFutureClassesModel.getTutorMeetings(UID);
    }

    public static ArrayList<Meeting> getStudentMeetings(String UID){
        return PastFutureClassesModel.getStudentMeetings(UID);
    }

}
